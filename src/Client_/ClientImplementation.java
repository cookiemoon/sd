package Client_;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImplementation extends UnicastRemoteObject implements ClientInterface {
    protected ClientImplementation() throws RemoteException {
        super();
    }

    @Override
    public void newEditor(String granter) throws RemoteException {
        System.out.print(granter + " granted you editor privileges.\n>> ");
    }

    @Override
    public void removeEditor(String remover) throws RemoteException {
        System.out.print(remover + " removed your editor privileges.\n>> ");
    }

    @Override
    public void newAlbumEdit(String editorUsername, String albumName) throws RemoteException {
        System.out.print(editorUsername + " edited Album \"" + albumName + "\"'s description.\n>> ");
    }
}
