package org.example.database.service;

public class Session {
    private boolean LoggedIn;
    private String username;
    private String password;


    public Session(String username, String password) {
        setLoggedIn(true);
        setUsernames(username);
        setPasswords(password);
    }

    public boolean isLoggedIn() {
        return LoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        LoggedIn = loggedIn;
    }

    public String getUsernames() {
        return username;
    }

    public void setUsernames(String username) {
        this.username = username;
    }

    public String getPasswords() {
        return password;
    }

    public void setPasswords(String password) {
        this.password = password;
    }
}
