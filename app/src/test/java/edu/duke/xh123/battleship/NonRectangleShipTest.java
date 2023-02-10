package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class NonRectangleShipTest {
    @Test
    public void test_makeCoords() {
        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        HashSet<Coordinate> s1 = NonRectangleShip.makeCoords(2, 3, r);
        assertFalse(s1.isEmpty());
        s1.remove(new Coordinate(0, 1));
        s1.remove(new Coordinate(1, 0));
        s1.remove(new Coordinate(1, 1));
        s1.remove(new Coordinate(2, 0));
        assertTrue(s1.isEmpty());
    }

    @Test
    public void test_constructor() {
        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        NonRectangleShip<Character> ship = new NonRectangleShip<>("Battleship", new Placement("B2?"), 2, 3, r, 'b',
                '*');
        assertFalse(ship.occupiesCoordinates(new Coordinate(1, 2)));
        assertTrue(ship.occupiesCoordinates(new Coordinate(2, 2)));
        assertTrue(ship.occupiesCoordinates(new Coordinate(3, 2)));
        assertTrue(ship.occupiesCoordinates(new Coordinate(1, 3)));
        assertTrue(ship.occupiesCoordinates(new Coordinate(2, 3)));
        assertFalse(ship.occupiesCoordinates(new Coordinate(3, 3)));
        assertFalse(ship.occupiesCoordinates(new Coordinate(1, 1)));
        assertFalse(ship.occupiesCoordinates(new Coordinate(3, 4)));
    }

    @Test
    public void test_recordHitAt_and_wasHitAt() {
        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        NonRectangleShip<Character> ship = new NonRectangleShip<>("Battleship", new Placement("B2?"), 2, 3, r, 'b',
                '*');

        assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(new Coordinate(1, 2)));
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 3)));
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(3, 2)));
        assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(new Coordinate(3, 3)));

        assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(new Coordinate(1, 2)));
        assertTrue(ship.wasHitAt(new Coordinate(1, 3)));
        assertFalse(ship.wasHitAt(new Coordinate(2, 2)));
        assertFalse(ship.wasHitAt(new Coordinate(2, 3)));
        assertTrue(ship.wasHitAt(new Coordinate(3, 2)));
        assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(new Coordinate(3, 3)));
    }

    @Test
    public void test_isSunk() {
        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        NonRectangleShip<Character> ship = new NonRectangleShip<>("Battleship", new Placement("B2?"), 2, 3, r, 'b',
                '*');

        assertFalse(ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 3)));
        assertFalse(ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(2, 2)));
        assertFalse(ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(2, 3)));
        assertFalse(ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(3, 2)));
        assertTrue(ship.isSunk());
    }

    @Test
    public void test_getDisplayInfoAt() {
        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        NonRectangleShip<Character> ship = new NonRectangleShip<>("Battleship", new Placement("B2?"), 2, 3, r, 'b',
                '*');

        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 3)));
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(3, 2)));
        assertThrows(IllegalArgumentException.class, () -> ship.getDisplayInfoAt(new Coordinate(1, 2), true));
        assertEquals('*', ship.getDisplayInfoAt(new Coordinate(1, 3), true));
        assertEquals('b', ship.getDisplayInfoAt(new Coordinate(1, 3), false));
        assertEquals('b', ship.getDisplayInfoAt(new Coordinate(2, 2), true));
        assertEquals(null, ship.getDisplayInfoAt(new Coordinate(2, 2), false));
        assertEquals('b', ship.getDisplayInfoAt(new Coordinate(2, 3), true));
        assertEquals(null, ship.getDisplayInfoAt(new Coordinate(2, 3), false));
        assertEquals('*', ship.getDisplayInfoAt(new Coordinate(3, 2), true));
        assertEquals('b', ship.getDisplayInfoAt(new Coordinate(3, 2), false));
        assertThrows(IllegalArgumentException.class, () -> ship.getDisplayInfoAt(new Coordinate(3, 3), true));
    }

    @Test
    public void test_getName() {
        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        NonRectangleShip<Character> ship = new NonRectangleShip<>("Battleship", new Placement("B2?"), 2, 3, r, 'b',
                '*');

        assertEquals("Battleship", ship.getName());
    }

    @Test
    public void test_getCoordinates() {
        HashSet<Coordinate> cmp_set = new HashSet<>();
        cmp_set.add(new Coordinate(1, 3));
        cmp_set.add(new Coordinate(2, 2));
        cmp_set.add(new Coordinate(2, 3));
        cmp_set.add(new Coordinate(3, 2));

        HashSet<Coordinate> r = new HashSet<>();
        r.add(new Coordinate(0, 0));
        r.add(new Coordinate(2, 1));
        NonRectangleShip<Character> ship = new NonRectangleShip<>("Battleship", new Placement("B2?"), 2, 3, r, 'b',
                '*');

        Iterable<Coordinate> s1 = ship.getCoordinates();
        int cnt = 0;
        for (Coordinate c : s1) {
            assertEquals(true, cmp_set.contains(c));
            cnt++;
        }
        assertEquals(cmp_set.size(), cnt);
    }
}
