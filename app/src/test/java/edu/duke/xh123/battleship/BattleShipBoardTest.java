package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
    @Test
    void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<Character>(10, 20);
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 30));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
    }

    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, Character[][] expect) {
        for (int row = 0; row < expect.length; row++) {
            for (int col = 0; col < expect[row].length; col++) {
                assertEquals(expect[row][col], b.whatIsAt(new Coordinate(row, col)));
            }
        }
    }

    @Test
    public void test_whatIsAt() {
        BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10);
        Character[][] expect = new Character[10][10];
        checkWhatIsAtBoard(b, expect);
        if (b.tryAddShip(new BasicShip(new Coordinate(0, 0)))) {
            expect[0][0] = 's';
        }
        if (b.tryAddShip(new BasicShip(new Coordinate(9, 0)))) {
            expect[0][9] = 's';
        }
        if (b.tryAddShip(new BasicShip(new Coordinate(0, 9)))) {
            expect[9][0] = 's';
        }
        if (b.tryAddShip(new BasicShip(new Coordinate(9, 9)))) {
            expect[9][9] = 's';
        }
        if (b.tryAddShip(new BasicShip(new Coordinate(5, 5)))) {
            expect[5][5] = 's';
        }
        checkWhatIsAtBoard(b, expect);
    }
}