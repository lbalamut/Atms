package eu.balamut.kata.atm;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 */
public class AtmTestDataGenerator {

    public static AtmUserSession.UserLogin exampleUserLogin(int userAccountBalance, int userAccountOverdraftFacility) {
        return new AtmUserSession.UserLogin("123", "123", userAccountBalance, userAccountOverdraftFacility);
    }

    public static AtmUserSession.UserLogin exampleUserLogin() {
        return exampleUserLogin(0, 0);
    }

    public static class AtmUserSessionBuilder {
        private AtmUserSession.UserLogin userLogin = exampleUserLogin();
        private List<AtmOperation> operations = Collections.emptyList();
        private int balance = 0;
        private int overdraftFacility;

        private AtmUserSessionBuilder() {
        }

        public static AtmUserSessionBuilder userSession() {
            return new AtmUserSessionBuilder();
        }


        public AtmUserSessionBuilder withBalance(int balance) {
            this.balance = balance;
            userLogin = exampleUserLogin(this.balance, this.overdraftFacility);
            return this;
        }

        public AtmUserSessionBuilder withOverdraft(int overdraftFacility) {
            this.overdraftFacility = overdraftFacility;
            userLogin = exampleUserLogin(this.balance, this.overdraftFacility);
            return this;
        }

        public AtmUserSessionBuilder withOperations(AtmOperation... atmOperations) {
            operations = asList(atmOperations);
            return this;
        }

        public AtmUserSessionBuilder withPins(String userPin, String userEnteredPin) {
            userLogin = new AtmUserSession.UserLogin(userPin, userEnteredPin, 0, 0);
            return this;
        }

        public AtmUserSession build() {
            return new AtmUserSession(userLogin, operations);
        }
    }

}
