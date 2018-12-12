package Shared;

import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.Arrays;

public class Message<T> implements Serializable {
    @Expose
    private String type;
    @Expose
    private String state;
    @Expose
    private String uuid;
    @Expose
    private int msgid;
    @Expose
    private boolean accepted;
    @Expose
    private String [] errors;
    @Expose
    private T obj;


    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public String getUuid() {
        return uuid;
    }

    public int getMsgid() {
        return msgid;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getErrors() {
        return errors[0];
    }

    // Used to construct a request
    public Message(String type, String state, T obj) {
        this.type = type;
        this.state = state;
        this.obj = obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    /**
     * Used as a response to a req.
     * @param req The message request to which this object will respond
     * @param uuid The UUID from the dataserver. Serves to identify which server responded
     * @param accepted If the message got accepted or not
     * @param errors If it didn't got accepted, the errors make the problems explicit
     */
    public Message(Message<T> req, String uuid, boolean accepted, String[] errors) {
        this.type = req.getType();
        this.state = "response";
        this.uuid = uuid;
        this.msgid = req.getMsgid();
        this.accepted = accepted;
        this.errors = errors;
        this.obj = req.getObj();
    }

    public Message(Message<T> req, String uuid, boolean accepted, String[] errors, T u) {
        this.type = req.getType();
        this.state = "response";
        this.uuid = uuid;
        this.msgid = req.getMsgid();
        this.accepted = accepted;
        this.errors = errors;
        this.obj = u;
    }

    public boolean embedMsgid(int id) {
        this.msgid = id;
        return true;
    }

    public T getObj() {
        return this.obj;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", uuid='" + uuid + '\'' +
                ", msgid=" + msgid +
                ", accepted=" + accepted +
                ", errors=" + Arrays.toString(errors) +
                ", obj=" + obj +
                '}';
    }
}