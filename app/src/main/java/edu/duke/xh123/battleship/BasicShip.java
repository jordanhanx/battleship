package edu.duke.xh123.battleship;

/**
 * This is an specific type of Ship occupying only 1 grid in our Battleship
 * game.
 */
public class BasicShip implements Ship<Character> {
    private final Coordinate myLocation;

    /**
     * Constructs a BasicShip with the specified coordinate
     * 
     * @param c is the coordinate of the ship.
     */
    public BasicShip(Coordinate c) {
        this.myLocation = c;
    }

    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return where.equals(myLocation);
    }

    @Override
    public boolean isSunk() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void recordHitAt(Coordinate where) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Character getDisplayInfoAt(Coordinate where) {
        return 's';
    }

}
