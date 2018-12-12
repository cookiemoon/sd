package Shared;

import java.io.Serializable;

public class Label implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    String description;

    public static Label newLabel() {
        String name = inputUtil.promptStr("Name: ");
        String desc = inputUtil.promptStr("Desc: ");
        return new Label(name, desc);
    }

    public Label(String name, String description) {
        this.name = name;
        this.description = description;
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
