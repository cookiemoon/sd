package client.action;

import Shared.*;
import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchArtistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String term;
    private String type_artist;

    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(term, type_artist)) {
            List<String> terms = new ArrayList<>();
            terms.add(term);
            terms.add(type_artist);
            try {
                Message<List<Artist>> rsp = this.getBean().searchArtist(terms);
                System.out.println(rsp);
                if (rsp.isAccepted()) {
                    this.getBean().setSearchResultsArtist(rsp.getObj());
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
            session.put("error", "Please do not leave any empty fields");
            session.put("back", "menu");
            return INPUT;
        }
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setType_artist(String type_artist) {
        this.type_artist = type_artist;
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

