package client.action;

import client.model.UserBean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class MakeEditorAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String grantee;

    @Override
    public String execute() throws RemoteException {
        if(this.getUserBean().grantPrivilege(this.grantee).isAccepted())
            return SUCCESS;
        else
            return ERROR;
    }

    public void setGrantee(String grantee) {
        this.grantee = grantee;
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
