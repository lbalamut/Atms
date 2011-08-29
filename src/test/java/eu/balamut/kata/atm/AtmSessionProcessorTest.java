package eu.balamut.kata.atm;

import org.junit.Before;
import org.junit.Test;

import static eu.balamut.kata.atm.AtmOperation.Type.BALANCE;
import static eu.balamut.kata.atm.AtmOperation.Type.WITHDRAWAL;
import static eu.balamut.kata.atm.AtmTestDataGenerator.AtmUserSessionBuilder.userSession;
import static org.junit.Assert.assertEquals;

/**
 */
public class AtmSessionProcessorTest {

    private AtmSessionProcessor processor;
    private AtmLog log;

    @Before
    public void setup() {
        processor = new AtmSessionProcessor();
        log = new AtmLog(0);
    }

    @Test
    public void reportsBadUserLogin() throws Exception {

        processor.process(userSession().withPins("123", "321").build(), log);

        assertEquals("ACCOUNT_ERR", log.toString());
    }

    @Test
    public void showsUserBalance() throws Exception {
        processor.process(
                userSession()
                        .withBalance(123)
                        .withOperations(new AtmOperation(BALANCE))
                        .build(),
                log);

        assertEquals("123", log.toString());
    }

    @Test
    public void reportsFundsError() throws Exception {
        processor.process(
                userSession()
                        .withBalance(100)
                        .withOperations(new AtmOperation(WITHDRAWAL, 101))
                        .build(),
                log);

        assertEquals("FUNDS_ERR", log.toString());
    }

    @Test
    public void reportsAtmError() throws Exception {
        processor.process(
                userSession()
                        .withBalance(100)
                        .withOperations(new AtmOperation(WITHDRAWAL, 100))
                        .build(),
                log);

        assertEquals("ATM_ERR", log.toString());
    }

    @Test
    public void withdrawalFromUserBalance() throws Exception {
        log = new AtmLog(1000);

        processor.process(
                userSession()
                        .withBalance(100)
                        .withOperations(new AtmOperation(WITHDRAWAL, 100))
                        .build(),
                log);

        assertEquals("0", log.toString());
    }

    @Test
    public void withdrawalFromOverdraft() throws Exception {
        log = new AtmLog(1000);

        processor.process(
                userSession()
                        .withBalance(100)
                        .withOverdraft(50)
                        .withOperations(new AtmOperation(WITHDRAWAL, 112))
                        .build(),
                log);

        assertEquals("-12", log.toString());
    }
}
