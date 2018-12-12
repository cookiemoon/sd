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

import RMI.ServerInterface;
import Shared.Message;
import Shared.MessageIdentified;
import Shared.User;
import rmiserver.RMIServerInterface;

public class UserBean {
	private ServerInterface server;
	private User user;

	public UserBean() {
		try {
			server = (ServerInterface) Naming.lookup("rmi://localhost:1100/server");
			this.user = new User(null, null);
		}
		catch(NotBoundException|MalformedURLException|RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}

	public boolean login() throws RemoteException {
		Message<User> rsp = server.userLogin(this.user);
		return rsp.isAccepted();
	}

	public boolean register() throws RemoteException {
		Message<User> rsp = server.userRegister(this.user);
		return rsp.isAccepted();
	}

	public boolean grantPrivilege(String grantee) throws RemoteException {
		MessageIdentified<String> rsp = server.makeEditor(this.user, grantee);
		return rsp.isAccepted();
	}
	
	public void setUsername(String username) {
		this.user.setEmail(username);
	}
	
	public void setPassword(String password) {
		this.user.setPwd(password);
	}
}
