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

// Uses AtomicInteger for the msgid
public class RmiServer extends UnicastRemoteObject implements ServerInterface {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static final long serialVersionUID = 1L;
    static int SERVER_PORT = 1100;
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

    // Constructors
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
            r.rebind("RMIServer", server);

            System.out.println("RMIServer " + server.getID() + " ready");
        } catch (RemoteException e) {
            System.out.println("CRASHED " + e.getMessage());
            e.getStackTrace();
        }
    }

    private String waitForResponse(String json, int id) {
        NetworkUtil.multicastSend(SENDING_PORT, json);
        return broker.waitForResponse(json, id);
    }

    @Override
    public String userRegister(User obj) {
        int messageID = msgID.getAndIncrement();

        String json = JSON.messageRequest("register", obj, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String userLogin(User obj) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("login", obj, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String postMusic(User editor, Music m) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "music", editor, m, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String postArtist(User editor, Artist a) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "artist", editor, a, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String postAlbum(User editor, Album album) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "album", editor, album, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String postMusicFile(MusicFile mf) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("post", "files", mf.getOwner(), mf, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String postReview(Review review) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("review", review, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String searchAlbum(List<String> searchTerms) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("search_album", searchTerms, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String searchMusic(List<String> searchTerms) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("search_music", searchTerms, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String searchArtist(List<String> searchTerms) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("search_artist", searchTerms, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String editAlbum(User self, Album selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("edit_album", "album", self, selected, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String editArtist(User self, Artist selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("edit_artist", "artist", self, selected, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String editMusic(User self, Music selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("edit_music", "music", self, selected, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String editReview(Review review) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("edit_review", review, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String detailsAlbum(Album selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("details_album", selected, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String detailsArtist(Artist selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("details_artist", selected, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String detailsMusic(Music selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageRequest("details_music", selected, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String makeEditor(User self, String grantee) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("make_editor", "null", self, grantee, messageID);

        return waitForResponse(json, messageID);
    }

    @Override
    public String associateDropbox(User user, String code) throws RemoteException {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("associate_dropbox", "users", user, code, messageID);
        
        return waitForResponse(json, messageID);
    }

    @Override
    public String removeArtist(User self, Artist selected) {
        int messageID = msgID.getAndIncrement();
        String json = JSON.messageIdRequest("remove_artist", "null", self, selected, messageID);

        return waitForResponse(json, messageID);
    }
}