package Shared;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Album implements Serializable {
    int id;
    String title;
    String description;
    String groupName;
    String prevKey;
    String label;
    Calendar releaseDate;
    List<Integer> musicIDs;
    List<String> genres;
    static final long serialVersionUID = 420L;
    String details;
    private List<Integer> artist_id;
    private Album old;

    public Album(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Album(int id, String title, String description, Calendar releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public Album(int id, String title, String description, String groupName, Calendar releaseDate, String label) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.groupName = groupName;
        this.releaseDate = releaseDate;
        this.label = label;
    }

    public static Album newAlbum() {
        String title = inputUtil.promptStr("Title:");
        String desc = inputUtil.promptStr("Description: ");
        String groupName = inputUtil.promptStr("Group name: ");
        Calendar release = inputUtil.promptDate("Release date", false);
        String label = inputUtil.promptStr("Label: ");
        return new Album(-1, title, desc, groupName, release, label);
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Integer> getMusicIDs() { return this.musicIDs; }

    public void edit() {
        // FIXME: in depth edit
        this.old = this;
        String enter = ") <ENTER> to skip\n>> ";
        setTitle(inputUtil.oldStrOrNew("Title (Old: "+getTitle()+ enter, getTitle()));
        setDescription(inputUtil.oldStrOrNew("Description <ENTER> to skip: \n>> ", getDescription()));
        setGroupName(inputUtil.oldStrOrNew("Group Name (" + getGroupName() + enter, getGroupName()));

        boolean changeReleaseDate = inputUtil.promptYesNo("Change release date (Old: " + inputUtil.formatDate(getReleaseDate()) + ")? (Y/N)\n>> ");
        if(changeReleaseDate)
            setReleaseDate(inputUtil.promptDate("New Release Date: ", false));
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public void setMusicIDs(List<Integer> musicIDs) {
        this.musicIDs = musicIDs;
    }

    public List<String> getGenres() {
        return this.genres;
    }

    public void addArtist_ID(int artist_id) { this.artist_id.add(artist_id); }

    public List<Integer> getArtist_ID() {
        return this.artist_id;
    }

    public Album getOld() {
        return this.old;
    }
}
