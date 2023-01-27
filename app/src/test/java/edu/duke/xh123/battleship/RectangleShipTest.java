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
        RectangleShip ship = new RectangleShip<Character>(new Coordinate(1, 2), 2, 3, 's', '*');
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(1, 2)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(2, 2)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(3, 2)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(1, 3)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(2, 3)));
        assertEquals(true, ship.occupiesCoordinates(new Coordinate(3, 3)));
        assertEquals(false, ship.occupiesCoordinates(new Coordinate(1, 1)));
        assertEquals(false, ship.occupiesCoordinates(new Coordinate(3, 4)));
    }
}
