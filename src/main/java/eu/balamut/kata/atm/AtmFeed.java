package eu.balamut.kata.atm;

import java.util.*;

/**
 */
public class AtmFeed {

    private final int initialBalance;
    private final List<AtmUserSession> userUserSessions;

    public AtmFeed(int initialBalance, List<AtmUserSession> userSessions) {
        this.initialBalance = initialBalance;
        userUserSessions = Collections.unmodifiableList(userSessions);
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public List<AtmUserSession> getUserUserSessions() {
        return userUserSessions;
    }
}
