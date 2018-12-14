package client.action;

import Shared.Message;
import Shared.User;
import client.model.UserBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class RegisterAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null;
    private String error = "";

    @Override
    public String execute() {
        if(this.username != null && !username.equals("") && this.password != null && !password.equals("")) {
            this.getUserBean().setUsername(this.username);
            this.getUserBean().setPassword(this.password);
            try {
                Message<User> rsp = this.getUserBean().register();
                if (rsp.isAccepted()) {
                    session.put("username", username);
                    session.put("loggedin", true);
                    session.put("editor", rsp.getObj().isEditor_f());
                    return SUCCESS;
                } else {
                    this.error = rsp.getErrors();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return LOGIN;
        }
        else {
            this.error = "Please do not leave empty fields.";
            return LOGIN;
        }
    }

    public void setUsername(String username) {
        this.username = username; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserBean getUserBean() {
        if(!session.containsKey("userBean"))
            this.setUserBean(new UserBean());
        return (UserBean) session.get("userBean");
    }

    public void setUserBean(UserBean userBean) {
        this.session.put("userBean", userBean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
