package client.action;

import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import Shared.*;

import java.rmi.RemoteException;
import java.util.Map;

public class DetailsMusicAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String musicID;

    @Override
    public String execute() {
        try {
            Music obj = new Music(Integer.parseInt(musicID), "");
            Message<Music> rsp = this.getBean().getMusic(obj);
            if (rsp.isAccepted()) {
                this.getBean().setMusic(obj);
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

    public void setMusicID(String musicID) {
        this.musicID = musicID;
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
