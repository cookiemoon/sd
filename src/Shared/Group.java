package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Group implements Serializable {
    int id;
    String name;
    List<Artist> artists;
    List<Calendar> period;
    String details;

    public Group(int id, String name, String description, List<Calendar> period) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.period = period;
        this.artists = Collections.emptyList();
    }

    public static Group newGroup() {
        String name = inputUtil.promptStr("Name: ");
        String desc = inputUtil.promptStr("Description: ");
        List<Calendar> period = new ArrayList<>();
        do {
            period.add(inputUtil.promptDate("=====\nStart\n=====", false));
            period.add(inputUtil.promptDate("=====\nEnd\n=====", true));
        } while (inputUtil.promptYesNo("Add another period? (Y\\N)"));
        return new Group(-1, name, desc, period);
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
        setName(inputUtil.promptStr("Name: "));
        setDescription(inputUtil.promptStr("Description: "));
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return this.details;
    }
}
