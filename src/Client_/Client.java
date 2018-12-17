package Client_;

import Dataserver.MalformedQuery;
import Shared.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.nio.file.Files;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

import RMI.ServerInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Client {
    private String PROMPT = ">> ";
    private User self;
    private ClientImplementation ci = null;
    private ServerInterface serverInterface = null;
    private int SERVER_PORT = 7000;
    private Gson gson;
    private String addr;

    public User getSelf() {
        return self;
    }

    public ServerInterface getServerInterface() {
        return serverInterface;
    }

    // Special character combination for empty input?
    public static void main (String[] args) {
        Client c = null;

        String addr = "localhost:1100";
        if (args.length == 1) {
            addr = args[0];
        }

        try {
            c = new Client(addr);
        } catch (RemoteException e) {
            System.out.println("Couldn't create client interface");
            e.printStackTrace();
        }

        c.loop();
    }

    public Client(String addr) throws RemoteException {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.self = null;
        this.ci = new ClientImplementation();
        this.addr = addr;
        try {
            this.serverInterface = (ServerInterface) Naming.lookup("rmi://localhost:1100/RMIServer");
        } catch (NotBoundException| MalformedURLException e) {
            System.out.println("Couldn't find server interface");
            e.printStackTrace();
        }
    }


    public void loop () {
        boolean quit = false;
        boolean logged = false;

        while (!logged || !quit) {
            if (!logged) {
                pGateMenu();
                String option = inputUtil.promptStr(PROMPT);
                logged = multiplexGate(option);
                /*if (self != null && self.getNotifications() != null) {
                    for (String n : self.getNotifications()) {
                        System.out.println("Notifications!");
                        System.out.println(" - " + n);
                    }
                }*/
                if (logged) {
                    try {
                        this.serverInterface.associateCb(this.self.getEmail(), this.ci);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                pMainMenu();
                String option = inputUtil.promptStr(PROMPT);
                multiplexMain(option);
            }
        }
    }

    public boolean multiplexGate(String option) {
        boolean res = false;
        try {
            switch (option) {
                case "login":
                case "1":
                    res = login();
                    break;

                case "register":
                case "2":
                    res = register();
                    break;
                default:
                    System.out.println("Invalid option");
            }
            return res;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean register() throws RemoteException {
        String username = inputUtil.promptStr("Enter your username: ");
        String password = inputUtil.promptStr("Enter password: ");

        User u = new User(username,password);
        String json = serverInterface.userRegister(u);
    
        Message<User> msg = gson.fromJson(json, new TypeToken<Message<User>>() {}.getType());

        if (msg.isAccepted()) {
            json = serverInterface.userLogin(u);
            msg = gson.fromJson(json, new TypeToken<Message<User>>() {}.getType());
            System.out.println(json);
            this.self = msg.getObj();
            return msg.isAccepted();
        } else {
            System.out.println("Error: " + msg.getErrors());
            return msg.isAccepted();
        }
    }

    public boolean login() throws RemoteException {
        String username = inputUtil.promptStr("Enter username: ");
        String password = inputUtil.promptStr("Enter password: ");
        User u = new User(username, password);

        String res = serverInterface.userLogin(u);

        Message<User> msg = gson.fromJson(res, new TypeToken<Message<User>>() {}.getType());

        System.out.println(res);

        if (msg.isAccepted()) {
            this.self = msg.getObj();
            System.out.println("Logged in succesfully");
            return msg.isAccepted();
        } else {
            System.out.println("Error: " + msg.getErrors());
            return msg.isAccepted();
        }
    }

    private void multiplexMain(String option) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            switch (option) {
                case "add":
                case "1":
                    add();
                    break;

                case "edit":
                case "album edit":
                case "edit album":
                case "2":
                    editAlbum();
                    break;

                case "search album":
                case "album search":
                case "3":
                    searchAlbum();
                    break;

                case "album details":
                case "details album":
                case "4":
                    albumDetails();
                    break;

                case "review":
                case "5":
                    review();
                    break;

                case "artist details":
                case "details artist":
                case "6":
                    artistDetails();
                    break;

                case "grant editor":
                case "grant editor privilege":
                case "7":
                    grantEditor();
                    break;

                default:
                    System.out.println("Invalid");
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void add() throws RemoteException {
        String option;
        String json = null;
        boolean valid = false;
        
        while(!valid) {
            System.out.println("What do you want to add? (Artist, Music, Album) or go back.");
            option = inputUtil.promptStr(PROMPT);
            
            switch(option) {
                case "music":
                    addMusic();
                    valid = true;
                    break;
                
                case "artist":
                    addArtist();
                    valid = true;                    
                    break;
                
                case "album":
                    addAlbum();
                    valid = true;
                    break;
                
                case "back":
                    valid = true;
                    break;                                        
                
                default:
                    System.out.println("Invalid. You can only add artist, music or album");                                
                    break;            
            }
        }
    }

    private void addMusic() throws RemoteException {
        Music newMusic = Music.getInstance();

        String json = serverInterface.postMusic(self, newMusic);
        MessageIdentified<Music> msg = gson.fromJson(json, new TypeToken<MessageIdentified<Music>>() {}.getType());

        if(msg.accepted()) {
            System.out.println("\nOperation successful.\n");
        } else {
            System.out.println("\nError: "+msg.errors()+"\n");
        }
    }

    private void addAlbum() throws RemoteException {
        Album a = Album.getInstance();
        
        String json = serverInterface.postAlbum(self, a);
        MessageIdentified<Album> msg = gson.fromJson(json, new TypeToken<MessageIdentified<Album>>() {}.getType());

        if(msg.accepted()) {
            System.out.println("\nOperation successful.\n");
        } else {
            System.out.println("\nError: "+msg.errors()+"\n");
        }
    }

    private void addArtist() throws RemoteException {
        Artist a = Artist.getInstance();

        String json = serverInterface.postArtist(self, a);
        MessageIdentified<Artist> msg = gson.fromJson(json, new TypeToken<MessageIdentified<Artist>>() {}.getType());

        if(msg.accepted()) {
            System.out.println("\nOperation successful.\n");
        } else {
            System.out.println("\nError: "+msg.errors()+"\n");
        }
    }

    private void searchAlbum() throws RemoteException {
        String option="";
        int cnt = 1;
        while (!option.equals("artist")&&!option.equals("genre")&&!option.equals("title")) {
            option = inputUtil.promptStr("Search album by (artist, genre, or title): ");
        }
        String term = inputUtil.promptStr("Search term: ");
        List<String> terms = new ArrayList<>();
        terms.add(option);
        terms.add(term);
        
        String json = serverInterface.searchAlbum(terms);
        Message<List<Album>> rsp = gson.fromJson(json, new TypeToken<MessageIdentified<List<Album>>>() {}.getType());
        if(rsp.isAccepted()) {
            for (Album a : rsp.getObj()) {
                System.out.println(String.valueOf(cnt) +". Title: "+a.getTitle()+"\nID: "+String.valueOf(a.getID())+"\n\n");
            }
        } else
            System.out.println("\nError: "+rsp.getErrors()+"\n");
    }

    public void albumDetails() throws RemoteException {
        int albumID = inputUtil.promptInt("Album ID: ");
        Album dtls = new Album(albumID);
        
        String json = serverInterface.detailsAlbum(dtls);
        Message<Album> msg = gson.fromJson(json, new TypeToken<Message<Album>>() {}.getType());
        Album a = msg.getObj();

        if(msg.isAccepted()) {
            System.out.println(a.getDetails());
        } else {
            System.out.println("\nError: "+msg.getErrors()+"\n");
        }
    }

    public void artistDetails() throws RemoteException {
        int artistID = inputUtil.promptInt("Artist ID: ");
        Artist dtls = new Artist(artistID);

        String json = serverInterface.detailsArtist(dtls);
        Message<Artist> msg = gson.fromJson(json, new TypeToken<Message<Artist>>() {}.getType());
        Artist a = msg.getObj();

        if(msg.isAccepted()) {
            System.out.println(a.getDetails());
        } else {
            System.out.println("\nError: "+msg.getErrors()+"\n");
        }
    }

    private void editAlbum() throws RemoteException {
        int albumID = inputUtil.promptInt("Album ID: ");
        Album chosen = Album.editInstance(albumID);

        String json = serverInterface.editAlbum(self, chosen);
        Message<Album> msg = gson.fromJson(json, new TypeToken<Message<Album>>() {}.getType());
        Album a = msg.getObj();

        if(msg.isAccepted()) {
            System.out.println("\nOperation successful.\n");
            //if (a.isEdited())
                //sendpacketNotifEditors(a.getID(), a.getEditors());
        } else {
            System.out.println("\nError: " + msg.getErrors() + "\n");
        }
    }

    public void review() throws RemoteException {
        Review r = Review.getInstance(self);

        String json = serverInterface.postReview(r);
        Message<Review> msg = gson.fromJson(json, new TypeToken<Message<Review>>() {}.getType());

        if(msg.isAccepted()) {
            System.out.println("\nOperation successful.\n");
        } else {
            System.out.println("\nError: "+msg.getErrors()+"\n");
        }
    }

    public void grantEditor() throws RemoteException {
        String username = inputUtil.promptStr("User to grant editor privilege: ");
        String json = serverInterface.makeEditor(self, username);
        MessageIdentified<String> msg = gson.fromJson(json, new TypeToken<MessageIdentified<String>>() {}.getType());

        if(msg.accepted()) {
            System.out.println("\nOperation successful.\n");
        } else {
            System.out.println("\nError: "+msg.errors()+"\n");
        }
    }

    private void pMainMenu () {
        System.out.println("Main Menu.\nType the name of what you want to do\n\n" +
        "1. Add\n"+
        "2. Edit Album\n"+
        "3. Search Album\n"+
        "4. Get Album Details\n"+
        "5. Review Album\n"+
        "6. Get Artist Details\n"+
        "7. Grant Editor Privilege\n"
        );
    }

    private void pGateMenu() {
        System.out.println("Login Menu.\nType the name of what you want to do\n\n" +
        "1. Login\n"+
        "2. Register\n"
        );
    }
}