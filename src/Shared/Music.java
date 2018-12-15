package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Music implements Serializable {
    int id;
    int duration;
    String title;
    String lyrics;
    String groupName;
    String albumName;
    List<String> genres;
    String details;
    List<Integer> album_id = new ArrayList<>();
    Music old;

    public Music(int duration, String title, String lyrics, String groupName, String albumName, List<String> genres) {
        this.duration = duration;
        this.title = title;
        this.lyrics = lyrics;
        this.groupName = groupName;
        this.albumName = albumName;
        this.genres = genres;
    }

    public Music(int id, String title, int duration, String lyrics) {
        this.duration = duration;
        this.title = title;
        this.lyrics = lyrics;
        this.id = id;
    }

    static public Music newMusic() {
        int duration = inputUtil.promptIntBound(9999999, 0, "Duration: ", false);
        String title = inputUtil.promptStr("Title: ");
        String lyrics = inputUtil.promptStr("Lyrics: ");
        String groupName = inputUtil.promptStr("Group Name: ");
        String albumName = inputUtil.promptStr("Album Name: ");
        List<String> genres = inputUtil.separateBy(inputUtil.promptStr("Genres (separate with commas \",\"): "), ",");
        return new Music (duration, title, lyrics, groupName, albumName, genres);
    }

    public Music(int id, int duration, String title, String lyrics, String groupName, String albumName, List<String> genres) {
        this.id = id;
        this.duration = duration;
        this.title = title;
        this.lyrics = lyrics;
        this.groupName = groupName;
        this.albumName = albumName;
        this.genres = genres;
    }

    public Music(int id, String title) {
        this.id = id;
        this.title = title;
        this.duration = -1;
        this.lyrics = null;
    }

    public int getID() {
        return id;
    }

    public void addAlbum_ID(int album_id) { this.album_id.add(album_id); }

    public List<Integer> getAlbum_ID() { return this.album_id; }

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void edit() {
        this.old = this;
        String enter = ") <ENTER> to skip\n>> ";
        setTitle(inputUtil.oldStrOrNew("Title (Old: "+getTitle()+ enter, getTitle()));
        setLyrics(inputUtil.oldStrOrNew("Lyrics <ENTER> to skip: \n>> ", getLyrics()));
        setGroupName(inputUtil.oldStrOrNew("Group Name (" + getGroupName() + enter, getGroupName()));
        setAlbumName(inputUtil.oldStrOrNew("Album Name (" + getAlbumName() + enter, getAlbumName()));
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public Music getOld() { return this.old; }

    public void setAlbum_id(int album_id) {
        this.album_id.add(album_id);
    }
}
