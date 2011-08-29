package eu.balamut.kata.atm;

/**
 */
public class AtmFeedProcessor {

    private final AtmSessionProcessor sessionProcessor;

    public AtmFeedProcessor(AtmSessionProcessor sessionProcessor) {
        this.sessionProcessor = sessionProcessor;
    }

    public AtmLog process(AtmFeed atmFeed) {

        AtmLog atmLog = new AtmLog(atmFeed.getInitialBalance());
        for (AtmUserSession userUserSession : atmFeed.getUserUserSessions()) {
            sessionProcessor.process(userUserSession, atmLog);
        }
        return atmLog;
    }
}
