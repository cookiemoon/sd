package Dataserver;

import Shared.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import sun.nio.ch.Net;

import javax.xml.transform.Result;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Worker implements Runnable {
    private ArrayBlockingQueue<String> q;
    private ConcurrentHashMap<String, String> sqlCommands;
    private String MULTICAST_ADDRESS = "224.3.2.1";
    private Db db;
    private final int RESPONSE_PORT = 5432; //SENDING PORT
    private String uuid;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Thread t;

    public Worker(ArrayBlockingQueue<String> q, ConcurrentHashMap<String, String> sqlCommands, String uuid, Db db) {
        this.q = q;
        this.sqlCommands = sqlCommands;
        this.uuid = uuid;
        this.db = db;
        t = new Thread(this);
        t.start();
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String json = q.take();
                System.out.println(json);
                multiplex(json);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }

    private void multiplex(String json) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(json).getAsJsonObject();

        String type;
        try {
            type = o.get("type").getAsString();
        } catch (NullPointerException e) {
            type = "";
        }


        String table = null;
        if (type.equals("post"))
            table = o.get("table").getAsString();
        System.out.println("TYPE = " + type + "\n");

        switch (type) {
            case "register":
                register(json);
                break;
            case "login":
                login(json);
                break;
            case "logout":
                //logout(json);
                break;
            case "post":
                switch (table) {
                    case "music":
                        postMusic(json);
                        break;
                    case "album":
                        postAlbum(json);
                        break;
                    case "artist":
                        postArtist(json);
                        break;
                    case "file":
                        //postFile(json);
                        break;
                    default:
                        System.out.println("invalid JSON message @ post request");
                }
                break;
            case "review":
                postReview(json);
                break;
            case "edit_music":
                editMusic(json);
                break;
            case "edit_album":
                editAlbum(json);
                break;
            case "edit_artist":
                editArtist(json);
                break;
            case "edit_file":
                //editFile(json);
                break;
            case "edit_review":
                editReview(json);
                break;
            case "share_file":
                shareFile(json);
                break;
            case "search_music":
                searchMusic(json);
                break;
            case "details_music":
                getMusic(json);
                break;
            case "details_album":
                getAlbum(json);
                break;
            case "details_artist":
                getArtist(json);
                break;
            case "make_editor":
                makeEditor(json);
                break;
            case "alive":
                //alive(json);
                break;
            case "dead":
                //deadRequest(json);
                break;
            case "store_notification":
                //storeNotification(json);
                break;
            default:
                System.out.println("Invalid JSON message @ multiplex");
        }
    }

    private <T> void internalServerError(Connection con, Message<T> req) {
        System.out.println("INTERNAL SERVER ERROR");
        try {
            con.rollback();
            messageClientError(req, "Internal Server error");
        } catch (SQLException e1) {
            System.out.println("Crashed at Worker.internalServerError");
            e1.printStackTrace();
        }
    }

    private <T> void internalServerError(Connection con, MessageIdentified<T> req) {
        System.out.println("INTERNAL SERVER ERROR");
        try {
            con.rollback();
            messageClientError(req, "Internal Server error");
        } catch (SQLException e1) {
            System.out.println("Crashed at Worker.internalServerError");
            e1.printStackTrace();
        }
    }

    private <T> void messageClientError(Message<T> req, String errors) {
        NetworkUtil.multicastSend(
                RESPONSE_PORT,
                JSON.messageResponse(req, uuid, false, errors, null)
        );
    }

    private <T> void messageClientSuccess(Message<T> req, T obj) {
        NetworkUtil.multicastSend(
                RESPONSE_PORT,
                JSON.messageResponse(req, uuid, true, null, obj)
        );
    }

    private <T> void messageClientError(MessageIdentified<T> req, String errors) {
        NetworkUtil.multicastSend(
                RESPONSE_PORT,
                JSON.messageIdResponse(req, uuid, false, errors)
        );
    }

    private <T> void messageClientSuccess(MessageIdentified<T> req, T obj) {
        NetworkUtil.multicastSend(
                RESPONSE_PORT,
                JSON.messageIdResponse(req, uuid, true, null)
        );
    }

    private PreparedStatement setFields (Connection con, String sql, Object... fields) throws MalformedQuery {
        try {
            PreparedStatement s = con.prepareStatement(sqlCommands.get(sql), Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (Object o : fields) {
                if  (o == null)
                    s.setNull(i, Types.DATE);
                else if (o instanceof String)
                    s.setString(i, (String) o);
                else if (o instanceof Integer)
                    s.setInt(i, (Integer) o);
                else if (o instanceof Long)
                    s.setLong(i, (Long) o);
                else if (o instanceof Boolean)
                    s.setBoolean(i, (Boolean) o);
                else if (o instanceof Float)
                    s.setFloat(i, (Float) o);
                else if (o instanceof Double)
                    s.setDouble(i, (Double) o);
                else if (o instanceof Calendar)
                    s.setDate(i, inputUtil.toDate((Calendar) o));
                i++;
            }
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MalformedQuery("Couldn't prepare the SQL statement");
        }
    }

    //SQL SPECIFIC FUNCTIONS

    //Checking functions: these serve to verify if certain data exists on the DB
    //Type indicates whether to search by name or to search by id
    //Sorted alphabetically

    private Album getAlbum(Album data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "get-album-id", data.getID());
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                Album album = new Album(rs.getInt("id"), rs.getString("title"), rs.getString("adesc"), inputUtil.toCalendar(rs.getDate("release_date")));

                stmnt = setFields(con, "get-album-music", data.getID());
                rs = stmnt.executeQuery();

                while(rs.next()) {
                    Music m = getMusic(new Music(rs.getInt("music_id"), ""), con);
                    album.addMusic(m);
                }

                stmnt = setFields(con, "get-album-review", data.getID());
                rs = stmnt.executeQuery();

                while(rs.next()) {
                    Review r = getReview(new Review(0, "", new User(rs.getString("users_email"), ""), new Album(rs.getInt("album_id"), "")), con);
                    album.addReview(r);
                }

                stmnt = setFields(con, "get-album-editor", data.getID());
                rs = stmnt.executeQuery();

                while(rs.next()) {
                    album.addEditor(rs.getString("users_email"));
                }

                return album;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private Artist getArtist(Artist data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "get-artist-id", data.getID());
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                Artist artist = new Artist(rs.getInt("id"), rs.getString("aname"), rs.getString("adesc"));

                stmnt = setFields(con, "get-artist-album", data.getID());
                rs = stmnt.executeQuery();

                while(rs.next()) {
                    Album a = getAlbum(new Album(rs.getInt("album_id"), ""), con);
                    artist.addAlbum(a);
                }

                stmnt = setFields(con, "get-artist-editor", data.getID());
                rs = stmnt.executeQuery();

                while(rs.next()) {
                    artist.addEditor(rs.getString("users_email"));
                }

                return artist;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private MusicFile getFile(MusicFile data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "get-file", data.getMusicID(), data.getOwner().getEmail());
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                MusicFile file = new MusicFile(getUser(new User(rs.getString("users_email"), ""), con), getMusic(new Music(rs.getInt("music_id"), ""), con));
                return file;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private Music getMusic(Music data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "get-music-id", data.getID());
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                Music music = new Music(rs.getInt("id"), rs.getString("title"), rs.getInt("duration"), rs.getString("lyrics"));
                return music;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private Review getReview(Review data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "get-review", data.getReviewed().getID(), data.getReviewer().getEmail());
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                Review review = new Review(rs.getInt("score"), rs.getString("review"), getUser(new User(rs.getString("users_email"), ""), con), getAlbum(new Album(rs.getInt("id"), ""), con));
                return review;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private User getUser(User data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "get-user", data.getEmail());
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                User user = new User(rs.getString("email"), rs.getString("pwd"), rs.getBoolean("editor"));
                return user;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkAnyUser(Connection con) {
        try {
            ResultSet rs = con.prepareStatement(sqlCommands.get("user-any-exists")).executeQuery();
            rs.next();
            int n = rs.getInt("count");
            return (n!=0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Posting functions: they post shit, dude

    private ResultSet postAlbum(User editor, List<Artist> upper, Album data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-album", data.getTitle(), data.getDescription(), data.getReleaseDate(), data.getLabel());

            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            if(rs.isBeforeFirst()){
                rs.next();

                for (Artist a : upper) {
                    stmnt = setFields(con, "post-artist-album", a.getID(), rs.getInt("id"));
                    stmnt.executeUpdate();
                }

                List<String> genres = data.getGenres();

                if(genres!=null) {
                    for (String g : genres) {
                        stmnt = setFields(con, "post-album-genre", rs.getInt("id"), g);
                        stmnt.executeUpdate();
                    }
                }

                stmnt = setFields(con, "post-album-editor", rs.getInt("id"), editor.getEmail());
                stmnt.executeUpdate();
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet postArtist(List<Calendar> upper, Artist data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-artist", data.getName(), data.getDescription());

            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();
            if(rs.isBeforeFirst()) {
                rs.next();

                for (int i = 0; i < upper.size(); i += 2) {
                    if (upper.get(i + 1) != null)
                        stmnt = setFields(con, "post-period-end", rs.getInt("id"), upper.get(i), upper.get(i + 1));
                    else
                        stmnt = setFields(con, "post-period", rs.getInt("id"), upper.get(i));
                    stmnt.executeUpdate();
                }

                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet postFile(List<User> sharee, MusicFile data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-file", data.getMusicID(), data.getOwner().getEmail());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            rs.next();

            for (User u : sharee) {
                stmnt = setFields(con, "post-file-user", rs.getInt("id"), u.getEmail());
                stmnt.executeUpdate();
            }

            rs.beforeFirst();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet postMusic(List<Album> upper, Music data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-music", data.getTitle(), data.getDuration(), data.getLyrics());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            if(rs.isBeforeFirst()) {
                rs.next();
                for (Album a : upper) {
                    stmnt = setFields(con, "post-album-music", a.getID(), rs.getInt("id"));
                    stmnt.executeUpdate();
                }

                List<String> genres = data.getGenres();

                if(genres!=null) {
                    for (String g : genres) {
                        stmnt = setFields(con, "post-music-genre", rs.getInt("id"), g);
                        stmnt.executeUpdate();
                    }
                }
                return rs;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet postReview(Review data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-review", data.getReviewText(), data.getReviewer().getEmail(), data.getReviewed().getID(), data.getScore());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet postUser(User data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-user", data.getEmail(), inputUtil.hashedPass(data.getPwd()), data.isEditor());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    //Edit functions: as before mentioned...

    private ResultSet editAlbum(User editor, Album old, Album data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "update-album", data.getTitle(), data.getDescription(), data.getReleaseDate(), data.getLabel(), old.getID());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            if (!old.getDescription().equals(data.getDescription())) {
                stmnt = setFields(con, "post-album-editor", old.getID(), editor.getEmail());
                stmnt.executeUpdate();
            }

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet editArtist(User editor, Artist old, Artist data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "update-artist", data.getName(), data.getDescription(), old.getID());

            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet editMusic(Music old, Music data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "update-music", data.getTitle(), data.getDuration(), data.getLyrics(), old.getID());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResultSet editReview(Review data, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "update-review", data.getReviewText(), data.getScore(), data.getReviewer().getEmail(), data.getReviewed().getID());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    //Search functions

    //Misc

    private ResultSet makeEditor(User grantee, Connection con) {
        try {
            PreparedStatement stmnt = setFields(con, "post-editor", true, grantee.getEmail());
            stmnt.executeUpdate();
            ResultSet rs = stmnt.getGeneratedKeys();

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedQuery e) {
            e.printStackTrace();
            return null;
        }
    }

    //Login specific functions: these serve to manage the user's session
    //Login, register, and logout

    private void login(String json) {
        System.out.println("Started Worker.login function");

        Message<User> req = gson.fromJson(json, new TypeToken<Message<User>>() {}.getType());
        User user = req.getObj();

        Connection con = db.getConn();
        try {
            con.setAutoCommit(false);

            User real = getUser(user, con);

            if (real==null) {
                messageClientError(req, "The email or password is incorrect.");
            } else {
                if (inputUtil.hashedPass(user.getPwd()).equals(real.getPwd()))
                    messageClientSuccess(req, real);
                else {
                    messageClientError(req, "The email or password is incorrect.");
                }
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        }
    }

    private void register(String json) {
        System.out.println("Started Worker.register function");

        Message<User> req = gson.fromJson(json, new TypeToken<Message<User>>() {}.getType());
        User user = req.getObj();

        PreparedStatement stmnt;

        Connection con = db.getConn();
        try {
            con.setAutoCommit(false);

            User real = getUser(user, con);

            if (real==null) {
                user.setSesh_hash(UUID.randomUUID().toString());

                if (checkAnyUser(con)) {
                    user.setEditor(false);
                } else {
                    user.setEditor(true);
                }
                postUser(user, con);
                con.commit();

                messageClientSuccess(req, user);
            } else {
                messageClientError(req, "Email is already in use.");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        }
    }

    private void userIsEditor (User candidate) throws NotAuthorized {
        final String IS_EDITOR_SQL = sqlCommands.get("user-is-editor");

        Connection con = db.getConn();
        try {
            con.setAutoCommit(false);
            PreparedStatement stmnt = setFields(con, "user-is-editor", candidate.getEmail());
            ResultSet rs = stmnt.executeQuery();
            rs.next();
            boolean isEditor = rs.getBoolean("editor");
            if (!isEditor)
                throw new NotAuthorized("User is not authorized.");
        } catch (SQLException|MalformedQuery e) {
            e.printStackTrace();
        }
    }

    //Posting functions: insert things in database

    private void postMusic(String json) {
        MessageIdentified<Music> req = gson.fromJson(json, new TypeToken<MessageIdentified<Music>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        Music music = req.getObj();
        List<Album> upper = new ArrayList<>();

        try {
            userIsEditor(user);

            con.setAutoCommit(false);

            for (int id : music.getAlbum_ID()) {
                Album real = getAlbum(new Album(id, ""), con);
                if(real != null)
                    upper.add(real);
            }

            if (upper.size() == 0) {
                messageClientError(req, "Album does not exist");
                con.close();
                return;
            } else {

                ResultSet rs = postMusic(upper, music, con);

                if(rs!=null) {
                    con.commit();
                    con.close();

                    messageClientSuccess(req, null);
                } else {
                    internalServerError(con, req);
                    con.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        } catch (Exception e) {
            e.printStackTrace();
            messageClientError(req, "Invalid request");
        }
    }

    private void postAlbum(String json) {
        MessageIdentified<Album> req = gson.fromJson(json, new TypeToken<MessageIdentified<Album>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        Album album = req.getObj();
        List<Artist> upper = new ArrayList<>();

        try {
            con.setAutoCommit(false);
            userIsEditor(user);

            for (int id : album.getArtist_ID()) {
                Artist real = getArtist(new Artist(id, "", ""), con);
                if(real != null)
                    upper.add(real);
            }


            if (upper.size() == 0) {
                messageClientError(req, "Artist does not exist");
                con.close();
                return;
            } else {
                ResultSet rs = postAlbum(user, upper, album, con);

                if(rs!=null) {

                    con.commit();
                    con.close();

                    messageClientSuccess(req, null);

                } else {
                    internalServerError(con, req);
                    con.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        }
    }

    private void postArtist(String json) {
        MessageIdentified<Artist> req = gson.fromJson(json, new TypeToken<MessageIdentified<Artist>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        Artist artist = req.getObj();
        List<Calendar> period = artist.getPeriod();

        try {
            con.setAutoCommit(false);

            userIsEditor(user);

            if(validPeriods(period)) {

                ResultSet rs = postArtist(period, artist, con);

                if(rs!=null) {
                    con.commit();
                    con.close();
                    messageClientSuccess(req, null);
                } else {
                    internalServerError(con, req);
                    con.close();
                }
            } else {
                messageClientError(req, "Period malformatted");
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        }
    }

    private void postReview(String json) {
        Message<Review> req = gson.fromJson(json, new TypeToken<Message<Review>>() {}.getType());

        Connection con = db.getConn();
        Review review = req.getObj();

        try {

            con.setAutoCommit(false);

            ResultSet rs = postReview(review, con);

            if(rs.isBeforeFirst()) {

                con.commit();
                con.close();

                messageClientSuccess(req, null);
            } else {
                internalServerError(con, req);
                con.close();
            }

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    //Editing functions

    private void editMusic(String json) {
        MessageIdentified<Music> req = gson.fromJson(json, new TypeToken<MessageIdentified<Music>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        Music music = req.getObj();

        try {

            con.setAutoCommit(false);

            userIsEditor(user);

            ResultSet rs = editMusic(music.getOld(), music, con);

            if(rs!=null) {

                con.commit();
                con.close();

                messageClientSuccess(req, null);
            } else {
                internalServerError(con, req);
                con.close();
            }

        } catch (SQLException e) {
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        }
    }

    private void editAlbum(String json) {
        MessageIdentified<Album> req = gson.fromJson(json, new TypeToken<MessageIdentified<Album>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        Album album = req.getObj();

        try {
            con.setAutoCommit(false);

            userIsEditor(user);

            con.setAutoCommit(false);

            userIsEditor(user);

            ResultSet rs = editAlbum(user, album.getOld(), album, con);

            if(rs!=null) {

                con.commit();
                con.close();

                messageClientSuccess(req, getAlbum(album, con));
            } else {
                internalServerError(con, req);
                con.close();
            }

        } catch (SQLException e) {
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        }
    }

    private void editArtist(String json) {
        MessageIdentified<Artist> req = gson.fromJson(json, new TypeToken<MessageIdentified<Artist>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        Artist artist = req.getObj();

        try {
            con.setAutoCommit(false);

            userIsEditor(user);

            ResultSet rs = editArtist(user, artist.getOld(), artist, con);

            if(rs!=null) {

                con.commit();
                con.close();

                messageClientSuccess(req, getArtist(artist, con));
            } else {
                internalServerError(con, req);
                con.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        }
    }

    private void editReview(String json) {
        Message<Review> req = gson.fromJson(json, new TypeToken<Message<Review>>() {}.getType());

        Connection con = db.getConn();
        Review review = req.getObj();

        try {

            con.setAutoCommit(false);

            ResultSet rs = editReview(review, con);

            if(rs!=null) {

                con.commit();
                con.close();

                messageClientSuccess(req, null);
            } else {
                internalServerError(con, req);
                con.close();
            }

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    //Details functions

    private void getAlbum(String json) {
        Message<Album> req = gson.fromJson(json, new TypeToken<Message<Album>>() {}.getType());

        Connection con = db.getConn();
        Album album = req.getObj();

        try {

            con.setAutoCommit(false);

            Album real = getAlbum(album, con);

            if(real!=null) {
                messageClientSuccess(req, real);
            } else {
                messageClientError(req, "Album not found");
            }

            con.close();

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    private void getMusic(String json) {
        Message<Music> req = gson.fromJson(json, new TypeToken<Message<Music>>() {}.getType());
        Connection con = db.getConn();

        Music music = req.getObj();

        try {

            con.setAutoCommit(false);

            Music real = getMusic(music, con);

            if(real!=null) {
                messageClientSuccess(req, real);
            } else {
                messageClientError(req, "Music not found");
            }

            con.close();

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    private void getArtist(String json) {
        Message<Artist> req = gson.fromJson(json, new TypeToken<Message<Artist>>() {}.getType());

        Connection con = db.getConn();
        Artist artist = req.getObj();

        try {

            con.setAutoCommit(false);

            Artist real = getArtist(artist, con);

            if(real!=null) {
                messageClientSuccess(req, real);
            } else {
                messageClientError(req, "Music not found");
            }

            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            internalServerError(con, req);
        }
    }

    //Misc

    private void shareFile(String json) {
        Message<MusicFile> req = gson.fromJson(json, new TypeToken<Message<MusicFile>>() {}.getType());

        final String EDITUSERFILE_SQL = sqlCommands.get("post-user-file");
        final String GETUSERFILE_SQL = sqlCommands.get("get-user-has-file");
        final String GETUSER_SQL = sqlCommands.get("user-login");

        Connection con = db.getConn();
        MusicFile file = req.getObj();
        String owner = file.getOwner().getEmail();
        int file_id = file.getMusicID();
        List<String> users = file.getUsers();

        String[] errors = new String[1];

        try {

            con.setAutoCommit(false);

            PreparedStatement stmnt = con.prepareStatement(GETUSER_SQL);
            ResultSet rs;
            for (String user : users) {
                stmnt.setString(1, user);
                rs = stmnt.executeQuery();
                if(!rs.isBeforeFirst())
                    users.remove(user);
            }

            stmnt = con.prepareStatement(GETUSERFILE_SQL);
            for (String user : users) {
                stmnt.setString(1, user);
                stmnt.setString(2, owner);
                stmnt.setInt(3, file_id);
                rs = stmnt.executeQuery();
                if(rs.isBeforeFirst()) {
                    users.remove(user);
                } 
            }

            stmnt = con.prepareStatement(EDITUSERFILE_SQL);
            for (String user : users) {
                stmnt.setString(1, user);
                stmnt.setString(2, owner);
                stmnt.setInt(3, file_id);
                stmnt.executeUpdate();
            }

            con.commit();
            con.close();

            messageClientSuccess(req, null);

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    private void makeEditor(String json) {
        MessageIdentified<String> req = gson.fromJson(json, new TypeToken<MessageIdentified<String>>() {}.getType());
        User user = req.getUser();

        Connection con = db.getConn();
        String grantee = req.getObj();

        try {
            userIsEditor(user);

            con.setAutoCommit(false);

            User g = getUser(new User(grantee, ""), con);

            if (g == null) {
                messageClientError(req, "User does not exist");
                con.close();
            } else {
                
                makeEditor(g, con);

                con.commit();
                con.close();

                messageClientSuccess(req, null);
            }
        } catch (SQLException e) {
            internalServerError(con, req);
        } catch (NotAuthorized notAuthorized) {
            messageClientError(req, "User not authorized");
        }
    }

    //Search

    private void searchMusic(String json) {
        Message<List<String>> req = gson.fromJson(json, new TypeToken<Message<List<String>>>() {}.getType());
        List<String> terms = req.getObj();
        Message<List<Music>> resp = new Message<>(req.getType(), "response", null);
        resp.embedMsgid(req.getMsgid());
        List<Music> obj = new ArrayList<>();
        Music music = new Music(0, null);

        final String SEARCHMUSIC_SQL = sqlCommands.get("get-music");
        Connection con = db.getConn();
        
        try {

            con.setAutoCommit(false);

            PreparedStatement stmnt = con.prepareStatement(SEARCHMUSIC_SQL);
            stmnt.setString(1, terms.get(0));
            stmnt.setString(2, terms.get(1));
            stmnt.setString(3, terms.get(2));
            ResultSet rs = stmnt.executeQuery();

            if(!rs.isBeforeFirst()) {
                terms.set(0, "No matching results were found");
            } else {
                while(rs.next()) {
                    music = new Music(rs.getInt("id"), rs.getInt("duration"), rs.getString("title"), rs.getString("lyrics"), null, null, null);
                }

                obj.add(music);
            }

            con.close();

            messageClientSuccess(resp, obj);

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    private void searchSelfFile(String json) {
        Message<List<String>> req = gson.fromJson(json, new TypeToken<Message<List<String>>>() {}.getType());
        List<String> terms = req.getObj();

        final String SEARCHFILE_SQL = sqlCommands.get("get-user-files");
        final String GETMUSICID_SQL = sqlCommands.get("get-music-id");
        int count = 1;
        Connection con = db.getConn();
        String result = "";
        
        try {
            con.setAutoCommit(false);

            PreparedStatement stmnt = con.prepareStatement(SEARCHFILE_SQL);
            stmnt.setString(1, terms.get(0));
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs_2;

            if(!rs.isBeforeFirst()) {
                terms.set(0, "No matching results were found");
            } else {
                while(rs.next()) {
                    stmnt = con.prepareStatement(GETMUSICID_SQL);
                    stmnt.setInt(1, rs.getInt("music_id"));
                    rs_2 = stmnt.executeQuery();
                    result = count + ". Song title: " + rs_2.getString("title")
                    + "\n";
                    terms.set(count, gson.toJson(rs.getInt("music_id")));
                    count++;
                }

                terms.set(0, result);
            }

            con.close();

            messageClientSuccess(req, terms);

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    private void searchSharedFile(String json) {
        Message<List<String>> req = gson.fromJson(json, new TypeToken<Message<List<String>>>() {}.getType());
        List<String> terms = req.getObj();

        final String SEARCHFILE_SQL = sqlCommands.get("get-user-shared-files");
        final String GETMUSICID_SQL = sqlCommands.get("get-music-id");
        int count = 1;
        int termcount = 1;
        Connection con = db.getConn();
        String result = "";
        
        try {

            con.setAutoCommit(false);

            PreparedStatement stmnt = con.prepareStatement(SEARCHFILE_SQL);
            stmnt.setString(1, terms.get(0));
            ResultSet rs = stmnt.executeQuery();
            ResultSet rs_2;

            if(!rs.isBeforeFirst()) {
                terms.set(0, "No matching results were found");
            } else {
                while(rs.next()) {
                    stmnt = con.prepareStatement(GETMUSICID_SQL);
                    stmnt.setInt(1, rs.getInt("music_id"));
                    rs_2 = stmnt.executeQuery();
                    result = count + ". Song title: " + rs_2.getString("title")
                    + "Owner: " + rs.getString("users_email")
                    + "\n";
                    terms.set(termcount, gson.toJson(rs.getInt("music_id")));
                    terms.set(termcount+1, gson.toJson(rs.getString("users_email")));
                    termcount+=2;
                    count++;
                }

                terms.set(0, result);
            }

            con.close();

            messageClientSuccess(req, terms);

        } catch (SQLException e) {
            internalServerError(con, req);
        }
    }

    //Add-ons

    private boolean isSequential(List<Integer> numbers) {
        Collections.sort(numbers);

        for (int i=0; i<numbers.size(); i++) {
            if(numbers.get(i) != i+1)
                return false; 
        }

        return true;
    }

    private boolean validPeriods(List<Calendar> dates) {
        int size = dates.size();
        if (size == 1) return true;
        else if(size == 2 && dates.get(1) == null) return true;
        for(int i = 0; i < size-1; i++) {
            if(!dates.get(i).before(dates.get(i+1)))
                return false;
        }
        return true;
    }

}
