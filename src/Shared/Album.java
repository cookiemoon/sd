package Shared;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Album implements Serializable {
    private int id;
    private String title;
    private String description;
    private String label;
    private Calendar releaseDate;
    private List<Integer> musicIDs = new ArrayList<>();
    private List<String> musicTitles = new ArrayList<>();
    private int artistID;
    private String artist;
    private String details;
    private Album old;

    public List<Review> getReviews() {
        return reviews;
    }

    public List<String> getEditors() {
        return editors;
    }

    private List<String> genres = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private List<String> editors = new ArrayList<>();
    private boolean edited = false;
    static final long serialVersionUID = 420L;

    public Album(int id) {
        this.id = id;
        this.title = null;
    }

    public Album(int id, String title, String description, Calendar releaseDate, String label) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.label = label;
    }

    public void addMusic(Music m) {
        this.musicTitles.add(m.getTitle());
        this.musicIDs.add(m.getID());
    }

    public void addReview(Review m) { this.reviews.add(m); }

    public void addGenre(String genre) { this.genres.add(genre); }

    public List<Review> getReview () { return this.reviews; }

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

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }

    public List<String> getGenres() {
        return this.genres;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getArtistID() {
        return this.artistID;
    }

    public String getArtist() {
        return this.artist;
    }

    public Album getOld() {
        return this.old;
    }

    public void addEditor(String users_email) {
        this.editors.add(users_email);
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setOld(Album album) {
        this.old = album;
    }

    public List<String> getMusicTitles() {
        return this.musicTitles;
    }

    public List<Integer> getMusicIDs() {
        return this.musicIDs;
    }
}
