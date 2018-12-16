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
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import RMI.ServerInterface;
import Shared.*;
import com.google.gson.reflect.TypeToken;

public class Bean {
	private ServerInterface server;

	private User user = new User(null, null);
	private Music music;
	private Album album;
	private Artist artist;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Bean() {
        try {
            server = (ServerInterface) Naming.lookup("rmi://localhost:1100/RMIServer");
        }
        catch(NotBoundException|MalformedURLException|RemoteException e) {
            e.printStackTrace();
        }
    }

	//MUSIC RELATED

    public void setMusic(String musicID) throws RemoteException {
        this.music = getMusic(new Music(Integer.parseInt(musicID))).getObj();
    }

    public MessageIdentified<Music> postMusic(Music obj) throws RemoteException {
        String json = server.postMusic(this.user, obj);
        MessageIdentified<Music> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Music>>() {}.getType());
        return rsp;
    }

    public MessageIdentified<Music> editMusic(Music obj) throws RemoteException {
        String json = server.editMusic(this.user, obj);
        MessageIdentified<Music> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Music>>() {}.getType());
        return rsp;
    }

    public Message<Music> getMusic(Music obj) throws RemoteException {
        String json = server.detailsMusic(obj);
        Message<Music> rsp = gson.fromJson(json, new TypeToken<Message<Shared.Music>>() {}.getType());
        return rsp;
    }

    public String getMusicAlbum() {
        if(this.music != null)
            return this.music.getAlbum();
        return null;
    }

    public String getMusicAlbumID() {
        if(this.music != null)
            return String.valueOf(this.music.getAlbumID());
        return null;
    }

    public String getMusicArtist() {
        if(this.music != null)
            return this.music.getArtist();
        return null;
    }

    public String getMusicArtistID() {
        if(this.music != null)
            return String.valueOf(this.music.getArtistID());
        return null;
    }

	public String getMusicTitle() {
		if(this.music != null)
			return this.music.getTitle();
		return null;
	}

	public String getMusicID() {
		if(this.music != null)
			return String.valueOf(this.music.getID());
		return null;
	}

    public String getMusicLyrics() {
        if(this.music != null)
            return this.music.getLyrics();
        return null;
    }

    public String getMusicDuration() {
        if(this.music != null)
            return String.valueOf(this.music.getDuration());
        return null;
    }

    //ALBUM RELATED

    public void setAlbum(String albumID) throws RemoteException {
        this.album = getAlbum(new Album(Integer.parseInt(albumID))).getObj();
    }

    public MessageIdentified<Album> postAlbum(Album obj) throws RemoteException {
        String json = server.postAlbum(this.user, obj);
        MessageIdentified<Album> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Album>>() {}.getType());
        return rsp;
    }

    public MessageIdentified<Album> editAlbum(Album obj) throws RemoteException {
        String json = server.editAlbum(this.user, obj);
        MessageIdentified<Album> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Album>>() {}.getType());
        return rsp;
    }

    public Message<Album> getAlbum(Album obj) throws RemoteException {
        String json = server.detailsAlbum(obj);
        Message<Album> rsp = gson.fromJson(json, new TypeToken<Message<Shared.Album>>() {}.getType());
        return rsp;
    }

    public String getAlbumArtist() {
        if(this.album!=null)
            return this.album.getArtist();
        return null;
    }

    public String getAlbumArtistID() {
        if(this.album!=null)
            return String.valueOf(this.album.getArtistID());
        return null;
    }

    public String getAlbumTitle() {
        if(this.album != null)
            return this.album.getTitle();
        return null;
    }

    public String getAlbumLabel() {
        if(this.album != null)
            return this.album.getLabel();
        return null;
    }

    public String getAlbumID() {
        if(this.album != null)
            return String.valueOf(this.album.getID());
        return null;
    }

    public String getAlbumDescription() {
        if(this.album != null)
            return this.album.getDescription();
        return null;
    }

    public String getAlbumReleaseDate() {
        if(this.album != null)
            return inputUtil.formatDate(this.album.getReleaseDate());
        return null;
    }

    public String getAlbumMusicID(int index) {
        if(this.album!=null && this.album.getMusicIDs().size() > index)
            return String.valueOf(this.album.getMusicIDs().get(index));
        return null;
    }

    public List<String> getAlbumMusic() {
        if(this.album != null)
            return this.album.getMusicTitles();
        return null;
    }

    //ARTIST RELATED

    public void setArtist(String artistID) throws RemoteException {
        this.artist = getArtist(new Artist(Integer.parseInt(artistID))).getObj();
    }

    public MessageIdentified<Artist> postArtist(Artist obj) throws RemoteException {
        String json = server.postArtist(this.user, obj);
        MessageIdentified<Artist> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Artist>>() {}.getType());
        return rsp;
    }

    public MessageIdentified<Artist> editArtist(Artist obj) throws RemoteException {
        String json = server.editArtist(this.user, obj);
        MessageIdentified<Artist> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Artist>>() {}.getType());
        return rsp;
    }

    public Message<Artist> getArtist(Artist obj) throws RemoteException {
        String json = server.detailsArtist(obj);
        Message<Artist> rsp = gson.fromJson(json, new TypeToken<Message<Shared.Artist>>() {}.getType());
        return rsp;
    }

    public String getArtistName() {
        if(this.artist != null)
            return this.artist.getName();
        return null;
    }

    public String getArtistID() {
        if(this.artist != null)
            return String.valueOf(this.artist.getID());
        return null;
    }

    public String getArtistDescription() {
        if(this.artist != null)
            return this.artist.getDescription();
        return null;
    }

    public String getArtistAlbumID(int index) {
        if(this.artist!=null && this.artist.getAlbumIDs().size() > index)
            return String.valueOf(this.artist.getAlbumIDs().get(index));
        return null;
    }

    public List<String> getArtistAlbum() {
        if(this.artist != null)
            return this.artist.getAlbumTitles();
        return null;
    }

    //USER RELATED

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
	
	public void setUsername(String username) {
		this.user.setEmail(username);
	}
	
	public void setPassword(String password) {
		this.user.setPwd(password);
	}

}
