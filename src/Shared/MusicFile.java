package Shared;

import java.io.Serializable;
import java.util.List;

public class MusicFile implements Serializable {
    User owner;
    Music m;
    List<String> users;
    String details;

    public MusicFile(User owner, Music m) {
        this.owner = owner;
        this.m = m;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Music getM() {
        return m;
    }

    public void setM(Music m) {
        this.m = m;
    }

    public int getMusicID() { return this.m.getID(); }

    public List<String> getUsers() {
        return users;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }
}
