package client.action;

import Shared.*;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import ws.WebSocketAnnotation;

import java.rmi.RemoteException;
import java.util.Map;

public class MakeEditorAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String grantee;

    @Override
    public String execute() {
        try {
            MessageIdentified<String> rsp = this.getBean().grantPrivilege(this.grantee);
            if (rsp.isAccepted()) {
                WebSocketAnnotation.onlineUsers.get(rsp.getObj()).sendMessage("You are now an editor!");
                return SUCCESS;
            }
            else {
                session.put("error", rsp.getErrors());
                session.put("back", "menu");
                return INPUT;
            }
        } catch (RemoteException e) {
            session.put("error", "Server error");
            session.put("back", "menu");
            return INPUT;
        }
    }

    public void setGrantee(String grantee) {
        this.grantee = grantee;
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
