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
            remove.add(where.getCoordinate());
            remove.add(new Coordinate(where.getCoordinate().getRow(), where.getCoordinate().getColumn() + 2));
            return new NonRectangleShip<Character>("Battleship", where.getCoordinate(), 3, 2, remove, 'b', '*');
        } else if (where.getOrientation() == 'D') {
            remove.add(new Coordinate(where.getCoordinate().getRow() + 1, where.getCoordinate().getColumn()));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 1, where.getCoordinate().getColumn() + 2));
            return new NonRectangleShip<Character>("Battleship", where.getCoordinate(), 3, 2, remove, 'b', '*');
        } else if (where.getOrientation() == 'L') {
            remove.add(where.getCoordinate());
            remove.add(new Coordinate(where.getCoordinate().getRow() + 2, where.getCoordinate().getColumn()));
            return new NonRectangleShip<Character>("Battleship", where.getCoordinate(), 2, 3, remove, 'b', '*');
        } else if (where.getOrientation() == 'R') {
            remove.add(new Coordinate(where.getCoordinate().getRow(), where.getCoordinate().getColumn() + 1));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 2, where.getCoordinate().getColumn() + 1));
            return new NonRectangleShip<Character>("Battleship", where.getCoordinate(), 2, 3, remove, 'b', '*');
        } else {
            throw new IllegalArgumentException(
                    "Placement's orientation should be Up, Down, Left or Right, but " + where.getOrientation());
        }
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        HashSet<Coordinate> remove = new HashSet<>();
        if (where.getOrientation() == 'U') {
            remove.add(new Coordinate(where.getCoordinate().getRow(), where.getCoordinate().getColumn() + 1));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 1, where.getCoordinate().getColumn() + 1));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 4, where.getCoordinate().getColumn()));
            return new NonRectangleShip<Character>("Carrier", where.getCoordinate(), 2, 5, remove, 'c', '*');
        } else if (where.getOrientation() == 'D') {
            remove.add(new Coordinate(where.getCoordinate().getRow(), where.getCoordinate().getColumn() + 1));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 3, where.getCoordinate().getColumn()));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 4, where.getCoordinate().getColumn()));
            return new NonRectangleShip<Character>("Carrier", where.getCoordinate(), 2, 5, remove, 'c', '*');
        } else if (where.getOrientation() == 'L') {
            remove.add(where.getCoordinate());
            remove.add(new Coordinate(where.getCoordinate().getRow(), where.getCoordinate().getColumn() + 1));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 2, where.getCoordinate().getColumn() + 4));
            return new NonRectangleShip<Character>("Carrier", where.getCoordinate(), 5, 2, remove, 'c', '*');
        } else if (where.getOrientation() == 'R') {
            remove.add(where.getCoordinate());
            remove.add(new Coordinate(where.getCoordinate().getRow() + 1, where.getCoordinate().getColumn() + 3));
            remove.add(new Coordinate(where.getCoordinate().getRow() + 1, where.getCoordinate().getColumn() + 4));
            return new NonRectangleShip<Character>("Carrier", where.getCoordinate(), 5, 2, remove, 'c', '*');
        } else {
            throw new IllegalArgumentException(
                    "Placement's orientation should be Up, Down, Left or Right, but " + where.getOrientation());
        }
    }
}
