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
		MessageIdentified<String> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<String>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<Artist> postArtist(Artist obj) throws RemoteException {
		String json = server.postArtist(this.user, obj);
		MessageIdentified<Artist> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Artist>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<Music> postMusic(Music obj) throws RemoteException {
		String json = server.postMusic(this.user, obj);
		MessageIdentified<Music> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Music>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<Album> postAlbum(Album obj) throws RemoteException {
		String json = server.postAlbum(this.user, obj);
		MessageIdentified<Album> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Album>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<Artist> editArtist(Artist obj) throws RemoteException {
		String json = server.editArtist(this.user, obj);
		MessageIdentified<Artist> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Artist>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<Music> editMusic(Music obj) throws RemoteException {
		String json = server.editMusic(this.user, obj);
		MessageIdentified<Music> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Music>>() {}.getType());
		return rsp;
	}

	public MessageIdentified<Album> editAlbum(Album obj) throws RemoteException {
		String json = server.editAlbum(this.user, obj);
		MessageIdentified<Album> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Album>>() {}.getType());
		return rsp;
	}
	
	public void setUsername(String username) {
		this.user.setEmail(username);
	}
	
	public void setPassword(String password) {
		this.user.setPwd(password);
	}


}