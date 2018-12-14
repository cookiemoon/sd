/**
 * Raul Barbosa 2014-11-07
 */
package client.model;

import java.rmi.Remote;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import RMI.ServerInterface;
import Shared.*;
import com.google.gson.reflect.TypeToken;

public class Bean {
	private ServerInterface server;
	private User user = new User(null, null);
	private Music music = new Music(-1, null);
	private Album album = new Album(-1, null);
	private Artist artist = new Artist(-1, null, null);
	private Review review = new Review(-1, null, null, null);
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public Bean() {
		try {
			server = (ServerInterface) Naming.lookup("rmi://localhost:1100/RMIServer");
		}
		catch(NotBoundException|MalformedURLException|RemoteException e) {
			e.printStackTrace();
		}
	}

	public Message<User> login() throws RemoteException {
		String json = server.userLogin(this.user);
		Message<User> rsp = gson.fromJson(json, new TypeToken<Message<Shared.User>>() {}.getType());
		return rsp;
	}

	public Message<User> register() throws RemoteException {
		String json = server.userRegister(this.user);
		Message<User> rsp = gson.fromJson(json, new TypeToken<Message<Shared.User>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<String> grantPrivilege(String grantee) throws RemoteException {
		String json = server.makeEditor(this.user, grantee);
		MessageIdentified<String> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<User>>() {}.getType());
		return rsp;
	}
	
	public void setUsername(String username) {
		this.user.setEmail(username);
	}
	
	public void setPassword(String password) {
		this.user.setPwd(password);
	}
}
