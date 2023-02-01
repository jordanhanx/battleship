package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
    @Test
    public void test_InBoundsRule() {
        Board<Character> b_10by20 = new BattleShipBoard<Character>(10, 20);
        AbstractShipFactory<Character> shipFactory = new V1ShipFactory();
        Ship<Character> s1 = shipFactory.makeSubmarine(new Placement(new Coordinate(0, 0), 'v'));
        Ship<Character> s2 = shipFactory.makeSubmarine(new Placement(new Coordinate(-1, 0), 'v'));
        Ship<Character> s3 = shipFactory.makeSubmarine(new Placement(new Coordinate(18, 0), 'v'));
        Ship<Character> s4 = shipFactory.makeSubmarine(new Placement(new Coordinate(19, 0), 'v'));
        Ship<Character> s5 = shipFactory.makeSubmarine(new Placement(new Coordinate(0, 0), 'h'));
        Ship<Character> s6 = shipFactory.makeSubmarine(new Placement(new Coordinate(0, -1), 'h'));
        Ship<Character> s7 = shipFactory.makeSubmarine(new Placement(new Coordinate(19, 8), 'h'));
        Ship<Character> s8 = shipFactory.makeSubmarine(new Placement(new Coordinate(19, 9), 'h'));
        InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
        assertEquals(null, checker.checkPlacement(s1, b_10by20));
        assertEquals("That placement is invalid: the ship goes off the top of the board.",
                checker.checkPlacement(s2, b_10by20));
        assertEquals(null, checker.checkPlacement(s3, b_10by20));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.",
                checker.checkPlacement(s4, b_10by20));
        assertEquals(null, checker.checkPlacement(s5, b_10by20));
        assertEquals("That placement is invalid: the ship goes off the left of the board.",
                checker.checkPlacement(s6, b_10by20));
        assertEquals(null, checker.checkPlacement(s7, b_10by20));
        assertEquals("That placement is invalid: the ship goes off the right of the board.",
                checker.checkPlacement(s8, b_10by20));
    }
}
