package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
    @Test
    void test_MakeCoords() {
        HashSet<Coordinate> s1 = RectangleShip.makeCoords(new Coordinate(1, 2), 2, 3);
        assertEquals(false, s1.isEmpty());
        s1.remove(new Coordinate(1, 2));
        s1.remove(new Coordinate(2, 2));
        s1.remove(new Coordinate(3, 2));
        s1.remove(new Coordinate(1, 3));
        s1.remove(new Coordinate(2, 3));
        s1.remove(new Coordinate(3, 3));
        assertEquals(true, s1.isEmpty());
    }

    @Test
    void test_constructor() {
        RectangleShip<Character> ship = new RectangleShip<>(new Coordinate(1, 2), 2, 3, 's', '*');
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(1, 2)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(2, 2)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(3, 2)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(1, 3)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(2, 3)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(3, 3)));
        assertEquals(false, ship.occupiesCoordinates(new Coordinate(1, 1)));
        assertEquals(false, ship.occupiesCoordinates(new Coordinate(3, 4)));
    }

    @Test
    void test_recordHitAt_and_wasHitAt() {
        RectangleShip<Character> ship = new RectangleShip<>(new Coordinate(1, 2), 2, 3, 's', '*');
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 2)));
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(2, 2)));
        assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(new Coordinate(3, 4)));

        assertEquals(true, ship.wasHitAt(new Coordinate(1, 2)));
        assertEquals(true, ship.wasHitAt(new Coordinate(2, 2)));
        assertEquals(false, ship.wasHitAt(new Coordinate(3, 2)));
        assertEquals(false, ship.wasHitAt(new Coordinate(1, 3)));
        assertEquals(false, ship.wasHitAt(new Coordinate(2, 3)));
        assertEquals(false, ship.wasHitAt(new Coordinate(3, 3)));
        assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(new Coordinate(3, 4)));
    }

    @Test
    void test_isSunk() {
        RectangleShip<Character> ship = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
        assertEquals(false, ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 2)));
        assertEquals(false, ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(2, 2)));
        assertEquals(false, ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(3, 2)));
        assertEquals(true, ship.isSunk());
    }

    @Test
    void test_getDisplayInfoAt() {
        RectangleShip<Character> ship = new RectangleShip<>(new Coordinate(1, 2), 1, 3, 's', '*');
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 2)));
        assertEquals('*', ship.getDisplayInfoAt(new Coordinate(1, 2)));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(2, 2)));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(3, 2)));
    }
}
