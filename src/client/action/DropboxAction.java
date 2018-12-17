package client.action;

import Shared.MessageIdentified;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class DropboxAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private String code;

    @Override
    public String execute() {
        System.out.println(code);
        // Check how to get the thing from the url
        // ?code=09129391230
        try {
            MessageIdentified<String> rsp = this.getBean().associateDropbox(this.code);
            if (rsp.isAccepted()) {
                session.put("dropbox_associated", true);
                session.put("dropbox_token", this.code);
                System.out.println("DROPBOX IS YAY");
            } else {
                System.out.println("Some thing went OH SO VERY WRONG");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Couldn't fucking associate the stuff");
        }
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public Bean getBean() {
        if(!session.containsKey("bean"))
            this.setBean(new Bean());
        return (Bean) session.get("bean");
    }

    public void setBean(Bean bean) {
        this.session.put("bean", bean);
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
