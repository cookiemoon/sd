package Shared;

public class BadInput extends Exception {
    public BadInput(String param, String reason) {
        super("Invalid value of parameter "+param+" for the following reason:\n"+reason);
    }
}
