package eu.balamut.kata.atm;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 */
public class AtmAcceptanceTest {

    private DiyIocContainer container = new DiyIocContainer();


    @Test
    public void processExampleFeed() throws Exception {
        String input =
                "8000 \n" +
                " \n" +
                "12345678 1234 1234 \n" +
                "500 100 \n" +
                "B \n" +
                "W 100 \n" +
                " \n" +
                "87654321 4321 4321 \n" +
                "100 0 \n" +
                "W 10 \n" +
                " \n" +
                "87654321 4321 4321 \n" +
                "0 0 \n" +
                "W 10 \n" +
                "B ";


        String expectedOutput =
                "500\n" +
                "400\n" +
                "90\n" +
                "FUNDS_ERR\n" +
                "0";

        assertThat(container.process(input), equalTo(expectedOutput));

    }
}
