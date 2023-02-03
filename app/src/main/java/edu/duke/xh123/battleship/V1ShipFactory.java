package edu.duke.xh123.battleship;

/**
 * This is the Abstract Factory for building our version 1 BattleShips
 */
public class V1ShipFactory implements AbstractShipFactory<Character> {

    /**
     * Makes a rectangle ship with specified parameters.
     * 
     * @param where  is the coordinate of rectangle's upper left corner.
     * @param w      is the ship's width.
     * @param h      is the ship's length.
     * @param letter is the symbol to display this ship.
     * @param name   is the name of this ship.
     */
    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name) {
        if (where.getOrientation() == 'V') {
            return new RectangleShip<Character>(name, where.getCoordinate(), w, h, letter, '*');
        } else if (where.getOrientation() == 'H') {
            return new RectangleShip<Character>(name, where.getCoordinate(), h, w, letter, '*');
        } else {
            throw new IllegalArgumentException(
                    "Placement's orientation should be veritcal or horizontal, but " + where.getOrientation());
        }
    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return createShip(where, 1, 4, 'b', "Battleship");
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return createShip(where, 1, 6, 'c', "Carrier");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

}
