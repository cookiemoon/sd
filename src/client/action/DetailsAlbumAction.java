package client.action;

import Shared.*;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class DetailsAlbumAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String albumID;

    @Override
    public String execute() {
        try {
            Album obj = new Album(Integer.parseInt(albumID), "");
            Message<Album> rsp = this.getBean().getAlbum(obj);
            if (rsp.isAccepted()) {
                session.put("album", obj);
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

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
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
