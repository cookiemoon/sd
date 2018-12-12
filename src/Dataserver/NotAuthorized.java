package Dataserver;

public class NotAuthorized extends Exception {
    public NotAuthorized(String msg) {
        super(msg);
    }
}
