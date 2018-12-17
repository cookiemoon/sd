package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Shared.*;

// String json cause its much more performant if the client processes the json and the RMI just embeds it with the msgid
// AtomicInteger 
// should the interface check if the json the user send is valid?
public interface ServerInterface extends Remote {
    String  userRegister(User obj) throws RemoteException;
    String  userLogin(User obj) throws RemoteException;

    // Post
    String  postMusic(User editor, Music m) throws RemoteException;
    String  postArtist(User editor, Artist a) throws RemoteException;
    String  postAlbum(User self, Album album) throws RemoteException;
    String  postMusicFile(MusicFile mf) throws RemoteException;

    // Minor Exception
    String  postReview(Review review) throws RemoteException;

    // Search
    String  searchAlbum(List<String> searchTerms) throws RemoteException;
    String  searchMusic(List<String> searchTerms) throws RemoteException;
    String  searchArtist(List<String> searchTerms) throws RemoteException;

    // Edit
    String  editAlbum(User self, Album selected) throws RemoteException;
    String  editArtist(User self, Artist selected) throws RemoteException;
    String  editMusic(User self, Music selected) throws RemoteException;

    // Minor Exception
    String  editReview(Review review) throws RemoteException;

    // Details
    String  detailsAlbum(Album selected) throws RemoteException;
    String  detailsArtist(Artist selected) throws RemoteException;
    String  detailsMusic(Music selected) throws RemoteException;

    // Other
    String makeEditor(User self, String grantee) throws RemoteException;
    String removeArtist(User self, Artist selected) throws RemoteException;

}