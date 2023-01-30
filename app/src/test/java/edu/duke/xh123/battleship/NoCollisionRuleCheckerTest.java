package edu.duke.xh123.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
    @Test
    void test_NoCollisionRule() {
        Board<Character> b_10by20 = new BattleShipBoard<Character>(10, 20);
        AbstractShipFactory<Character> shipFactory = new V1ShipFactory();
        Ship<Character> c1 = shipFactory.makeCarrier(new Placement(new Coordinate(0, 0), 'V'));
        b_10by20.tryAddShip(c1);
        Ship<Character> s1 = shipFactory.makeSubmarine(new Placement(new Coordinate(2, 0), 'h'));
        Ship<Character> s2 = shipFactory.makeSubmarine(new Placement(new Coordinate(2, 1), 'h'));

        NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
        assertEquals(false, checker.checkPlacement(s1, b_10by20));
        assertEquals(true, checker.checkPlacement(s2, b_10by20));
    }

    @Test
    void test_InBoundsRule_and_NoCollisionRule() {
        Board<Character> b_10by20 = new BattleShipBoard<Character>(10, 20);
        AbstractShipFactory<Character> shipFactory = new V1ShipFactory();
        Ship<Character> c1 = shipFactory.makeCarrier(new Placement(new Coordinate(0, 0), 'h'));
        b_10by20.tryAddShip(c1);

        Ship<Character> s1 = shipFactory.makeSubmarine(new Placement(new Coordinate(-1, 0), 'v'));
        Ship<Character> s2 = shipFactory.makeSubmarine(new Placement(new Coordinate(0, 0), 'v'));
        Ship<Character> s3 = shipFactory.makeSubmarine(new Placement(new Coordinate(1, 0), 'v'));

        NoCollisionRuleChecker<Character> checker_0 = new NoCollisionRuleChecker<>(null);
        InBoundsRuleChecker<Character> checker_1 = new InBoundsRuleChecker<>(checker_0);

        assertEquals(false, checker_1.checkPlacement(s1, b_10by20));
        assertEquals(false, checker_1.checkPlacement(s2, b_10by20));
        assertEquals(true, checker_1.checkPlacement(s3, b_10by20));
    }
}
