package com.utils;

public class SessionManager {
    private static SessionManager instance;
    private Session currentSession;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void createSession() {
        currentSession = new Session() {};
    }

    public Session getSession() {
        return currentSession;
    }

    public void clearSession() {
        currentSession = null;
    }
}

