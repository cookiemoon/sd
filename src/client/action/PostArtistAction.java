package client.action;

import client.model.Bean;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import Shared.*;

public class PostArtistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String name;
    private String desc;
    private String start;
    private String end;
    private Calendar startDate;
    private Calendar endDate;
    private Artist obj = new Artist(-1, "", "");


    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(name, desc, start, end)) {
            obj.setName(name);
            obj.setDescription(desc);
            List<Calendar> period = new ArrayList<>();
            period.add(inputUtil.toCalendar(start));
            period.add(inputUtil.toCalendar(end));
            obj.setPeriod(period);
            try {
                MessageIdentified<Artist> rsp = this.getBean().postArtist(obj);
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
            session.put("error", "Please do not leave any empty fields");
            session.put("back", "menu");
            return INPUT;
        }
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setStart(String start) {
        this.start = start;
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