package eu.balamut.kata.atm;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class AtmLog {

    private List<Operation> atmOperations = new ArrayList<Operation>();

    private static class Operation {

        //user account number might go here
        AtmError error = null;
        int withdrawalAmount = 0;
        int userBalanceAfter = 0;

        Operation(int withdrawalAmount, int userBalanceAfter) {
            this.withdrawalAmount = withdrawalAmount;
            this.userBalanceAfter = userBalanceAfter;
        }

        Operation(AtmError error) {
            this.error = error;
        }
    }

    public AtmLog(int balance) {
        //model initial balance as negative withdrawal
        atmOperations.add(new Operation(-balance, 0));
    }

    public int getAtmBalance() {

        int balance = 0;
        for (Object operation : atmOperations) {
            if (operation instanceof Integer) {
                balance += ((Integer) operation).intValue();
            } else if (operation instanceof Operation) {
                balance -= ((Operation) operation).withdrawalAmount;
            }
        }

        return balance;
    }

    public void logBalance(int userAccountBalance) {
        atmOperations.add(new Operation(0, userAccountBalance));
    }

    public void logError(AtmError err) {
        atmOperations.add(new Operation(err));
    }

    public void logWithdrawal(int amount, int userBalanceAfter) {
        atmOperations.add(new Operation(amount, userBalanceAfter));
    }

    @Override
    public String toString() {
        if (atmOperations.size() == 1) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        //skips initial balance
        for (Operation operation : atmOperations.subList(1, atmOperations.size())) {
            if (operation.error != null) {
                builder.append(operation.error);
            } else {
                builder.append(operation.userBalanceAfter);
            }
            builder.append("\n");
        }
        //to remove last \n
        return builder.substring(0, builder.length() - 1);
    }
}
