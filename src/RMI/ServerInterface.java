package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Shared.*;

// String json cause its much more performant if the client processes the json and the RMI just embeds it with the msgid
// AtomicInteger 
// should the interface check if the json the user send is valid?
public interface ServerInterface extends Remote {
    Message<User> userRegister(User obj) throws RemoteException;
    Message<User> userLogin(User obj) throws RemoteException;

    // Post
    MessageIdentified<Music>        postMusic(User editor, Music m) throws RemoteException;
    MessageIdentified<Artist>       postArtist(User editor, Artist a) throws RemoteException;
    MessageIdentified<Album>        postAlbum(User self, Album album) throws RemoteException;
    MessageIdentified<MusicFile>    postMusicFile(MusicFile mf) throws RemoteException;

    // Minor Exception
    Message<Review> postReview(Review review) throws RemoteException;

    // Search
    Message<List<Album>>    searchAlbum(List<String> searchTerms) throws RemoteException;
    Message<List<Music>>    searchMusic(List<String> promptListStr) throws RemoteException;
    Message<List<Artist>>   searchArtist(List<String> searchTerms) throws RemoteException;

    // Edit
    MessageIdentified<Album>    editAlbum(User self, Album selected) throws RemoteException;
    MessageIdentified<Artist>   editArtist(User self, Artist selected) throws RemoteException;
    MessageIdentified<Music>    editMusic(User self, Music selected) throws RemoteException;

    // Minor Exception
    Message<Review> editReview(Review review) throws RemoteException;

    // Details
    Message<Album>      detailsAlbum(Album selected) throws RemoteException;
    Message<Artist>     detailsArtist(Artist selected) throws RemoteException;
    Message<Music>      detailsMusic(Music selected) throws RemoteException;

    // Other
    MessageIdentified<String> makeEditor(User self, String grantee) throws RemoteException;

}