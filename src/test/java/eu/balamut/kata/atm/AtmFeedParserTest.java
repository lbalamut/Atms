package eu.balamut.kata.atm;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static eu.balamut.kata.atm.AtmTestDataGenerator.exampleUserLogin;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 */
public class AtmFeedParserTest {

    private AtmFeedParser parser;

    @Mock
    private AtmSessionParser mockSessionParser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        parser = new AtmFeedParser(mockSessionParser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyInput() throws Exception {
        parser.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNullInput() throws Exception {
        parser.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNotIntegerInitialBalance() throws Exception {
        parser.parse("not-int");
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNegativeInitialBalance() throws Exception {
        parser.parse("-1");
    }

    @Test
    public void parsesInitialBalance() throws Exception {
        assertEquals(8000, parser.parse("8000").getInitialBalance());

        assertEquals(1234, parser.parse("1234    \n\n").getInitialBalance());
    }

    @Test
    public void userSessionsEmptyList() throws Exception {

        when(mockSessionParser.parse(eq("any"))).thenReturn(new AtmUserSession(exampleUserLogin()));

        AtmFeed atmFeed = parser.parse(
                "1234");

        assertEquals(1234, atmFeed.getInitialBalance());
        assertTrue("user session must be empty", atmFeed.getUserUserSessions().isEmpty());
    }

    @Test
    public void parsesUserSessions() throws Exception {

        when(mockSessionParser.parse(eq("any"))).thenReturn(new AtmUserSession(exampleUserLogin()));

        AtmFeed atmFeed = parser.parse(
                "1234\n\n" +
                        "anya    \n\n" +
                        "any\t\n\n" +
                        "any  "
        );

        assertEquals(1234, atmFeed.getInitialBalance());
        assertEquals(3, atmFeed.getUserUserSessions().size());
    }
}
