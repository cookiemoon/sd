package Shared;

import java.io.Serializable;

public class Genre implements Serializable {
    String name;
    String description;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Genre newGenre() {
        String name = inputUtil.promptStr("Name: ");
        String desc = inputUtil.promptStr("Desc: ");
        return new Genre(name, desc);
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
}
