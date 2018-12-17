package client.action;

import Shared.*;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class RemoveArtistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String choice;

    @Override
    public String execute() {
        if(choice.equals("Confirm")) {
            if(this.getBean().artistHasNoContent()) {
                try {
                    MessageIdentified<Artist> rsp = this.getBean().removeArtist();
                    if (rsp.isAccepted()) {
                        return SUCCESS;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                session.put("error", "Server error.");
                session.put("back", "menu");
                return INPUT;
            } else {
                session.put("error", "Artist has albums and/or music, cannot be removed.");
                session.put("back", "menu");
                return INPUT;
            }
        } else {
            return SUCCESS;
        }
    }

    public void setChoice(String choice) {
        this.choice = choice;
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
