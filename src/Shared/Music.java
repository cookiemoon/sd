package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Music implements Serializable {
    private int id;
    private int duration;
    private String title;
    private String lyrics;
    private String album;
    private List<String> genres;
    private String details;
    private int albumID;
    private int artistID;
    private Music old;
    private String artist;

    public Music(int id, String title, int duration, String lyrics) {
        this.duration = duration;
        this.title = title;
        this.lyrics = lyrics;
        this.id = id;
    }

    public Music(int id) {
        this.id = id;
        this.title = null;
        this.duration = -1;
        this.lyrics = null;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String albumName) {
        this.album = albumName;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void addGenre(String genre) {
        this.genres.add(genre);
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public Music getOld() { return this.old; }

    public void setOld(Music music) {
        this.old = music;
    }
}
