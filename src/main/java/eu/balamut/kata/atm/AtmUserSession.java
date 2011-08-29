package eu.balamut.kata.atm;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 */
public final class AtmUserSession {

    private final UserLogin userLogin;
    private final List<AtmOperation> operations;


    public final static class UserLogin {

        //TODO we do not need it now
//        private final String accountNumber;
        private final String userPin;
        private final String userEnteredPin;
        private final int userAccountBalance;
        private final int userAccountOverdraftFacility;

        public UserLogin(String userPin,
                         String userEnteredPin,
                         int userAccountBalance,
                         int userAccountOverdraftFacility) {

            if (isBlank(userPin)) {
                throw new IllegalArgumentException("user pin cannot be empty");
            }

            if (isBlank(userEnteredPin)) {
                throw new IllegalArgumentException("user cannot enter empty pin");
            }

            if (userAccountBalance < 0) {
                throw new IllegalArgumentException("user account logBalance cannot be less than 0: " + userAccountBalance);
            }
            if (userAccountOverdraftFacility < 0) {
                throw new IllegalArgumentException("user account overdraft facility  cannot be less than 0: " + userAccountOverdraftFacility);
            }

//            this.accountNumber = accountNumber;
            this.userPin = userPin;
            this.userEnteredPin = userEnteredPin;


            this.userAccountBalance = userAccountBalance;
            this.userAccountOverdraftFacility = userAccountOverdraftFacility;
        }

        public int getUserAccountBalance() {
            return userAccountBalance;
        }

        public boolean userAuthenticated() {
            return userPin.equals(userEnteredPin);
        }

        public int getUserAccountOverdraftFacility() {
            return userAccountOverdraftFacility;
        }
    }

    public AtmUserSession(UserLogin userLogin, List<AtmOperation> operations) {
        this.userLogin = userLogin;
        this.operations = Collections.unmodifiableList(operations);
    }

    public AtmUserSession(UserLogin userLogin) {
        this(userLogin, Collections.<AtmOperation>emptyList());
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public List<AtmOperation> getOperations() {
        return operations;
    }
}
