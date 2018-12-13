/*package Client;

import Shared.*;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import RMI.ServerInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Client {
    static int SERVER_PORT = 1099;

    private String PROMPT = ">> ";
    private User self;
    private ClientImplementation ci = null;
    private ServerInterface serverInterface = null;
    private Gson gson;
    private String addr;

    public User getSelf() {
        return self;
    }

    public ServerInterface getServerInterface() {
        return serverInterface;
    }

    // FIXME: Protect against useless shit
    // Special character combination for empty input?
    public static void main (String[] args) {
        Client c = null;

        String addr = "localhost";
        if (args.length == 1) {
            addr = args[0];
        }

        try {
            c = new Client(addr);
            // FIXME: Add a logout hook again
//            Runtime.getRuntime().addShutdownHook(new Logout(c).hook());
            c.loop();
        } catch (RemoteException e) {
            System.out.println("Couldn't be bothered from using A FUCKING DECENT LANGUAGE");
            e.printStackTrace();
        }
    }

    public Client(String addr) throws RemoteException {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.self = null;
        this.ci = new ClientImplementation();
        this.addr = addr;
        try {
            this.serverInterface = (ServerInterface) Naming.lookup("RMIServer");
        } catch (NotBoundException e) {
            System.out.println("Couldn't find server interface");
            e.printStackTrace();
        } catch (MalformedURLException e) {
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
            } else {
                pMainMenu();
                String option = inputUtil.promptStr(PROMPT);
                multiplexMain(option);
            }
        }
    }

    public boolean multiplexGate(String option) {
        boolean res = false;
        switch(option) {
            case "login":
            case "1":
                res = login(null);
                break;
    
            case "register":
            case "2":
                res = register();
                break;
            default:
                System.out.println("Invalid option");
        }
        return res;
    }

    private boolean register() {
        User toRegister = User.newUser();

        try {
            Message<User> resp = serverInterface.userRegister(toRegister);
            System.out.println(resp);
            if (resp.isAccepted()) {
                return login(toRegister);
            } else {
                System.out.println("Error: " + resp.getErrors());
                return resp.isAccepted();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(User obj) {
        User toLogin;
        if (obj == null)
            toLogin = User.newUser();
        else
            toLogin = obj;

        try {
            Message<User> resp = serverInterface.userLogin(toLogin);
            System.out.println(resp);
            if (resp.isAccepted()) {
                System.out.println("Logged in successfully");
                this.self = resp.getObj();
                return resp.isAccepted();
            } else {
                System.out.println("Error: " + resp.getErrors());
                return resp.isAccepted();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void multiplexMain(String option) {
        switch (option) {
            case "add":
            case "1":
                add();
                break;

            case "edit":
            case "2":
                edit();
                break;

            case "details":
            case "3":
                details();
                break;

            case "review":
            case "4":
                addReview();
                break;

            case "grant editor":
            case "grant editor privilege":
            case "5":
                grantEditor();
                break;

            case "upload":
            case "upload song":
            case "6":
//                upload();
                break;

            case "share song":
            case "share":
            case "7":
//                share();
                break;

            default:
                System.out.println("Invalid");
                break;
        }
    }

    private void grantEditor() {
        try {
            String grantee = inputUtil.promptStr("Email of the user: ");
            MessageIdentified<String> req = serverInterface.makeEditor(self, grantee);

            if (req.isAccepted())
                System.out.println("Editor privileges granted successfully.");
            else
                System.out.println(req.getErrors());

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    /**
     * SELECTIONS
     * - Album
     * - Music
     */


    /*private Album selectAlbum(List<Album> albums) {
        Album selected;
        while (true) {
            for(Album a : albums)
                System.out.println(a.getID() + ": " + a.getTitle() + " by " + a.getGroupName());

            int id = inputUtil.promptInt("Which ID? ");
            selected = albums.stream().filter(a -> a.getID() == id).collect(Collectors.toList()).get(0);

            if (selected != null)
                break;
        }
        return selected;
    }

    private Music selectMusic(List<Music> songs) {
        Music selected;
        while (true) {
            for(Music m : songs)
                System.out.println(m.getID() + ": " + m.getTitle() + " by " + m.getGroupName());

            int id = inputUtil.promptInt("Which ID? ");
            selected = songs.stream().filter(a -> a.getID() == id).collect(Collectors.toList()).get(0);

            if (selected != null)
                break;
        }
        return selected;
    }

    private Artist selectArtist(List<Artist> artists) {
        Artist selected;
        while(true) {
            int i = 1;
            for (Artist a : artists)
                System.out.println(i++ + ". " + a.getName() + "(" + inputUtil.formatDate(a.getBirthDate()) + ")");
            int id = inputUtil.promptInt("Which artist? ");
            if (id < 0) id = 999;
            selected = artists.get(id-1);

            if (selected != null)
                break;
        }
        return selected;
    }

    private Group selectGroup(List<Group> groups) {
        Group selected;
        while (true) {
            for(Group g : groups)
                System.out.println(g.getID() + ": " + g.getName());

            int id = inputUtil.promptInt("Which ID? ");
            selected = groups.stream().filter(a -> a.getID() == id).collect(Collectors.toList()).get(0);

            if (selected != null)
                break;
        }
        return selected;
    }

    private Playlist selectPlaylist(List<Playlist> playlists) {
        Playlist selected;
        while (true) {
            for(Playlist p : playlists)
                System.out.println(p.getID() + ": " + p.getName());

            int id = inputUtil.promptInt("Which ID? ");
            selected = playlists.stream().filter(a -> a.getID() == id).collect(Collectors.toList()).get(0);

            if (selected != null)
                break;
        }
        return selected;
    }

    /**
     * EDITS
     * - Album
     * - Artist
     * - Group
     * - Music
     * - Playlist
     */

    /*private void edit() {
        List<String> list = new ArrayList<>();
        list.add("Album, Artist, Group, Music, Playlist");
        String option;
        boolean valid = false;

        while(!valid) {
            System.out.println("What do you want to edit? " + list + " or go back.");
            option = inputUtil.promptStr(PROMPT).toLowerCase();

            switch(option) {
                case "album":
                    editAlbum();
                    valid = true;
                    break;
                case "artist":
                    editArtist();
                    valid = true;
                    break;
                case "playlist":
                    editPlaylist();
                    valid = true;
                    break;
                case "music":
                    editMusic();
                    valid = true;
                    break;
                case "group":
                    editGroup();
                    valid = true;
                    break;
                case "back":
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid.");
                    break;
            }
        }
    }

    private void editAlbum() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the album: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Album>> albumsQuery = serverInterface.searchAlbum(searchTerms);
            Message<List<Music>> musicQuery;
            boolean valid = true;
            List<Integer> musicIDs = new ArrayList<>();

            if(albumsQuery.getObj().size() > 0) {

                Album selected = selectAlbum(albumsQuery.getObj());
                selected.edit();

                if (inputUtil.promptYesNo("Would you like to edit the song list? (Y/N): ")) {
                    while (valid) {
                        searchTerms.add(0, inputUtil.promptStr("Insert the name of the song: "));
                        musicQuery = serverInterface.searchMusic(searchTerms);
                        if(musicQuery.getObj().size() > 0) {
                            Music selectedMusic = selectMusic(musicQuery.getObj());
                            musicIDs.add(selectedMusic.getID());
                        } else {
                            System.out.println("Song not found");
                        }
                        if (!inputUtil.promptYesNo("Would you like to add another song? (Y/N): "))
                            valid = false;
                    }
                }

                selected.setMusicIDs(musicIDs);
                MessageIdentified<Album> resp = serverInterface.editAlbum(self, selected);

                if (resp.isAccepted())
                    System.out.println("Edited Album successfully");
                else
                    System.out.println(resp.getErrors());
            } else {
                System.out.println("Album not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void editArtist() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the artist's name: "));

            Message<List<Artist>> artistQuery = serverInterface.searchArtist(searchTerms);

            if (artistQuery.getObj().size() > 0 ) {

                Artist selected = selectArtist(artistQuery.getObj());
                selected.edit();

                MessageIdentified<Artist> resp = serverInterface.editArtist(self, selected);

                if (resp.isAccepted())
                    System.out.println("Edited Artist successfully");
                else
                    System.out.println(resp.getErrors());
            } else {
                System.out.println("Artist was not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void editPlaylist() {
        try {
            List<String> searchTerms = inputUtil.promptListStr();
            Message<List<Playlist>> playlistQuery = serverInterface.searchPlaylist(searchTerms);

            Playlist selected = selectPlaylist(playlistQuery.getObj());
            selected.edit();

            MessageIdentified<Playlist> resp = serverInterface.editPlaylist(self, selected);

            if (resp.isAccepted())
                System.out.println("Edited Playlist successfully");
            else
                System.out.println(resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void editMusic() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the song: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the album: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Music>> musicQuery = serverInterface.searchMusic(searchTerms);

            if (musicQuery.getObj().size() > 0) {
                Music selected = selectMusic(musicQuery.getObj());
                selected.edit();

                MessageIdentified<Music> resp = serverInterface.editMusic(self, selected);

                if (resp.isAccepted())
                    System.out.println("Edited Music successfully");
                else
                    System.out.println(resp.getErrors());
            } else {
                System.out.println("Music not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void editGroup() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the group / artist: "));
            Message<List<Group>> groupQuery = serverInterface.searchGroup(searchTerms);

            if (groupQuery.getObj().size() > 0) {

                Group selected = selectGroup(groupQuery.getObj());
                selected.edit();

                System.out.println("hello\n");

                MessageIdentified<Group> resp = serverInterface.editGroup(self, selected);

                if (resp.isAccepted())
                    System.out.println("Edited Group successfully");
                else
                    System.out.println(resp.getErrors());
            } else {
                System.out.println("Group not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void editReview() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the album: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Album>> search = serverInterface.searchAlbum(searchTerms);

            if (search.isAccepted()) {
                if(search.getObj().size() > 0) {
                    Album selected = selectAlbum(search.getObj());
                    Review review = Review.newReview(this.self, selected);

                    Message<Review> resp = serverInterface.editReview(review);
                    if (resp.isAccepted())
                        System.out.println("Review has been successfully edited");
                    else
                        System.out.println("Error: " + resp.getErrors());
                } else {
                    System.out.println("Album not found");
                }
            } else
                System.out.println("Error: " + search.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add
     * - Artist
     * - Album
     * - Group
     * - Music
     * - Label
     * - Genre
     * - Concert
     * - Playlist
     */

    /*private void add() {
        List<String> list = new ArrayList<>();
        list.add("Artist, Album, Group, Music, Label, Genre, Concertos, Playlist, ");
        String option;
        boolean valid = false;
        
        while(!valid) {
            System.out.println("What do you want to add? (Artist, Music, Album, Group, Genres, Files, Playlist, Label) or go back.");
            option = inputUtil.promptStr(PROMPT).toLowerCase();
            
            switch(option) {
                case "album":
                    addAlbum();
                    valid = true;
                    break;
                case "artist":
                    addArtist();
                    valid = true;
                    break;
                case "concert":
                    addConcert();
                    valid = true;
                    break;
                case "genres":
                    addGenre();
                    valid = true;
                    break;
                case "files":
                    addFiles();
                    valid = true;
                    break;
                case "playlist":
                    addPlaylist();
                    valid = true;
                    break;
                case "music":
                    addMusic();
                    valid = true;
                    break;
                case "group":
                    addGroup();
                    valid = true;
                    break;
                case "label":
                    addLabel();
                    valid = true;
                    break;
                case "back":
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid.");
                    break;            
            }
        }
    }

    private void addGenre() {
        try {
            Genre genre = Genre.newGenre();
            MessageIdentified<Genre> resp = serverInterface.postGenre(self, genre);

            if (resp.isAccepted())
                System.out.println("Added genre successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void addMusic() {
        try {
            Music music = Music.newMusic();
            MessageIdentified<Music> resp = serverInterface.postMusic(self, music);

            if (resp.isAccepted())
                System.out.println("Added music successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addArtist() {
        try {
            Artist artist = Artist.newArtist();
            MessageIdentified<Artist> resp = serverInterface.postArtist(self, artist);
            System.out.println();
            if (resp.isAccepted())
                System.out.println("Added artist successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Couldn't get response from the server");
            e.printStackTrace();
        }
    }

    private void addAlbum() {
        try {
            Album album= Album.newAlbum();
            MessageIdentified<Album> resp = serverInterface.postAlbum(self, album);

            if (resp.isAccepted())
                System.out.println("Added album successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Couldn't get response from the server");
        }
    }

    private void addGroup() {
        try {
            Group group = Group.newGroup();
            MessageIdentified<Group> resp = serverInterface.postGroup(self, group);

            if (resp.isAccepted())
                System.out.println("Added group successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Couldn't get response from the server");
        }
    }

    private void addReview() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the album: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Album>> search = serverInterface.searchAlbum(searchTerms);

            if (search.isAccepted()) {
                if(search.getObj().size() > 0) {
                    Album selected = selectAlbum(search.getObj());
                    Review review = Review.newReview(this.self, selected);

                    Message<Review> resp = serverInterface.postReview(review);
                    if (resp.isAccepted())
                        System.out.println("Review has been successfully written");
                    else
                        System.out.println("Error: " + resp.getErrors());
                } else {
                    System.out.println("Album not found");
                }
            } else
                System.out.println("Error: " + search.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addConcert() {
        try {
            Concert concert = Concert.newConcert();
            MessageIdentified<Concert> resp = serverInterface.postConcert(self, concert);
            if (resp.isAccepted())
                System.out.println("Added concert successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addLabel() {
        try {
            Label label = Label.newLabel();
            MessageIdentified<Label> resp = serverInterface.postLabel(self, label);
            if (resp.isAccepted())
                System.out.println("Added label successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void addPlaylist() {
        try {
            Playlist playlist = Playlist.newPlaylist(this.self);
            MessageIdentified<Playlist> resp = serverInterface.postPlaylist(playlist);
            if (resp.isAccepted())
                System.out.println("Added playlist successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addFiles() {
        try {
            Message<List<Music>> listResp = serverInterface.searchMusic(inputUtil.promptListStr());
            MusicFile mf = new MusicFile(this.self, selectMusic(listResp.getObj()));
            MessageIdentified<MusicFile> resp = serverInterface.postMusicFile(mf);
            if (resp.isAccepted())
                System.out.println("Added file successfully");
            else
                System.out.println("Error: " + resp.getErrors());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /** DETAILS
     * Album
     * Artist
     * Playlist
     * Music
     * Group
     */

    /*private void details() {
        List<String> list = new ArrayList<>();
        list.add("Artist, Album, Group, Music, Playlist)");
        String option;
        boolean valid = false;

        while(!valid) {
            System.out.println("What do you want to see information of?" + list + "or go back.");
            option = inputUtil.promptStr(PROMPT).toLowerCase();

            switch(option) {
                case "album":
                    detailsAlbum();
                    valid = true;
                    break;
                case "artist":
                    detailsArtist();
                    valid = true;
                    break;
                case "playlist":
                    detailsPlaylist();
                    valid = true;
                    break;
                case "music":
                    detailsMusic();
                    valid = true;
                    break;
                case "group":
                    detailsGroup();
                    valid = true;
                    break;
                case "back":
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid.");
                    break;
            }
        }
    }

    private void detailsAlbum() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the album: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Album>> albumsQuery = serverInterface.searchAlbum(searchTerms);

            if(albumsQuery.getObj().size() > 0) {

                Album selected = selectAlbum(albumsQuery.getObj());
                Message<Album> resp = serverInterface.detailsAlbum(selected);

                if (resp.isAccepted())
                    System.out.println(resp.getObj().getDetails());
                else
                    System.out.println(resp.getErrors());

            } else {
                System.out.println("Album not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void detailsArtist() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Artist>> artistQuery = serverInterface.searchArtist(searchTerms);

            if(artistQuery.getObj().size() > 0) {

                Artist selected = selectArtist(artistQuery.getObj());
                Message<Artist> resp = serverInterface.detailsArtist(selected);

                if (resp.isAccepted())
                    System.out.println(resp.getObj().getDetails());
                else
                    System.out.println(resp.getErrors());

            } else {
                System.out.println("Album not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void detailsPlaylist() {}

    private void detailsMusic() {
        try {
            List<String> searchTerms = new ArrayList<>();
            searchTerms.add(inputUtil.promptStr("Insert the name of the song: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the album: "));
            searchTerms.add(inputUtil.promptStr("Insert the name of the artist: "));
            Message<List<Music>> musicQuery = serverInterface.searchMusic(searchTerms);

            if(musicQuery.getObj().size() > 0) {

                Music selected = selectMusic(musicQuery.getObj());
                Message<Music> resp = serverInterface.detailsMusic(selected);

                if (resp.isAccepted())
                    System.out.println(resp.getObj().getDetails());
                else
                    System.out.println(resp.getErrors());

            } else {
                System.out.println("Album not found");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println();
        }
    }

    private void detailsGroup() {try {
        List<String> searchTerms = new ArrayList<>();
        searchTerms.add(inputUtil.promptStr("Insert the name of the group/artist: "));
        Message<List<Group>> groupQuery = serverInterface.searchGroup(searchTerms);

        if(groupQuery.getObj().size() > 0) {

            Group selected = selectGroup(groupQuery.getObj());
            Message<Group> resp = serverInterface.detailsGroup(selected);

            if (resp.isAccepted())
                System.out.println(resp.getObj().getDetails());
            else
                System.out.println(resp.getErrors());

        } else {
            System.out.println("Album not found");
        }
    } catch (RemoteException e) {
        e.printStackTrace();
    } catch (NullPointerException e) {
        System.out.println();
    }}

    private void pMainMenu () {
        System.out.println("Main Menu.\nType the name of what you want to do\n\n" +
        "1. Add\n"+
        "2. Edit\n"+
        "3. Details\n"+
        "4. Review Album\n"+
        "5. Grant Editor Privilege\n"+
        "6. Upload new Song\n"+
        "7. Share song\n"+
        "8. Download song\n\n"
        );
    }

    private void pGateMenu() {
        System.out.println("Login Menu.\nType the name of what you want to do\n\n" +
        "1. Login\n"+
        "2. Register\n"
        );
    }
}*/