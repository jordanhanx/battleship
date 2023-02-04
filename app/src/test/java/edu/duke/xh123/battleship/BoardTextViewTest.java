package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<>(11, 20, 'X');
        Board<Character> tallBoard = new BattleShipBoard<>(10, 27, 'X');
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }

    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
        Board<Character> b1 = new BattleShipBoard<>(w, h, 'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    public void test_display_empty_2by2() {
        String expectedHeader = "  0|1\n";
        String expectedBody = "A  |  A\n" +
                "B  |  B\n";
        emptyBoardHelper(2, 2, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_empty_3by2() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody = "A  | |  A\n" +
                "B  | |  B\n";
        emptyBoardHelper(3, 2, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_empty_3by5() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody = "A  | |  A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n";
        emptyBoardHelper(3, 5, expectedHeader, expectedBody);
    }

    @Test
    public void test_display_notEmpty_3by4() {
        Board<Character> b1 = new BattleShipBoard<>(3, 4, 'X');
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*'));
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(3, 0), 's', '*'));
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 2), 's', '*'));
        b1.tryAddShip(new RectangleShip<Character>(new Coordinate(3, 2), 's', '*'));
        BoardTextView view = new BoardTextView(b1);
        String expectedHeader = "  0|1|2\n";
        String expectedBody = "A s| |s A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "D s| |s D\n";
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    public void test_displayEnemyBoard() {
        Board<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
        BoardTextView view = new BoardTextView(b1);
        b1.tryAddShip(new V1ShipFactory().makeSubmarine(new Placement("B0H")));
        b1.tryAddShip(new V1ShipFactory().makeDestroyer(new Placement("A3V")));
        b1.fireAt(new Coordinate(0, 0));
        b1.fireAt(new Coordinate(0, 3));
        b1.fireAt(new Coordinate(1, 1));
        String selfView = "  0|1|2|3\n" +
                "A  | | |* A\n" +
                "B s|*| |d B\n" +
                "C  | | |d C\n" +
                "  0|1|2|3\n";
        String enemyView = "  0|1|2|3\n" +
                "A X| | |d A\n" +
                "B  |s| |  B\n" +
                "C  | | |  C\n" +
                "  0|1|2|3\n";
        assertEquals(selfView, view.displayMyOwnBoard());
        assertEquals(enemyView, view.displayEnemyBoard());
    }

    @Test
    public void test_displayMyBoardWithEnemyNextToIt() {
        Board<Character> b1 = new BattleShipBoard<>(4, 3, 'X');
        BoardTextView view = new BoardTextView(b1);
        b1.tryAddShip(new V1ShipFactory().makeSubmarine(new Placement("B0H")));
        b1.tryAddShip(new V1ShipFactory().makeDestroyer(new Placement("A3V")));
        b1.fireAt(new Coordinate(0, 0));
        b1.fireAt(new Coordinate(0, 3));
        b1.fireAt(new Coordinate(1, 1));
        String expect = "     h1                       h2\n" +
                "  0|1|2|3                    0|1|2|3\n" +
                "A  | | |* A                A X| | |d A\n" +
                "B s|*| |d B                B  |s| |  B\n" +
                "C  | | |d C                C  | | |  C\n" +
                "  0|1|2|3                    0|1|2|3\n";
        assertEquals(expect, view.displayMyBoardWithEnemyNextToIt(view, "h1", "h2"));
    }
}
