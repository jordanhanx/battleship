package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {
    private void checkShip(Ship<Character> testShip, String expectedName,
            char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());
        for (Coordinate c : expectedLocs) {
            assertEquals(expectedLetter, testShip.getDisplayInfoAt(c));
        }
    }

    @Test
    public void test_MakeBattleship() {
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> s1 = new V1ShipFactory().makeBattleship(v1_2);
        checkShip(s1, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
                new Coordinate(4, 2));

        Placement h1_2 = new Placement(new Coordinate(1, 2), 'H');
        Ship<Character> s2 = new V1ShipFactory().makeBattleship(h1_2);
        checkShip(s2, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
                new Coordinate(1, 5));

        Placement neg_v1_1 = new Placement(new Coordinate(-1, -1), 'V');
        Ship<Character> s3 = new V1ShipFactory().makeBattleship(neg_v1_1);
        checkShip(s3, "Battleship", 'b', new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(1, -1),
                new Coordinate(2, -1));
    }

    @Test
    public void test_MakeCarrier() {
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> s1 = new V1ShipFactory().makeCarrier(v1_2);
        checkShip(s1, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2),
                new Coordinate(4, 2), new Coordinate(5, 2), new Coordinate(6, 2));

        Placement h1_2 = new Placement(new Coordinate(1, 2), 'H');
        Ship<Character> s2 = new V1ShipFactory().makeCarrier(h1_2);
        checkShip(s2, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
                new Coordinate(1, 5), new Coordinate(1, 6), new Coordinate(1, 7));

        Placement neg_v1_1 = new Placement(new Coordinate(-1, -1), 'V');
        Ship<Character> s3 = new V1ShipFactory().makeCarrier(neg_v1_1);
        checkShip(s3, "Carrier", 'c', new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(1, -1),
                new Coordinate(2, -1), new Coordinate(3, -1), new Coordinate(4, -1));
    }

    @Test
    public void test_MakeDestroyer() {
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> s1 = new V1ShipFactory().makeDestroyer(v1_2);
        checkShip(s1, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));

        Placement h1_2 = new Placement(new Coordinate(1, 2), 'H');
        Ship<Character> s2 = new V1ShipFactory().makeDestroyer(h1_2);
        checkShip(s2, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));

        Placement neg_v1_1 = new Placement(new Coordinate(-1, -1), 'V');
        Ship<Character> s3 = new V1ShipFactory().makeDestroyer(neg_v1_1);
        checkShip(s3, "Destroyer", 'd', new Coordinate(-1, -1), new Coordinate(0, -1), new Coordinate(1, -1));
    }

    @Test
    public void test_MakeSubmarine() {
        Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
        Ship<Character> s1 = new V1ShipFactory().makeSubmarine(v1_2);
        checkShip(s1, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

        Placement h1_2 = new Placement(new Coordinate(1, 2), 'H');
        Ship<Character> s2 = new V1ShipFactory().makeSubmarine(h1_2);
        checkShip(s2, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));

        Placement neg_v1_1 = new Placement(new Coordinate(-1, -1), 'V');
        Ship<Character> s3 = new V1ShipFactory().makeSubmarine(neg_v1_1);
        checkShip(s3, "Submarine", 's', new Coordinate(-1, -1), new Coordinate(0, -1));
    }

    @Test
    public void test_invalidPlacement() {
        Placement invalid_p = new Placement(new Coordinate(1, 2), 'x');
        assertThrows(IllegalArgumentException.class, () -> new V1ShipFactory().makeBattleship(invalid_p));
    }
}
