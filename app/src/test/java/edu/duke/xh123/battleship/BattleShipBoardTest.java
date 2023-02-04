package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 30, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
    }

    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, Character[][] expect, boolean isSelf) {
        for (int row = 0; row < expect.length; row++) {
            for (int col = 0; col < expect[row].length; col++) {
                if (isSelf) {
                    assertEquals(expect[row][col], b.whatIsAtForSelf(new Coordinate(row, col)));
                } else {
                    assertEquals(expect[row][col], b.whatIsAtForEnemy(new Coordinate(row, col)));
                }
            }
        }
    }

    @Test
    public void test_whatIsAt() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 10, 'X');
        Character[][] expect_self = new Character[10][10];
        Character[][] expect_enemy = new Character[10][10];
        checkWhatIsAtBoard(b, expect_self, true);
        checkWhatIsAtBoard(b, expect_enemy, false);
        if (b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*')) == null) {
            expect_self[0][0] = 's';
        }
        if (b.tryAddShip(new RectangleShip<Character>(new Coordinate(9, 0), 's',
                '*')) == null) {
            b.fireAt(new Coordinate(9, 0));
            expect_self[9][0] = '*';
            expect_enemy[9][0] = 's';
        }
        if (b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 9), 's',
                '*')) == null) {
            expect_self[0][9] = 's';
        }
        if (b.tryAddShip(new RectangleShip<Character>(new Coordinate(9, 9), 's',
                '*')) == null) {
            b.fireAt(new Coordinate(9, 9));
            expect_self[9][9] = '*';
            expect_enemy[9][9] = 's';
        }
        if (b.tryAddShip(new RectangleShip<Character>(new Coordinate(5, 5), 's',
                '*')) == null) {
            b.fireAt(new Coordinate(5, 5));
            expect_self[5][5] = '*';
            expect_enemy[5][5] = 's';
        }
        b.fireAt(new Coordinate(1, 1));
        expect_enemy[1][1] = 'X';
        b.fireAt(new Coordinate(2, 5));
        expect_enemy[2][5] = 'X';
        checkWhatIsAtBoard(b, expect_self, true);
        checkWhatIsAtBoard(b, expect_enemy, false);
    }

    @Test
    public void test_tryAddShip() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(10, 10, 'X');
        AbstractShipFactory<Character> shipFactory = new V1ShipFactory();
        Ship<Character> c1 = shipFactory.makeCarrier(new Placement(new Coordinate(0, 0), 'h'));
        assertEquals(null, b.tryAddShip(c1));

        Ship<Character> s1 = shipFactory.makeSubmarine(new Placement(new Coordinate(-1, 0), 'v'));
        Ship<Character> s2 = shipFactory.makeSubmarine(new Placement(new Coordinate(0, 0), 'v'));
        Ship<Character> s3 = shipFactory.makeSubmarine(new Placement(new Coordinate(1, 0), 'v'));

        assertEquals("That placement is invalid: the ship goes off the top of the board.", b.tryAddShip(s1));
        assertEquals("That placement is invalid: the ship overlaps another ship.", b.tryAddShip(s2));
        assertEquals(null, b.tryAddShip(s3));
    }

    @Test
    public void test_fireAt() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(5, 5, 'X');
        Ship<Character> s1 = new V1ShipFactory().makeSubmarine(new Placement("B1V"));
        b.tryAddShip(s1);
        assertFalse(s1.isSunk());
        for (int row = 0; row < b.getHeight(); ++row) {
            for (int col = 0; col < b.getWidth(); ++col) {
                if ((row == 1 || row == 2) && col == 1) {
                    assertSame(s1, b.fireAt(new Coordinate(row, col)));
                } else {
                    assertSame(null, b.fireAt(new Coordinate(row, col)));
                }
            }
        }
        assertTrue(s1.isSunk());
    }

    @Test
    public void test_shipsAreAllSunk() {
        BattleShipBoard<Character> b = new BattleShipBoard<>(5, 5, 'X');
        assertTrue(b.shipsAreAllSunk());
        Ship<Character> s1 = new V1ShipFactory().makeSubmarine(new Placement("B1V"));
        b.tryAddShip(s1);
        assertFalse(b.shipsAreAllSunk());
        b.fireAt(new Coordinate(1, 1));
        assertFalse(b.shipsAreAllSunk());
        b.fireAt(new Coordinate(2, 1));
        assertTrue(b.shipsAreAllSunk());
    }
}