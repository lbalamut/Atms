package eu.balamut.kata.atm;

import java.util.List;

import static eu.balamut.kata.atm.AtmError.*;


/**
 */
public class AtmSessionProcessor {

    public void process(AtmUserSession userSession, AtmLog log) {

        if (!userSession.getUserLogin().userAuthenticated()) {
            log.logError(AtmError.ACCOUNT_ERR);
            return;
        }

        final List<AtmOperation> operations = userSession.getOperations();

        for (AtmOperation operation : operations) {

            switch (operation.getType()) {
                case BALANCE:
                    log.logBalance(userSession.getUserLogin().getUserAccountBalance());
                    break;
                case WITHDRAWAL:
                    final Integer amount = operation.getArgument();
                    final int balance = userSession.getUserLogin().getUserAccountBalance();
                    final int overdraftFacility = userSession.getUserLogin().getUserAccountOverdraftFacility();
                    withdrawal(log, amount, balance, overdraftFacility);
                    break;
            }
        }

    }

    private void withdrawal(AtmLog log, int amount, int userBalance, int userOverdraftFacility) {
        if(amount > userBalance + userOverdraftFacility) {
            log.logError(FUNDS_ERR);
        } else {
            final int atmBalance = log.getAtmBalance();
            if (amount > atmBalance) {
                log.logError(ATM_ERR);
            } else {
                log.logWithdrawal(amount, userBalance - amount);
            }
        }
    }
}
