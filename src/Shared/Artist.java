package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Artist implements Serializable {
    int id;
    String name;
    List<Artist> artists;
    List<Calendar> period;
    String details;
    private Artist old;
    private List<String> albums = new ArrayList<>();
    private List<Integer> albumIDs = new ArrayList<>();
    private List<String> editors = new ArrayList<>();

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    private boolean edited = false;

    public Artist(int id, String name, String description, List<Calendar> period) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.period = period;
        this.artists = Collections.emptyList();
    }

    public Artist(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Artist(int id) {
        this.id = id;
        this.name = null;
        this.description = null;
    }

    public static Artist newGroup() {
        String name = inputUtil.promptStr("Name: ");
        String desc = inputUtil.promptStr("Description: ");
        List<Calendar> period = new ArrayList<>();
        do {
            period.add(inputUtil.promptDate("=====\nStart\n=====", false));
            period.add(inputUtil.promptDate("=====\nEnd\n=====", true));
        } while (inputUtil.promptYesNo("Add another period? (Y\\N)"));
        return new Artist(-1, name, desc, period);
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Artist> getArtists() { return this.artists; }

    public List<Calendar> getPeriod() { return this.period; }

    String description;

    public void edit() {
        this.old = this;
        setName(inputUtil.promptStr("Name: "));
        setDescription(inputUtil.promptStr("Description: "));
    }

    public void addAlbum(Album m) {
        this.albums.add(m.getTitle());
        this.albumIDs.add(m.getID());
    }

    public List<String> getAlbumTitles () { return this.albums; }

    public List<Integer> getAlbumIDs () { return this.albumIDs; }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public Artist getOld() {
        return this.old;
    }

    public void setPeriod(List<Calendar> period) {
        this.period = period;
    }

    public void addEditor(String users_email) {
        this.editors.add(users_email);
    }

    public void setOld(Artist artist) {
        this.old = artist;
    }
}
