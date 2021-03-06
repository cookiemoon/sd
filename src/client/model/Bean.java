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

	private List<Music> searchResultsMusic;
	private List<Album> searchResultsAlbum;
	private List<Artist> searchResultsArtist;

	private List<String> searchResultNames = new ArrayList<>();
	private List<Integer> searchResultIDs = new ArrayList<>();

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

    public Message<List<Music>> searchMusic(List<String> obj) throws RemoteException {
        String json = server.searchMusic(obj);
        Message<List<Music>> rsp = gson.fromJson(json, new TypeToken<Message<List<Shared.Music>>>() {}.getType());
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

    public List<String> getMusicGenres() {
        return this.music.getGenres();
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

    public Message<List<Album>> searchAlbum(List<String> obj) throws RemoteException {
        String json = server.searchAlbum(obj);
        Message<List<Album>> rsp = gson.fromJson(json, new TypeToken<Message<List<Shared.Album>>>() {}.getType());
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

    public List<String> getAlbumGenres() {
        return this.album.getGenres();
    }

    public String getAlbumScore() {
        return String.valueOf(album.avgScore());
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

    public Message<List<Artist>> searchArtist(List<String> obj) throws RemoteException {
        String json = server.searchArtist(obj);
        Message<List<Artist>> rsp = gson.fromJson(json, new TypeToken<Message<List<Shared.Artist>>>() {}.getType());
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

    public boolean artistHasNoContent() {
        if(this.artist!=null) {
            if(this.artist.getAlbumTitles().size() == 0)
                return true;
        }
        return false;
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

	public MessageIdentified<String> associateDropbox (String code) throws RemoteException {
        String json = server.associateDropbox(this.user, code);
        MessageIdentified<String> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<String>>() {}.getType());
        return rsp;
    }
	
	public void setUsername(String username) {
		this.user.setEmail(username);
	}
	
	public void setPassword(String password) {
		this.user.setPwd(password);
	}

	//REVIEW

    public Message<Review> postReview(Review obj) throws RemoteException {
        obj.setReviewer(this.user);
        obj.setReviewed(this.album);
        String json = server.postReview(obj);
        Message<Review> rsp = gson.fromJson(json, new TypeToken<Message<Review>>() {}.getType());
        return rsp;
    }

    public List<String> getAlbumReview() {
        this.album.setReviews();
        return this.album.getReview();
    }

    public String getReviewScore(int index) {
        return String.valueOf(this.album.getReviewScore().get(index));
    }

    public String getReviewer(int index) {
        return String.valueOf(this.album.getReviewUser().get(index));
    }

    //SEARCH RESULTS

    public List<String> getSearchResultNames() {
        return searchResultNames;
    }

    public String getSearchResultID(int index) {
        if(this.searchResultIDs.size()>index)
            return String.valueOf(searchResultIDs.get(index));
        return null;
    }

    public String getAlbumResultArtist(int index) {
        if(this.searchResultsAlbum.size()>index)
            return this.searchResultsAlbum.get(index).getArtist();
        return null;
    }

    public String getMusicResultArtist(int index) {
        if(this.searchResultsMusic.size()>index)
            return this.searchResultsMusic.get(index).getArtist();
        return null;
    }

    public String getMusicResultAlbum(int index) {
        if(this.searchResultsMusic.size()>index)
            return this.searchResultsMusic.get(index).getAlbum();
        return null;
    }

    public void setSearchResultsMusic (List<Music> list) {
        this.searchResultNames.clear();
        this.searchResultIDs.clear();
        this.searchResultsMusic = list;
        for(Music m : list) {
            this.searchResultNames.add(m.getTitle());
            this.searchResultIDs.add(m.getID());
        }
    }

    public void setSearchResultsAlbum (List<Album> list) {
        this.searchResultNames.clear();
        this.searchResultIDs.clear();
        this.searchResultsAlbum = list;
        for(Album a: list) {
            this.searchResultNames.add(a.getTitle());
            this.searchResultIDs.add(a.getID());
        }
    }

    public void setSearchResultsArtist (List<Artist> list) {
        this.searchResultNames.clear();
        this.searchResultIDs.clear();
        this.searchResultsArtist = list;
        for(Artist a : list) {
            this.searchResultNames.add(a.getName());
            this.searchResultIDs.add(a.getID());
        }
    }

    //MISC

    public MessageIdentified<Artist> removeArtist() throws RemoteException {
        String json = server.removeArtist(this.user, this.artist);
        MessageIdentified<Artist> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<Shared.Artist>>() {}.getType());
        return rsp;
    }
}
