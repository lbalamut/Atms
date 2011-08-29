package eu.balamut.kata.atm;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static eu.balamut.kata.atm.AtmTestDataGenerator.exampleUserLogin;
import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

/**
 */
public class AtmFeedProcessorTest {
    private AtmFeedProcessor feedProcessor;

    @Mock
    private AtmSessionProcessor sessionProcessorMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        feedProcessor = new AtmFeedProcessor(sessionProcessorMock);
    }

    @Test
    public void setsInitialBalanceFromFeed() throws Exception {

        final AtmFeed atmFeed = new AtmFeed(10, Collections.<AtmUserSession>emptyList());

        assertEquals(10, feedProcessor.process(atmFeed).getAtmBalance());
    }

    @Test
    public void delegatesAllUserSessionsToSessionProcessor() throws Exception {

        final List<AtmUserSession> userSessions = asList(
                new AtmUserSession(exampleUserLogin()),
                new AtmUserSession(exampleUserLogin()),
                new AtmUserSession(exampleUserLogin()),
                new AtmUserSession(exampleUserLogin())
        );

        final AtmFeed atmFeed = new AtmFeed(10, userSessions);

        final AtmLog atmLog = feedProcessor.process(atmFeed);

        for (AtmUserSession userSession : userSessions) {
            verify(sessionProcessorMock).process(same(userSession), same(atmLog));
        }
    }
}
