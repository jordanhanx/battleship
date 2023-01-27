package edu.duke.xh123.battleship;

/**
 * This class handles any type of display information of a simple ship. It
 * implements ShipDisplayInfo interface.
 */
public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
    private T myData;
    private T onHit;

    /**
     * Constructs a display information of the simple ship.
     * 
     * @param myData is the display info of the ship's unhit part.
     * @param onHit  is the display info of the ship's hit part.
     */
    public SimpleShipDisplayInfo(T myData, T onHit) {
        this.myData = myData;
        this.onHit = onHit;
    }

    @Override
    public T getInfo(Coordinate where, boolean hit) {
        return hit ? onHit : myData;
    }
}
