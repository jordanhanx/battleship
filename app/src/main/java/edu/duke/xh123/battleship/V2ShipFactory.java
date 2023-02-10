package edu.duke.xh123.battleship;

import java.util.HashSet;

/**
 * This is the Abstract Factory for building our version 2 BattleShips
 */
public class V2ShipFactory extends V1ShipFactory {

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        HashSet<Coordinate> remove = new HashSet<>();
        if (where.getOrientation() == 'U') {
            remove.add(new Coordinate(0, 0));
            remove.add(new Coordinate(0, 2));
            return new NonRectangleShip<Character>("Battleship", where, 3, 2, remove, 'b', '*');
        } else if (where.getOrientation() == 'D') {
            remove.add(new Coordinate(1, 0));
            remove.add(new Coordinate(1, 2));
            return new NonRectangleShip<Character>("Battleship", where, 3, 2, remove, 'b', '*');
        } else if (where.getOrientation() == 'L') {
            remove.add(new Coordinate(0, 0));
            remove.add(new Coordinate(2, 0));
            return new NonRectangleShip<Character>("Battleship", where, 2, 3, remove, 'b', '*');
        } else if (where.getOrientation() == 'R') {
            remove.add(new Coordinate(0, 1));
            remove.add(new Coordinate(2, 1));
            return new NonRectangleShip<Character>("Battleship", where, 2, 3, remove, 'b', '*');
        } else {
            throw new IllegalArgumentException(
                    "Placement's orientation should be Up, Down, Left or Right, but " + where.getOrientation());
        }
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        HashSet<Coordinate> remove = new HashSet<>();
        if (where.getOrientation() == 'U') {
            remove.add(new Coordinate(0, 1));
            remove.add(new Coordinate(1, 1));
            remove.add(new Coordinate(4, 0));
            return new NonRectangleShip<Character>("Carrier", where, 2, 5, remove, 'c', '*');
        } else if (where.getOrientation() == 'D') {
            remove.add(new Coordinate(0, 1));
            remove.add(new Coordinate(3, 0));
            remove.add(new Coordinate(4, 0));
            return new NonRectangleShip<Character>("Carrier", where, 2, 5, remove, 'c', '*');
        } else if (where.getOrientation() == 'L') {
            remove.add(new Coordinate(0, 0));
            remove.add(new Coordinate(0, 1));
            remove.add(new Coordinate(1, 4));
            return new NonRectangleShip<Character>("Carrier", where, 5, 2, remove, 'c', '*');
        } else if (where.getOrientation() == 'R') {
            remove.add(new Coordinate(0, 0));
            remove.add(new Coordinate(1, 3));
            remove.add(new Coordinate(1, 4));
            return new NonRectangleShip<Character>("Carrier", where, 5, 2, remove, 'c', '*');
        } else {
            throw new IllegalArgumentException(
                    "Placement's orientation should be Up, Down, Left or Right, but " + where.getOrientation());
        }
    }
}
