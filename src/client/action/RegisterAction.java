package client.action;

import client.model.UserBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class RegisterAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String username = null, password = null;

    @Override
    public String execute() {
        if(this.username != null && !username.equals("")) {
            this.getUserBean().setUsername(this.username);
            this.getUserBean().setPassword(this.password);
            try {
                if (this.getUserBean().register()) {
                    session.put("username", username);
                    session.put("loggedin", true); // this marks the user as logged in
                    return SUCCESS;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return LOGIN;
        }
        else
            return LOGIN;
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
