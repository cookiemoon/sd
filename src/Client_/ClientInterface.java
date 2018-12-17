package Client_;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void newEditor    (String granter)                          throws RemoteException;
    void removeEditor (String granter)                          throws RemoteException;
    void newAlbumEdit (String editorUsername, String albumName) throws RemoteException;
}
