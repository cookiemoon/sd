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
    private List<String> review = new ArrayList<>();
    private List<Integer> reviewScore = new ArrayList<>();
    private List<String> reviewUser = new ArrayList<>();
    private int artistID;
    private String artist;
    private String details;
    private Album old;

    public static Album getInstance() {
        int artistID = inputUtil.promptInt("Album ID: ");
        String title = inputUtil.promptStr("Title: ");
        String desc = inputUtil.promptStr("Description: ");
        String label = inputUtil.promptStr("Label: ");
        Calendar releaseDate = inputUtil.promptDate("Release date: ", false);
        Album res = new Album(-1, title, desc, releaseDate, label);
        res.setArtistID(artistID);
        return res;
    }

    public static Album editInstance(int albumID) {
        String title = inputUtil.promptStr("New title: ");
        String desc = inputUtil.promptStr("New description: ");
        String label = inputUtil.promptStr("New label: ");
        Calendar releaseDate = inputUtil.promptDate("New release date: ", false);
        Album res = new Album(albumID, title, desc, releaseDate, label);
        return res;
    }

    public List<String> getReview() {
        return review;
    }

    public void addReview(String review) {
        this.review.add(review);
    }

    public List<Integer> getReviewScore() {
        return reviewScore;
    }

    public void addReviewScore(int reviewScore) {
        this.reviewScore.add(reviewScore);
    }

    public List<String> getReviewUser() {
        return reviewUser;
    }

    public void addReviewUser(String reviewUser) {
        this.reviewUser.add(reviewUser);
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

    public float avgScore() {
        float total=0, count=0;
        for (Review r: this.reviews) {
            total += r.getScore();
            count++;
        }
        return total/count;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void addReview(Review m) { this.reviews.add(m); }

    public void addGenre(String genre) { this.genres.add(genre); }

    public List<Review> getReviews () { return this.reviews; }

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
        int cnt = 1;
        this.details = "Title: "+this.title
                        + "\nArtist: " + this.artist
                        + "\nRelease date: " + inputUtil.formatDate(releaseDate)
                        + "\nDescription:\n" + this.description
                        + "\nMusic:\n\n";
        for (String music : musicTitles) {
            this.details = this.details + String.valueOf(cnt) + ". " + music + "\n";
            cnt++;
        }
        this.details = this.details + "Reviews:\n";
        for (Review r : reviews) {
            this.details = this.details + "\nReviewer: " + r.getReviewer().getEmail()
                            + "\nScore: " + String.valueOf(r.getScore())
                            + "\nReview:\n" + r.getReviewText() + "\n";
        }
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

    public void setReviews() {
        for (Review r : reviews) {
            this.review.add(r.getReviewText());
            this.reviewScore.add(r.getScore());
            this.reviewUser.add(r.getReviewer().getEmail());
        }
    }
}
