package client.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class DropboxAction extends ActionSupport implements SessionAware {
    private Map<String, Object> session;
    private String code;

    @Override
    public String execute() {
        System.out.println(code);
        // Check how to get the thing from the url
        // ?code=09129391230
        session.put("dropbox_token", this.code);
        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
