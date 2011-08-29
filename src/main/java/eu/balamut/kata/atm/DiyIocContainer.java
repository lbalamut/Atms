package eu.balamut.kata.atm;

/**
 */
public class DiyIocContainer {

    private final AtmFeedParser feedParser;
    private final AtmSessionParser sessionParser;

    private AtmFeedProcessor feedProcessor;
    private AtmSessionProcessor sessionProcessor;

    public DiyIocContainer() {
        sessionParser = new AtmSessionParser();
        feedParser = new AtmFeedParser(sessionParser);

        sessionProcessor = new AtmSessionProcessor();
        feedProcessor = new AtmFeedProcessor(this.sessionProcessor);
    }

    public String process(String input) {
        return feedProcessor.process(feedParser.parse(input)).toString();
    }

}
