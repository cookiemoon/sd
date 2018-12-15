package client.action;

import Shared.Message;
import Shared.User;
import client.model.Bean;
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
            this.getBean().setUsername(this.username);
            this.getBean().setPassword(this.password);
            try {
                Message<User> rsp = this.getBean().register();
                if (rsp.isAccepted()) {
                    session.put("username", username);
                    session.put("loggedin", true);
                    session.put("editor", rsp.getObj().isEditor());
                    return SUCCESS;
                } else {
                    session.put("error", rsp.getErrors());
                    session.put("back", "login");
                    return LOGIN;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            session.put("error", "Server error.");
            session.put("back", "login");
            return LOGIN;
        }
        else {
            session.put("error", "Please do not leave any empty fields.");
            session.put("back", "login");
            return LOGIN;
        }
    }

    public void setUsername(String username) {
        this.username = username; // will you sanitize this input? maybe use a prepared statement?
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bean getBean() {
        if(!session.containsKey("bean"))
            this.setBean(new Bean());
        return (Bean) session.get("bean");
    }

    public void setBean(Bean bean) {
        this.session.put("bean", bean);
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
