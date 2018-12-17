package client.action;

import Shared.*;
import Shared.inputUtil;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class PostAlbumAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String title;
    private String desc;
    private String artistID;
    private String release_date;
    private String label;
    private String genres;
    private Album obj = new Album(-1);

    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(title, desc, artistID, release_date, genres)) {
            try {
                obj.setTitle(title);
                obj.setDescription(desc);
                obj.setLabel(label);
                obj.setReleaseDate(inputUtil.toCalendar(release_date, "release date"));
                obj.setArtistID(inputUtil.StringToInt(artistID, "artist ID"));
                obj.setGenres(inputUtil.separateBy(genres, ","));
                try {
                    MessageIdentified<Album> rsp = this.getBean().postAlbum(obj);
                    System.out.println(rsp);
                    if (rsp.isAccepted()) {
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
            session.put("error", "Please do not leave any empty fields");
            session.put("back", "menu");
            return INPUT;
        }
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public void setTitle(String title) {
        this.title = title;
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