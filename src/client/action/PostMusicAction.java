package client.action;

import Shared.*;
import Shared.inputUtil;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class PostMusicAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String title;
    private String duration;
    private String lyrics;
    private String albumID;
    private String genres;
    private Music obj = new Music(-1);


    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(title, duration, lyrics, albumID, genres)) {
            try{
                obj.setTitle(title);
                obj.setLyrics(lyrics);
                obj.setDuration(inputUtil.StringToInt(duration, "duration"));
                obj.setAlbumID(inputUtil.StringToInt(albumID, "album ID"));
                obj.setGenres(inputUtil.separateBy(genres, ","));
                try {
                    MessageIdentified<Music> rsp = this.getBean().postMusic(obj);
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
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