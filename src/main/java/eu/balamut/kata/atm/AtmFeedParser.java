package eu.balamut.kata.atm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

public class AtmFeedParser {

    private final AtmSessionParser sessionParser;

    public AtmFeedParser(AtmSessionParser sessionParser) {
        this.sessionParser = sessionParser;
    }

    public AtmFeed parse(String input) {
        if (isBlank(input)) {
            throw new IllegalArgumentException("cannot parse empty string");
        }

        final List<String> feedElements = Arrays.asList(input.split("\\s*\n\\s*\n"));

        final String initialBalanceString = feedElements.get(0);
        final List<String> userSessionsStrings = feedElements.subList(1, feedElements.size());

        return new AtmFeed(
                parseInitialBalance(initialBalanceString),
                parseUsersSessions(userSessionsStrings));
    }

    private int parseInitialBalance(String initialBalanceString) {
        try {
            final int balance = Integer.parseInt(initialBalanceString);
            if (balance < 0) {
                throw new IllegalArgumentException("atm balance cannot be less than 0");
            }
            return balance;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("cannot parse initial atm balance from feed", e);
        }
    }

    private List<AtmUserSession> parseUsersSessions(List<String> userSessionsStrings) {
        List<AtmUserSession> userSessions = new ArrayList<AtmUserSession>(userSessionsStrings.size());
        for (String userSessionString : userSessionsStrings) {
            userSessions.add(sessionParser.parse(userSessionString));
        }
        return userSessions;
    }
}