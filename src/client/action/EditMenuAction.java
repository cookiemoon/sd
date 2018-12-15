package client.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class EditMenuAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String multiplex;

    @Override
    public String execute() {
        return multiplex;
    }

    public void setMultiplex(String multiplex) {
        this.multiplex = multiplex;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
