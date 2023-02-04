package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
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
        assertThrows(EOFException.class, () -> player.readPlacement(prompt));
    }

    @Test
    public void test_doOnePlacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(5, 5, "A0H\na0v\nE0V\nB0x\nb0h", bytes);

        String[] expect = new String[2];
        expect[0] = "Player A where do you want to place a Destroyer?\n" +
                "  0|1|2|3|4\n" +
                "A d|d|d| |  A\n" +
                "B  | | | |  B\n" +
                "C  | | | |  C\n" +
                "D  | | | |  D\n" +
                "E  | | | |  E\n" +
                "  0|1|2|3|4\n";
        expect[1] = "Player A where do you want to place a Destroyer?\n" +
                "That placement is invalid: the ship overlaps another ship.\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "That placement is invalid: the ship goes off the bottom of the board.\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "That placement is invalid: it does not have the correct format.\n" +
                "Player A where do you want to place a Destroyer?\n" +
                "  0|1|2|3|4\n" +
                "A d|d|d| |  A\n" +
                "B d|d|d| |  B\n" +
                "C  | | | |  C\n" +
                "D  | | | |  D\n" +
                "E  | | | |  E\n" +
                "  0|1|2|3|4\n";
        for (int i = 0; i < expect.length; ++i) {
            player.doOnePlacement("Destroyer", (p) -> new V1ShipFactory().makeDestroyer(p));
            if (i == 0) {
                assertEquals(expect[i], bytes.toString());
            } else {
                assertEquals(expect[i], bytes.toString());
            }
            bytes.reset();
        }
    }

    @Test
    public void test_doPlacementPhase() throws IOException {
        BufferedReader input = new BufferedReader(new StringReader("A0V\nB0H\nB1v\nd2V\nC2v\nAaX\nA4x\na4V"));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(6, 6, 'X');
        TextPlayer tp1 = new TextPlayer("test1", board, input, output, new V1ShipFactory()) {
            protected void setupShipCreationList() {
                shipsToPlace.addAll(Collections.nCopies(1, "Submarine"));
                shipsToPlace.addAll(Collections.nCopies(1, "Destroyer"));
                shipsToPlace.addAll(Collections.nCopies(1, "Battleship"));
                shipsToPlace.addAll(Collections.nCopies(1, "Carrier"));
            }
        };
        tp1.doPlacementPhase();
        String expectedOutput = "  0|1|2|3|4|5\n" +
                "A  | | | | |  A\n" +
                "B  | | | | |  B\n" +
                "C  | | | | |  C\n" +
                "D  | | | | |  D\n" +
                "E  | | | | |  E\n" +
                "F  | | | | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player test1: you are going to place the following ships (which are all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n\n" +
                "2 \"Submarines\" ships that are 1x2\n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n" +
                "Player test1 where do you want to place a Submarine?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | | |  A\n" +
                "B s| | | | |  B\n" +
                "C  | | | | |  C\n" +
                "D  | | | | |  D\n" +
                "E  | | | | |  E\n" +
                "F  | | | | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player test1 where do you want to place a Destroyer?\n" +
                "That placement is invalid: the ship overlaps another ship.\n" +
                "Player test1 where do you want to place a Destroyer?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | | |  A\n" +
                "B s|d| | | |  B\n" +
                "C  |d| | | |  C\n" +
                "D  |d| | | |  D\n" +
                "E  | | | | |  E\n" +
                "F  | | | | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player test1 where do you want to place a Battleship?\n" +
                "That placement is invalid: the ship goes off the bottom of the board.\n" +
                "Player test1 where do you want to place a Battleship?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | | |  A\n" +
                "B s|d| | | |  B\n" +
                "C  |d|b| | |  C\n" +
                "D  |d|b| | |  D\n" +
                "E  | |b| | |  E\n" +
                "F  | |b| | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player test1 where do you want to place a Carrier?\n" +
                "That placement is invalid: it does not have the correct format.\n" +
                "Player test1 where do you want to place a Carrier?\n" +
                "That placement is invalid: it does not have the correct format.\n" +
                "Player test1 where do you want to place a Carrier?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | |c|  A\n" +
                "B s|d| | |c|  B\n" +
                "C  |d|b| |c|  C\n" +
                "D  |d|b| |c|  D\n" +
                "E  | |b| |c|  E\n" +
                "F  | |b| |c|  F\n" +
                "  0|1|2|3|4|5\n";
        assertEquals(expectedOutput, bytes.toString());
    }
}
