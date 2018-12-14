package Shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

public class JSON {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> String messageRequest(String type, T obj, int id) {
        Message<T> msg = new Message(type, "request", obj);
        msg.embedMsgid(id);
        return gson.toJson(msg);
    }

    public static <T> String messageResponse(Message<T> req, String uuid, boolean accepted, String errors, T obj) {
        req.setObj(null);
        req.setState("response");
        return gson.toJson(new Message<>(req, uuid, accepted, errors, obj));
    }

    public static <T> String messageIdRequest(String type, String table, User user, T obj, int id) {
        MessageIdentified<T> msg = new MessageIdentified<>(type, "request", table, user, obj);
        msg.embedMsgid(id);
        return gson.toJson(msg);
    }

    public static <T> String messageIdResponse(MessageIdentified<T> req, String uuid, boolean accepted, String errors) {
        req.setObj(null);
        req.setState("response");
        return gson.toJson(new MessageIdentified<>(req, uuid, accepted, errors));
    }
}
