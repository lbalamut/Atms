package eu.balamut.kata.atm;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 */
public class AtmUserSessionTest {

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyPin() {
        new AtmUserSession.UserLogin(null, "111", 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyUserEnteredPin() {
        new AtmUserSession.UserLogin("123", "", 0, 0);
    }

    @Test
    public void rejectsNotEqualPins() {
        assertFalse(new AtmUserSession.UserLogin("123", "321", 0, 0).userAuthenticated());
    }

    @Test
    public void userEntersCorrectPin() {
        assertTrue(new AtmUserSession.UserLogin("123", "123", 0, 0).userAuthenticated());
    }
}
