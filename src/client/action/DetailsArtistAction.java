package client.action;

import Shared.*;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class DetailsArtistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String artistID;

    @Override
    public String execute() {
        try {
            Artist obj = new Artist(Integer.parseInt(artistID), "", "");
            Message<Artist> rsp = this.getBean().getArtist(obj);
            if (rsp.isAccepted()) {
                session.put("artist", obj);
                return SUCCESS;
            } else {
                session.put("error", rsp.getErrors());
                session.put("back", "menu");
                return INPUT;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        session.put("error", "Server error.");
        session.put("back", "menu");
        return INPUT;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
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
