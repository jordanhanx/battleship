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
    private TextPlayer createTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer(name, board, input, output, shipFactory);
    }

    private TextPlayer createPoorTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer(name, board, input, output, shipFactory) {
            protected void setupShipCreationList() {
                shipsToPlace.addAll(Collections.nCopies(1, "Submarine"));
            }
        };
    }

    private TextPlayer createNoTokenTextPlayer(String name, int w, int h, String inputData, OutputStream bytes,
            int moveToken, int sonarToken) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(w, h, 'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer(name, board, input, output, shipFactory) {
            protected void setupTokens() {
                moveShipToken = moveToken;
                sonarScanToken = sonarToken;
            }

            protected void setupShipCreationList() {
                shipsToPlace.addAll(Collections.nCopies(1, "Submarine"));
            }
        };
    }

    @Test
    public void test_readPlacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("Test", 10, 20, "B2V\nC8H\na4v\nd7U\ne3d\na0l\ng3R", bytes);

        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[7];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');
        expected[3] = new Placement(new Coordinate(3, 7), 'U');
        expected[4] = new Placement(new Coordinate(4, 3), 'D');
        expected[5] = new Placement(new Coordinate(0, 0), 'L');
        expected[6] = new Placement(new Coordinate(6, 3), 'R');

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
        TextPlayer player = createTextPlayer("Test", 10, 20, "", bytes);
        String prompt = "Please enter a location for a ship:";
        assertThrows(EOFException.class, () -> player.readPlacement(prompt));
    }

    @Test
    public void test_readCoordinate() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("Test", 10, 20, "B2\nC8\na4\n", bytes);

        String prompt = "Please enter a coordinate:";
        Coordinate[] expected = new Coordinate[3];
        expected[0] = new Coordinate(1, 2);
        expected[1] = new Coordinate(2, 8);
        expected[2] = new Coordinate(0, 4);

        for (int i = 0; i < expected.length; i++) {
            Coordinate c = player.readCoordinate(prompt);
            assertEquals(expected[i], c); // did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); // should have printed prompt and newline
            bytes.reset(); // clear out bytes for next time around
        }
    }

    @Test
    public void test_readCoordinate_EOF() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("Test", 10, 20, "", bytes);
        String prompt = "Please enter a coordinate:";
        assertThrows(EOFException.class, () -> player.readCoordinate(prompt));
    }

    @Test
    public void test_doOnePlacement() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer("Test", 5, 5, "A0H\na0v\nE0V\nB0x\nb0h", bytes);

        String[] expect = new String[2];
        expect[0] = "Player Test where do you want to place a Destroyer?\n" +
                "  0|1|2|3|4\n" +
                "A d|d|d| |  A\n" +
                "B  | | | |  B\n" +
                "C  | | | |  C\n" +
                "D  | | | |  D\n" +
                "E  | | | |  E\n" +
                "  0|1|2|3|4\n";
        expect[1] = "Player Test where do you want to place a Destroyer?\n" +
                "That placement is invalid: the ship overlaps another ship.\n" +
                "Player Test where do you want to place a Destroyer?\n" +
                "That placement is invalid: the ship goes off the bottom of the board.\n" +
                "Player Test where do you want to place a Destroyer?\n" +
                "That placement is invalid: it does not have the correct format.\n" +
                "Player Test where do you want to place a Destroyer?\n" +
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
        BufferedReader input = new BufferedReader(new StringReader("A0V\nB0H\nB1v\ne2r\nd2r\nAau\nA3V\na3U"));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<>(6, 6, 'X');
        TextPlayer tp1 = new TextPlayer("Test", board, input, output, new V2ShipFactory()) {
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
                "Player Test: you are going to place the following ships (which are not all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical) for Submarines/Destroyers and one in U (for Up), D (for\n" +
                "Down), L (for Left) and R (for Rigth) for Battleships/Carriers.\n" +
                "For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n" + "\n" +
                "2 \"Submarines\" ships that are 1x2\n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that that are now shaped as shown below\n" +
                "      b      OR    b         bbb         b\n" +
                "     bbb           bb   OR    b     OR  bb\n" +
                "                   b                     b\n\n" +
                "      Up         Right       Down      Left\n" +
                "2 \"Carriers\" that are now shaped as shown below\n" +
                "      c                       c             \n" +
                "      c           cccc        cc         ccc\n" +
                "      cc   OR    ccc      OR  cc   OR  cccc\n" +
                "      cc                       c\n" +
                "       c                       c\n\n" +
                "      Up         Right       Down      Left\n" +
                "Player Test where do you want to place a Submarine?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | | |  A\n" +
                "B s| | | | |  B\n" +
                "C  | | | | |  C\n" +
                "D  | | | | |  D\n" +
                "E  | | | | |  E\n" +
                "F  | | | | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player Test where do you want to place a Destroyer?\n" +
                "That placement is invalid: the ship overlaps another ship.\n" +
                "Player Test where do you want to place a Destroyer?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | | |  A\n" +
                "B s|d| | | |  B\n" +
                "C  |d| | | |  C\n" +
                "D  |d| | | |  D\n" +
                "E  | | | | |  E\n" +
                "F  | | | | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player Test where do you want to place a Battleship?\n" +
                "That placement is invalid: the ship goes off the bottom of the board.\n" +
                "Player Test where do you want to place a Battleship?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | | | |  A\n" +
                "B s|d| | | |  B\n" +
                "C  |d| | | |  C\n" +
                "D  |d|b| | |  D\n" +
                "E  | |b|b| |  E\n" +
                "F  | |b| | |  F\n" +
                "  0|1|2|3|4|5\n" +
                "Player Test where do you want to place a Carrier?\n" +
                "That placement is invalid: it does not have the correct format.\n" +
                "Player Test where do you want to place a Carrier?\n" +
                "That placement is invalid: it does not have the correct format.\n" +
                "Player Test where do you want to place a Carrier?\n" +
                "  0|1|2|3|4|5\n" +
                "A s| | |c| |  A\n" +
                "B s|d| |c| |  B\n" +
                "C  |d| |c|c|  C\n" +
                "D  |d|b|c|c|  D\n" +
                "E  | |b|b|c|  E\n" +
                "F  | |b| | |  F\n" +
                "  0|1|2|3|4|5\n";
        assertEquals(expectedOutput, bytes.toString());
    }

    @Test
    public void test_isLost() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createPoorTextPlayer("Test", 5, 5, "B1V", bytes);
        p1.doPlacementPhase();

        assertFalse(p1.isLost());
        p1.theBoard.fireAt(new Coordinate(1, 1));
        assertFalse(p1.isLost());
        p1.theBoard.fireAt(new Coordinate(2, 1));
        assertTrue(p1.isLost());
    }

    @Test
    public void test_announceVictory() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createPoorTextPlayer("Test1", 4, 3, "A0H", bytes);
        p1.announceVictory();
        assertEquals("Player Test1 is the winner!\n", bytes.toString());
    }

    @Test
    public void test_openFire() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createPoorTextPlayer("Test1", 4, 3, "B1V\nB1\nA1", bytes);
        TextPlayer p2 = createPoorTextPlayer("Test2", 4, 3, "A0H\nB1", bytes);
        p1.doPlacementPhase();
        p2.doPlacementPhase();
        bytes.reset();

        p1.openFire(p2.theBoard);
        p1.openFire(p2.theBoard);
        p2.openFire(p1.theBoard);

        StringBuilder expect = new StringBuilder();
        expect.append("Player Test1 where do you want to fire at?\n");
        expect.append("You missed!\n");
        expect.append("Player Test1 where do you want to fire at?\n");
        expect.append("You hit a Submarine!\n");
        expect.append("Player Test2 where do you want to fire at?\n");
        expect.append("You hit a Submarine!\n");

        assertEquals(expect.toString(), bytes.toString());
    }

    @Test
    public void test_moveShip() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createPoorTextPlayer("Test1", 4, 3, "B1V\nD1\nC1\nC2H", bytes);
        p1.doPlacementPhase();
        bytes.reset();

        assertEquals('s', p1.theBoard.whatIsAtForSelf(new Coordinate("B1")));
        assertEquals('s', p1.theBoard.whatIsAtForSelf(new Coordinate("C1")));

        assertThrows(IllegalArgumentException.class, () -> p1.moveShip());
        assertDoesNotThrow(() -> p1.moveShip());

        StringBuilder expect = new StringBuilder("");
        expect.append("Player Test1 which ship do you want to move? (input one Coordinate occupied by the ship)\n");
        expect.append("Player Test1 which ship do you want to move? (input one Coordinate occupied by the ship)\n");
        expect.append("Player Test1 where do you want to place the Submarine?\n");
        assertEquals(expect.toString(), bytes.toString());
        assertEquals('s', p1.theBoard.whatIsAtForSelf(new Coordinate("C2")));
        assertEquals('s', p1.theBoard.whatIsAtForSelf(new Coordinate("C3")));
    }

    @Test
    public void test_scanSonar() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createPoorTextPlayer("Test1", 4, 4, "B1V\nA0\nC3\nE4", bytes);
        TextPlayer p2 = createPoorTextPlayer("Test2", 4, 4, "A0H\n", bytes);
        p1.doPlacementPhase();
        p2.doPlacementPhase();
        bytes.reset();

        p1.scanSonar(p2.theBoard);
        p1.scanSonar(p2.theBoard);
        p1.scanSonar(p2.theBoard);
        StringBuilder expect = new StringBuilder("");
        expect.append("Player Test1 where do you want to scan with sonar?\n");
        expect.append("Submarines occupy 2 squares\n");
        expect.append("Player Test1 where do you want to scan with sonar?\n");
        expect.append("Submarines occupy 0 squares\n");
        expect.append("Player Test1 where do you want to scan with sonar?\n");
        expect.append("Submarines occupy 0 squares\n");
        assertEquals(expect.toString(), bytes.toString());
    }

    @Test
    public void test_printAvailableActions() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createNoTokenTextPlayer("Test1", 4, 4, "", bytes, 1, 1);
        TextPlayer p2 = createNoTokenTextPlayer("Test1", 4, 4, "", bytes, 1, 0);
        TextPlayer p3 = createNoTokenTextPlayer("Test1", 4, 4, "", bytes, 0, 1);
        TextPlayer p4 = createNoTokenTextPlayer("Test1", 4, 4, "", bytes, 0, 0);
        bytes.reset();

        p1.printAvailableActions();
        StringBuilder expect1 = new StringBuilder("");
        expect1.append("Possible actions for Player Test1:\n\n");
        expect1.append(" F Fire at a square\n");
        expect1.append(" M Move a ship to another square (1 remaining)\n");
        expect1.append(" S Sonar scan (1 remaining)\n");
        expect1.append("\nPlayer Test1, what would you like to do?\n");
        assertEquals(expect1.toString(), bytes.toString());
        bytes.reset();
        p2.printAvailableActions();
        StringBuilder expect2 = new StringBuilder("");
        expect2.append("Possible actions for Player Test1:\n\n");
        expect2.append(" F Fire at a square\n");
        expect2.append(" M Move a ship to another square (1 remaining)\n");
        expect2.append("\nPlayer Test1, what would you like to do?\n");
        assertEquals(expect2.toString(), bytes.toString());
        bytes.reset();
        p3.printAvailableActions();
        StringBuilder expect3 = new StringBuilder("");
        expect3.append("Possible actions for Player Test1:\n\n");
        expect3.append(" F Fire at a square\n");
        expect3.append(" S Sonar scan (1 remaining)\n");
        expect3.append("\nPlayer Test1, what would you like to do?\n");
        assertEquals(expect3.toString(), bytes.toString());
        bytes.reset();
        p4.printAvailableActions();
        assertEquals("", bytes.toString());
    }

    @Test
    public void test_takeAction() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createNoTokenTextPlayer("Test1", 4, 4, "B0H\n?\nf\nA0\nm\nB1\nC1V\nm\nS\nA0\nA1", bytes, 1, 1);
        TextPlayer p2 = createNoTokenTextPlayer("Test2", 4, 3, "A0H\ns\n", bytes, 1, 0);
        p1.doPlacementPhase();
        p2.doPlacementPhase();
        bytes.reset();

        assertThrows(IllegalArgumentException.class, () -> p1.takeAction(p2.theBoard));
        assertDoesNotThrow(() -> p1.takeAction(p2.theBoard));
        assertDoesNotThrow(() -> p1.takeAction(p2.theBoard));
        assertThrows(IllegalArgumentException.class, () -> p1.takeAction(p2.theBoard));
        assertDoesNotThrow(() -> p1.takeAction(p2.theBoard));
        assertDoesNotThrow(() -> p1.takeAction(p2.theBoard));
        assertThrows(IllegalArgumentException.class, () -> p2.takeAction(p1.theBoard));

        StringBuilder expect = new StringBuilder("");
        expect.append("Player Test1 where do you want to fire at?\n");
        expect.append("You hit a Submarine!\n");
        expect.append("Player Test1 which ship do you want to move? (input one Coordinate occupied by the ship)\n");
        expect.append("Player Test1 where do you want to place the Submarine?\n");
        expect.append("Player Test1 where do you want to scan with sonar?\n");
        expect.append("Submarines occupy 2 squares\n");
        expect.append("Player Test1 where do you want to fire at?\n");
        expect.append("You hit a Submarine!\n");
        assertEquals(expect.toString(), bytes.toString());
    }

    @Test
    public void test_playOneTurn() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer p1 = createPoorTextPlayer("Test1", 4, 4, "B0H\nm\na0\nf\nA0", bytes);
        TextPlayer p2 = createPoorTextPlayer("Test2", 4, 4, "A0H\n", bytes);
        p1.doPlacementPhase();
        p2.doPlacementPhase();
        bytes.reset();

        p1.playOneTurn(p2.theBoard, p2.view, p2.name);
        StringBuilder expect = new StringBuilder("");
        expect.append("Player Test1's turn:\n");
        expect.append("     Your ocean               Player Test2's ocean\n");
        expect.append("  0|1|2|3                    0|1|2|3\n");
        expect.append("A  | | |  A                A  | | |  A\n");
        expect.append("B s|s| |  B                B  | | |  B\n");
        expect.append("C  | | |  C                C  | | |  C\n");
        expect.append("D  | | |  D                D  | | |  D\n");
        expect.append("  0|1|2|3                    0|1|2|3\n");
        expect.append("Possible actions for Player Test1:\n\n");
        expect.append(" F Fire at a square\n");
        expect.append(" M Move a ship to another square (3 remaining)\n");
        expect.append(" S Sonar scan (3 remaining)\n");
        expect.append("\nPlayer Test1, what would you like to do?\n");
        expect.append("Player Test1 which ship do you want to move? (input one Coordinate occupied by the ship)\n");
        expect.append("Oops, there is no ship at (0, 0)\n");
        expect.append("Possible actions for Player Test1:\n\n");
        expect.append(" F Fire at a square\n");
        expect.append(" M Move a ship to another square (3 remaining)\n");
        expect.append(" S Sonar scan (3 remaining)\n");
        expect.append("\nPlayer Test1, what would you like to do?\n");
        expect.append("Player Test1 where do you want to fire at?\n");
        expect.append("You hit a Submarine!\n");
        assertEquals(expect.toString(), bytes.toString());
    }
}
