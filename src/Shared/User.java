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
    private boolean editor_f;
    private boolean admin_f;
    private boolean isHashed;

    public static User newUser() {
        String username = inputUtil.promptStr("Enter your username: ");
        String password = inputUtil.promptStr("Enter password: ");
        return new User(username, password);
    }


    public User(String email, String pwd, String sesh_hash, boolean editor_f, boolean admin_f) {
        this.email = email;
        this.pwd = pwd;
        this.sesh_hash = sesh_hash;
        this.editor_f = editor_f;
        this.admin_f = admin_f;
        this.isHashed = false;
    }

    public User(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
        this.sesh_hash = null;
        this.editor_f = false;
        this.admin_f = false;
        this.isHashed = false;
    }

    public User(String email, String pwd, boolean editor_f ) {
        this.email = email;
        this.pwd = pwd;
        this.editor_f = editor_f;
        this.isHashed = true;
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

    public String getHashedPwd() {
        if (this.isHashed)
            return this.pwd;
        else
            return inputUtil.hashedPass(pwd);
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

    public boolean isEditor_f() {
        return editor_f;
    }

    public void setEditor_f(boolean editor_f) {
        this.editor_f = editor_f;
    }

    public boolean isAdmin_f() {
        return admin_f;
    }

    public void setAdmin_f(boolean admin_f) {
        this.admin_f = admin_f;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", sesh_hash='" + sesh_hash + '\'' +
                ", editor_f=" + editor_f +
                ", admin_f=" + admin_f +
                '}';
    }
}