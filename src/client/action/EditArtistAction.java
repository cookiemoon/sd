package client.action;

import Shared.*;
import Shared.inputUtil;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class EditArtistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String artistID;
    private String name = "";
    private String desc = "";
    private Artist obj = new Artist(-1, "", "");

    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(artistID)) {
            obj.setName(name);
            obj.setDescription(desc);
            obj.setId(Integer.parseInt(artistID));
            obj.setOld(new Artist(Integer.parseInt(artistID), "", ""));
            try {
                MessageIdentified<Artist> rsp = this.getBean().editArtist(obj);
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
        } else {
            session.put("error", "Please do not leave ID field empty.");
            session.put("back", "menu");
            return INPUT;
        }
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
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
