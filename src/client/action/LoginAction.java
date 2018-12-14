/**
 * Raul Barbosa 2014-11-07
 */
package client.action;

import Shared.Message;
import Shared.User;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;
import client.model.UserBean;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;
	private String error = null;

	@Override
	public String execute() {
		System.out.println(this.username);
		if(this.username != null && !username.equals("")) {
			this.getUserBean().setUsername(this.username);
			this.getUserBean().setPassword(this.password);
			try {
				Message<User> rsp = this.getUserBean().login();
				if (rsp.isAccepted()) {
					session.put("username", username);
					session.put("loggedin", true); // this marks the user as logged in
					session.put("editor", rsp.getObj().isEditor_f());
					return SUCCESS;
				} else {
					this.error = rsp.getErrors();
					return LOGIN;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return LOGIN;
		}
		else {
			return SUCCESS;
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBean getUserBean() {
		if(!session.containsKey("userBean"))
			this.setUserBean(new UserBean());
		return (UserBean) session.get("userBean");
	}

	public void setUserBean(UserBean userBean) {
		this.session.put("userBean", userBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
