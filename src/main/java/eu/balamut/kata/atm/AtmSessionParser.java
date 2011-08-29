package eu.balamut.kata.atm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static eu.balamut.kata.atm.AtmOperation.Type;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 */
public class AtmSessionParser {


    public AtmUserSession parse(String sessionString) {
        if (isBlank(sessionString)) {
            throw new IllegalArgumentException("cannot parse empty string");
        }

        final List<String> sessionElements = Arrays.asList(sessionString.split("\\s*\n"));
        if (sessionElements.size() < 2) {
            throw new IllegalArgumentException("user session is too short - required at least two lines, found: " + sessionString);
        }

        String accountLine = sessionElements.get(0);
        String balanceLine = sessionElements.get(1);

        AtmUserSession.UserLogin userLogin = parseUserLogin(accountLine, balanceLine);
        List<AtmOperation> operations = parseOperations(sessionElements.subList(2, sessionElements.size()));

        return new AtmUserSession(userLogin, operations);

    }

    private AtmUserSession.UserLogin parseUserLogin(String accountLine, String balanceLine) {
        final String[] accountLineSplit = accountLine.split("\\s+");
        if (accountLineSplit.length < 3) {
            throw new IllegalArgumentException("no enough parameters for user login, found: " + accountLine);
        }
        String userPin = accountLineSplit[1];
        String userEnteredPin = accountLineSplit[2];

        final String[] balanceLineSplit = balanceLine.split("\\s+");
        if (accountLineSplit.length < 2) {
            throw new IllegalArgumentException("no enough parameters for user balance, found: " + balanceLine);
        }

        int balance = 0;
        try {
            balance = Integer.parseInt(balanceLineSplit[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("cannot parse user balance in: " + balanceLine, e);
        }
        int overdraft = 0;
        try {
            overdraft = Integer.parseInt(balanceLineSplit[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("cannot parse user overdraft in: " + balanceLine, e);
        }

        return new AtmUserSession.UserLogin(userPin, userEnteredPin, balance, overdraft);
    }

    private List<AtmOperation> parseOperations(List<String> operationStrings) {
        final ArrayList<AtmOperation> atmOperations = new ArrayList<AtmOperation>();

        for (String operationString : operationStrings) {
            final String[] operationStringSplit = operationString.split("\\s+");
            final AtmOperation.Type type;
            type = Type.fromString(operationStringSplit[0]);

            if (operationStringSplit.length == 1) {
                atmOperations.add(new AtmOperation(type));
            } else if (operationStringSplit.length == 2) {
                try {
                    atmOperations.add(new AtmOperation(type, Integer.valueOf(operationStringSplit[1])));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("operation parameter is not integer in line: " + operationString);
                }
            } else {
                throw new IllegalArgumentException("wrong parameters count for operation in line: " + operationString);
            }

        }
        return atmOperations;
    }
}
