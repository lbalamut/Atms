package eu.balamut.kata.atm;

import org.junit.Test;

import java.util.List;

import static eu.balamut.kata.atm.AtmOperation.Type.BALANCE;
import static eu.balamut.kata.atm.AtmOperation.Type.WITHDRAWAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 */
public class AtmSessionParserTest {

    private AtmSessionParser parser = new AtmSessionParser();

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyInput() throws Exception {
        parser.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullInput() throws Exception {
        parser.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTooShortInput() throws Exception {
        parser.parse("87654321 4321 4321");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTooLessArgumentsInLoginLine() throws Exception {
        parser.parse("87654321 4321 \n" +
                "100 0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsTooLessArgumentsInBalanceLine() throws Exception {
        parser.parse("87654321 4321 \n" +
                "100 ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNoIntsInBalanceLine() throws Exception {
        parser.parse("87654321 4321 4321\n" +
                "100 foo");
    }

    @Test
    public void parsesUserData() throws Exception {
        final AtmUserSession atmUserSession
                = parser.parse(
                "87654321 4321 4321\n" +
                        "100 50");

        final AtmUserSession.UserLogin userLogin = atmUserSession.getUserLogin();
        assertTrue(userLogin.userAuthenticated());
        assertEquals(100, userLogin.getUserAccountBalance());
        assertEquals(50, userLogin.getUserAccountOverdraftFacility());
    }

    @Test(expected = IllegalArgumentException.class)
    public void refusesUnknownOperation() throws Exception {
        parser.parse(
                "87654321 4321 4321\n" +
                        "100 50\n" +
                        "X");
    }

    @Test(expected = IllegalArgumentException.class)
    public void refusesNotIntOperationParameter() throws Exception {
        parser.parse(
                "87654321 4321 4321\n" +
                        "100 50\n" +
                        "W foo");
    }

    @Test
    public void parsesOperations() throws Exception {
        final AtmUserSession session = parser.parse(
                "87654321 4321 4321\n" +
                        "100 50\n" +
                        "W 123\n" +
                        "B\n" +
                        "W 321");

        final List<AtmOperation> operations = session.getOperations();

        assertEquals(3, operations.size());
        assertEquals(WITHDRAWAL, operations.get(0).getType());
        assertEquals(123, operations.get(0).getArgument().intValue());
        assertEquals(BALANCE, operations.get(1).getType());
        assertEquals(WITHDRAWAL, operations.get(2).getType());
        assertEquals(321, operations.get(2).getArgument().intValue());

    }
}
