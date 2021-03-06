package Shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Session is a SHA256 randomly generated hash
 */
public class User implements Serializable {
    static final long serialVersionUID = 1L;
    private String email;
    private String pwd;
    private String sesh_hash;
    private String dropboxToken;
    private boolean editor;

    public static User newUser() {
        String username = inputUtil.promptStr("Enter your username: ");
        String password = inputUtil.promptStr("Enter password: ");
        return new User(username, password);
    }


    public User(String email, String pwd, String sesh_hash, boolean editor) {
        this.email = email;
        this.pwd = pwd;
        this.sesh_hash = sesh_hash;
        this.editor = editor;
    }

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
        this.sesh_hash = null;
        this.editor = false;
    }

    public User(String email, String pwd, boolean editor ) {
        this.email = email;
        this.pwd = pwd;
        this.editor = editor;
    }

    public String getDropboxToken() {
        return dropboxToken;
    }

    public void setDropboxToken(String dropboxToken) {
        this.dropboxToken = dropboxToken;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSesh_hash() {
        return sesh_hash;
    }

    public void setSesh_hash(String sesh_hash) {
        this.sesh_hash = sesh_hash;
    }

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sesh_hash='" + sesh_hash + '\'' +
                ", editor=" + editor +
                '}';
    }
}