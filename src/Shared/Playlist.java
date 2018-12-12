package Shared;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Playlist implements Serializable {
    String name;
    int votes;
    boolean isPublic;
    User owner;
    int id;
    List<Integer> songs;
    List<String> users;
    List<Integer> order;
    String details;

    public Playlist(String name, int votes, boolean isPublic, User owner) {
        this.name = name;
        this.votes = votes;
        this.isPublic = isPublic;
        this.owner = owner;
        this.songs = Collections.emptyList();
        this.users = Collections.emptyList();
        this.order = Collections.emptyList();
    }

    public static Playlist newPlaylist(User owner) {
        String name = inputUtil.promptStr("Name: ");
        boolean isPublic = inputUtil.promptYesNo("Is it Public (Y/N): ");
        return new Playlist(name, 0, isPublic, owner);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void addVote() {
        this.votes++;
    }

    public void removeVote() {
        this.votes--;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public List<Integer> getSongs() {
        return this.songs;
    }

    public List<String> getUsers() {
        return this.users;
    }

    public List<Integer> getOrder() {
        return this.order;
    }

    public void edit() {
        // FIXME: edit songs inside the playlist too
        String enter = ") <ENTER> to skip\n>> ";
        setName(inputUtil.oldStrOrNew("Name (Old: "+ getName()+ enter, getName()));

        if (isPublic()) {
            setPublic(!inputUtil.promptYesNo("Turn into private playlist? (Y\\N)"));
        } else {
            setPublic(inputUtil.promptYesNo("Turn into public playlist? (Y\\N)"));
        }
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }
}
