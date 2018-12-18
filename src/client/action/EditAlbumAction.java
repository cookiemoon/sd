package client.action;

import Shared.*;
import Shared.inputUtil;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import ws.WebSocketAnnotation;

import java.rmi.RemoteException;
import java.util.Map;

public class EditAlbumAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String albumID;
    private String title = "";
    private String desc = "";
    private String label = "";
    private String release_date = "";
    private Album obj = new Album(-1);

    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(albumID)) {
            try {
                obj.setTitle(title);
                obj.setDescription(desc);
                obj.setId(inputUtil.StringToInt(albumID, "album ID"));
                obj.setLabel(label);
                obj.setReleaseDate(inputUtil.toCalendar(release_date, "release date"));
                obj.setOld(new Album(Integer.parseInt(albumID)));
                try {
                    MessageIdentified<Album> rsp = this.getBean().editAlbum(obj);
                    System.out.println(rsp);
                    if (rsp.isAccepted()) {
                        for (String user : rsp.getObj().getEditors()) {
                            if (!user.equals(this.session.get("username")) && WebSocketAnnotation.onlineUsers.get(user) != null)
                                WebSocketAnnotation.onlineUsers.get(user).sendMessage(user + " edited album '" + rsp.getObj().getTitle() + "'");
                        }
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
            } catch (BadInput e) {
                session.put("error", e.getMessage());
                session.put("back", "menu");
                return INPUT;
            }
        } else {
            session.put("error", "Please do not ID field empty.");
            session.put("back", "menu");
            return INPUT;
        }
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
