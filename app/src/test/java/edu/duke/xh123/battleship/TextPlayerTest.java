package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

public class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h);
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

    @Test
    public void test_readPlacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        for (int i = 0; i < expected.length; i++) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, expected[i]); // did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
            bytes.reset(); // clear out bytes for next time around
        }
    }

    @Test
    public void test_readPlacement_EOF() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "", bytes);
        String prompt = "Please enter a location for a ship:";
        assertThrows(IllegalArgumentException.class, () -> player.readPlacement(prompt));
    }

    @Test
    public void test_doOnePlacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 5, "B2V\nd1h\ne3h\n", bytes);

        String prompt = "Player A where do you want to place a Destroyer?\n";
        String expectedHeader = "  0|1|2|3|4\n";
        String[] expectedBodys = new String[3];
        expectedBodys[0] = "A  | | | |  A\n" +
                "B  | |d| |  B\n" +
                "C  | |d| |  C\n" +
                "D  | |d| |  D\n" +
                "E  | | | |  E\n";
        // expectedBodys[1] = "A | | | | A\n" +
        // "B | |d| | B\n" +
        // "C | |d| | C\n" +
        // "D | |d| | D\n" +
        // "E | | | | E\n";
        expectedBodys[1] = "That placement is invalid: the ship overlaps another ship.\n";
        // expectedBodys[2] = "A | | | | A\n" +
        // "B | |d| | B\n" +
        // "C | |d| | C\n" +
        // "D | |d| | D\n" +
        // "E | | | | E\n";
        expectedBodys[2] = "That placement is invalid: the ship goes off the right of the board.\n";
        for (int i = 0; i < expectedBodys.length; ++i) {
            player.doOnePlacement("Destroyer", (p) -> new V1ShipFactory().makeDestroyer(p));
            if (i == 0) {
                assertEquals(prompt + expectedHeader + expectedBodys[i] + expectedHeader, bytes.toString());
            } else {
                assertEquals(prompt + expectedBodys[i], bytes.toString());
            }
            bytes.reset();
        }
    }

    @Test
    public void test_doPlacementPhase_EOF() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 5, "", bytes);

        String prompt = "Player A where do you want to place a Destroyer?\n";
        player.doOnePlacement("Destroyer", (p) -> new V1ShipFactory().makeDestroyer(p));
        assertEquals(prompt + "That placement is invalid: it does not have the correct format.\n", bytes.toString());
    }

    // @Disabled
    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE) // Ensure proper serialization of
                                                                                      // the tests in parallel.
    public void test_doPlacementPhase() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);

        InputStream input = getClass().getClassLoader().getResourceAsStream("input_playerA.txt");
        assertNotNull(input);

        InputStream expectedStream = getClass().getClassLoader()
                .getResourceAsStream("output_playerA.txt");
        assertNotNull(expectedStream);

        Board<Character> board = new BattleShipBoard<Character>(10, 20);
        V1ShipFactory shipFactory = new V1ShipFactory();
        TextPlayer player = new TextPlayer("A", board, new BufferedReader(new InputStreamReader(input)), out,
                shipFactory);
        player.doPlacementPhase();

        String expected = new String(expectedStream.readAllBytes());
        String actual = bytes.toString();
        assertEquals(expected, actual);
    }

}
