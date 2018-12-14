package Shared;

import java.io.Serializable;

public class MessageIdentified<T> implements Serializable {
    private String type;
    private String state;
    private String table;
    private String uuid;
    private int msgid;
    private boolean accepted;
    private String errors;
    private User user;
    private T obj;

    public boolean accepted() {
        return this.accepted;
    }

    public String errors() {
        return this.errors;
    }

    // Used to construct a request
    public MessageIdentified(String type, String state, String table, User user, T obj) {
        this.type = type;
        this.state = state;
        this.user = user;
        this.table = table;
        this.obj = obj;
    }

    public MessageIdentified(String type, String state, User user, T obj) {
        this.type = type;
        this.state = state;
        this.user = user;
        this.obj = obj;
    }

    public MessageIdentified(MessageIdentified<T> req, String uuid, boolean accepted, String errors) {
        this.type = req.getType();
        this.state = "response";
        this.uuid = uuid;
        this.msgid = req.getMsgid();
        this.accepted = accepted;
        this.errors = errors;
        this.obj = null;
    }

    public boolean embedMsgid(int id) {
        this.msgid = id;
        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}