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

public class ReviewAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String score;
    private String review;
    private Review obj = new Review(-1, "", null, null);


    @Override
    public String execute() {
        if(inputUtil.notEmptyOrNull(score, review)) {
            obj.setScore(Integer.parseInt(score));
            obj.setReviewText(review);
            try {
                Message<Review> rsp = this.getBean().postReview(obj);
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

    public void setScore(String score) {
        this.score = score;
    }

    public void setReview(String review) {
        this.review = review;
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