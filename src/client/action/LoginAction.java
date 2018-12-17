/**
 * Raul Barbosa 2014-11-07
 */
package client.action;

import REST.DropBoxApi2;
import Shared.Message;
import Shared.User;
import client.model.Bean;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuthService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;
	private static final String API_APP_KEY = "xrd0027mffnnfuq";
	private static final String API_APP_SECRET = "d31u56fva1fxg40";
	private static final String CALLBACK_URL = "http://localhost:8080/Hey/dropbox";

	@Override
	public String execute() {
		if(this.username != null && !username.equals("")) {
			this.getBean().setUsername(this.username);
			this.getBean().setPassword(this.password);
			try {
				Message<User> rsp = this.getBean().login();
				System.out.println("Printing login rsp");
				System.out.println(rsp);
				if (rsp.isAccepted()) {
					session.put("username", username);
					session.put("loggedin", true); // this marks the user as logged in
					session.put("editor", rsp.getObj().isEditor());

					OAuthService service = new ServiceBuilder()
							.provider(DropBoxApi2.class)
							.apiKey(API_APP_KEY)
							.apiSecret(API_APP_SECRET)
							.callback(CALLBACK_URL)
							.build();

					session.put("dropbox_auth_url", service.getAuthorizationUrl(null));
					session.put("dropbox_associated", false);
					session.put("dropbox_token", "");
					return SUCCESS;
				} else {
					session.put("error", rsp.getErrors());
					session.put("back", "login");
					return LOGIN;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			session.put("error", "Server error.");
			session.put("back", "login");
			return LOGIN;
		}
		else {
			session.put("error", "Please do not leave any empty fields.");
			session.put("back", "login");
			return INPUT;
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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
