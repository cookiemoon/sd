package RMI;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import Shared.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.thavam.util.concurrent.BlockingHashMap;
import sun.nio.ch.Net;

// Uses AtomicInteger for the msgid
public class RmiServer extends UnicastRemoteObject implements ServerInterface {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static final long serialVersionUID = 1L;
    static int SERVER_PORT = 1099;
    static int SENDING_PORT = 4321;
    static MulticastListener multListener = null;
    static Registry r;

    // Attributes
//    private ConcurrentHashMap<String, ClientInterface> users;
    private String id = UUID.randomUUID().toString();
    private RespBroker broker;
    private AtomicInteger msgID;

    // Getters
    public String getID() { return id; }

    // Cosntructors
    public RmiServer(RespBroker broker, AtomicInteger msgID) throws RemoteException {
        super();
        this.broker = broker;
        this.msgID = msgID;
    }

    public static void main (String [] args) {
        // Our struct to hold every incoming message
        BlockingHashMap<Integer, String> queue = new BlockingHashMap<>();

        AtomicInteger msgID = new AtomicInteger();
        // Our broker will be the intermediary class to block for every message.
        RespBroker broker = new RespBroker(queue, SERVER_PORT);

        // The listener will listen to the multicast network and put new messages on the queue
        // Only messages with ID are put in the queue
        multListener = new MulticastListener(queue);
        System.setProperty("java.rmi.server.hostname","127.0.0.1");

        RmiServer server;
        try {
            server = new RmiServer(broker, msgID);

            r = LocateRegistry.createRegistry(SERVER_PORT);
            Naming.rebind("RMIServer", server);

            System.out.println("RMIServer " + server.getID() + " ready");
        } catch (RemoteException e) {
            System.out.println("CRASHED " + e.getMessage());
            e.getStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String waitForResponse(String json, int id) {
        NetworkUtil.multicastSend(SENDING_PORT, json);
        return broker.waitForResponse(json, id);
    }

    @Override
    public Message<User> userRegister(User obj) {
        int messageID = msgID.getAndIncrement();

        String json = JSON.messageRequest("register", obj, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Shared.User>>() {}.getType());
    }

    @Override
    public Message<User> userLogin(User obj) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("login", obj, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Shared.User>>() {}.getType());
    }

    @Override
    public MessageIdentified<Music> postMusic(User editor, Music m) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "music", editor, m, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<Music>>() {}.getType());
    }

    @Override
    public MessageIdentified<Artist> postArtist(User editor, Artist a) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "artist", editor, a, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<Artist>>() {}.getType());
    }

    @Override
    public MessageIdentified<Album> postAlbum(User editor, Album album) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "album", editor, album, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<Album>>() {}.getType());
    }

    @Override
    public MessageIdentified<MusicFile> postMusicFile(MusicFile mf) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "files", mf.getOwner(), mf, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<MusicFile>>() {}.getType());
    }

    @Override
    public Message<Review> postReview(Review review) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("review", review, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Review>>() {}.getType());
    }

    @Override
    public Message<List<Album>> searchAlbum(List<String> searchTerms) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("search_album", searchTerms, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<List<Album>>>() {}.getType());
    }

    @Override
    public Message<List<Music>> searchMusic(List<String> searchTerms) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("search_music", searchTerms, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<List<Music>>>() {}.getType());
    }

    @Override
    public Message<List<Artist>> searchArtist(List<String> searchTerms) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("search_artist", searchTerms, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<List<Music>>>() {}.getType());
    }

    @Override
    public MessageIdentified<Album> editAlbum(User self, Album selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("edit_album", "album", self, selected, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<Album>>() {}.getType());
    }

    @Override
    public MessageIdentified<Artist> editArtist(User self, Artist selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("edit_artist", "artist", self, selected, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<Artist>>() {}.getType());
    }

    @Override
    public MessageIdentified<Music> editMusic(User self, Music selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("edit_music", "music", self, selected, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<Music>>() {}.getType());
    }

    @Override
    public Message<Review> editReview(Review review) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("edit_review", review, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Review>>() {}.getType());
    }

    @Override
    public Message<Album> detailsAlbum(Album selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("details_album", selected, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Album>>() {}.getType());
    }

    @Override
    public Message<Artist> detailsArtist(Artist selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("details_artist", selected, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Artist>>() {}.getType());
    }

    @Override
    public Message<Music> detailsMusic(Music selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("details_music", selected, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<Message<Music>>() {}.getType());
    }

    @Override
    public MessageIdentified<String> makeEditor(User self, String grantee) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("make_editor", "null", self, grantee, messageID);

        return gson.fromJson(waitForResponse(json, messageID), new TypeToken<MessageIdentified<String>>() {}.getType());
    }
}