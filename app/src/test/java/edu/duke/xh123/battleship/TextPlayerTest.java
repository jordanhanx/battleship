package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h);
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

    @Test
    public void test_read_placement() throws IOException {
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
        expectedBodys[1] = "A  | | | |  A\n" +
                "B  | |d| |  B\n" +
                "C  | |d| |  C\n" +
                "D  | |d| |  D\n" +
                "E  | | | |  E\n";
        expectedBodys[2] = "A  | | | |  A\n" +
                "B  | |d| |  B\n" +
                "C  | |d| |  C\n" +
                "D  | |d| |  D\n" +
                "E  | | | |  E\n";
        for (int i = 0; i < expectedBodys.length; ++i) {
            player.doOnePlacement();
            assertEquals(prompt + expectedHeader + expectedBodys[i] + expectedHeader, bytes.toString());
            bytes.reset();
        }
    }

    @Test
    public void test_doPlacementPhase() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 5, "B2V\n", bytes);

        String prompt = "Player A where do you want to place a Destroyer?\n";
        String header = "  0|1|2|3|4\n";
        String emptyBody = "A  | | | |  A\n"
                + "B  | | | |  B\n"
                + "C  | | | |  C\n"
                + "D  | | | |  D\n"
                + "E  | | | |  E\n";
        String msg = "Player A: you are going to place the following ships (which are all\n"
                + "rectangular). For each ship, type the coordinate of the upper left\n"
                + "side of the ship, followed by either H (for horizontal) or V (for\n"
                + "vertical).  For example M4H would place a ship horizontally starting\n"
                + "at M4 and going to the right.  You have\n" + "\n"
                + "2 \"Submarines\" ships that are 1x2\n"
                + "2 \"Destroyers\" that are 1x3\n"
                + "3 \"Battleships\" that are 1x4\n"
                + "2 \"Carriers\" that are 1x6\n";
        String body = "A  | | | |  A\n"
                + "B  | |d| |  B\n"
                + "C  | |d| |  C\n"
                + "D  | |d| |  D\n"
                + "E  | | | |  E\n";
        player.doPlacementPhase();
        assertEquals(header + emptyBody + header + msg + prompt + header + body + header, bytes.toString());
        bytes.reset();
    }

}
