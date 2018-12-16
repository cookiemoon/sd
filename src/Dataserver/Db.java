package Dataserver;

import java.sql.*;
import java.util.Properties;

public class Db {
    static private final String url = "jdbc:postgresql://localhost:5432/dropmusic";
    private Properties props;

    public Db() {
        props = new Properties();
        props.setProperty("user","anaquelhas");
        props.setProperty("password","dropmusic");
    }

    public Connection getConn() throws NullPointerException {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("No conneciton estabilished");
    }
}
