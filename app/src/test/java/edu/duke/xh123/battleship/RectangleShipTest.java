package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {
    @Test
    public void test_MakeCoords() {
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
    public void test_constructor() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(1, 2), 2, 3, 's', '*');
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
    public void test_recordHitAt_and_wasHitAt() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(1, 2), 2, 3, 's', '*');
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
    public void test_isSunk() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
        assertEquals(false, ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 2)));
        assertEquals(false, ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(2, 2)));
        assertEquals(false, ship.isSunk());
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(3, 2)));
        assertEquals(true, ship.isSunk());
    }

    @Test
    public void test_getDisplayInfoAt() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
        assertDoesNotThrow(() -> ship.recordHitAt(new Coordinate(1, 2)));
        assertEquals('*', ship.getDisplayInfoAt(new Coordinate(1, 2), true));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(2, 2), true));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(3, 2), true));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(1, 2), false));
        assertEquals(null, ship.getDisplayInfoAt(new Coordinate(2, 2), false));
        assertEquals(null, ship.getDisplayInfoAt(new Coordinate(3, 2), false));
    }

    @Test
    public void test_getName() {
        RectangleShip<Character> sub = new RectangleShip<>("submarine", new Coordinate(1, 2), 1, 3, 's', '*');
        assertEquals("submarine", sub.getName());
        RectangleShip<Character> most_powerful = new RectangleShip<>("Yamato", new Coordinate(1, 2), 1, 3, 's', '*');
        assertNotEquals("Missouri", most_powerful.getName());
        assertEquals("Yamato", most_powerful.getName());
    }

    @Test
    public void test_getCoordinates() {
        HashSet<Coordinate> cmp_set = new HashSet<>();
        cmp_set.add(new Coordinate(1, 2));
        cmp_set.add(new Coordinate(2, 2));
        cmp_set.add(new Coordinate(1, 3));
        cmp_set.add(new Coordinate(2, 3));
        RectangleShip<Character> ship = new RectangleShip<>("test", new Coordinate(1, 2), 2, 2, 's', '*');
        Iterable<Coordinate> s1 = ship.getCoordinates();
        int cnt = 0;
        for (Coordinate c : s1) {
            assertEquals(true, cmp_set.contains(c));
            cnt++;
        }
        assertEquals(cmp_set.size(), cnt);
    }
}
