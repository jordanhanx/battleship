package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
    private void checkShip(Ship<Character> testShip, String expectedName,
            char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate c : expectedLocs) {
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(c, true));
        }
    }

    @Test
    void test_makeBattleship() {
        V2ShipFactory v2 = new V2ShipFactory();

        Ship<Character> s1 = v2.makeBattleship(new Placement("A0U"));
        checkShip(s1, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1),
                new Coordinate(1, 2));

        Ship<Character> s2 = v2.makeBattleship(new Placement("a0d"));
        checkShip(s2, "Battleship", 'b', new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2),
                new Coordinate(1, 1));

        Ship<Character> s3 = v2.makeBattleship(new Placement("A0l"));
        checkShip(s3, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1),
                new Coordinate(2, 1));

        Ship<Character> s4 = v2.makeBattleship(new Placement("a0R"));
        checkShip(s4, "Battleship", 'b', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1),
                new Coordinate(2, 0));

        assertThrows(IllegalArgumentException.class, () -> v2.makeBattleship(new Placement("A0$")));
    }

    @Test
    void test_makeCarrier() {
        V2ShipFactory v2 = new V2ShipFactory();

        Ship<Character> s1 = v2.makeCarrier(new Placement("A0U"));
        checkShip(s1, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
                new Coordinate(2, 1), new Coordinate(3, 0), new Coordinate(3, 1), new Coordinate(4, 1));

        Ship<Character> s2 = v2.makeCarrier(new Placement("a0d"));
        checkShip(s2, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1),
                new Coordinate(2, 0), new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(4, 1));

        Ship<Character> s3 = v2.makeCarrier(new Placement("A0l"));
        checkShip(s3, "Carrier", 'c', new Coordinate(0, 2), new Coordinate(0, 3), new Coordinate(0, 4),
                new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3));

        Ship<Character> s4 = v2.makeCarrier(new Placement("a0R"));
        checkShip(s4, "Carrier", 'c', new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(0, 3),
                new Coordinate(0, 4), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2));

        assertThrows(IllegalArgumentException.class, () -> v2.makeCarrier(new Placement("A0?")));
    }
}
