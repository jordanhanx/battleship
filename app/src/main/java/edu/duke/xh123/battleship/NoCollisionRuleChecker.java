package edu.duke.xh123.battleship;

/**
 * This is a placement rule checker for collision checking.
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    /**
     * Constructs a placement in collision rule checker followed by next placenment
     * rule checker.
     * 
     * @param next is the next placement rule checker in the chain of responsibility
     *             pattern
     */
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        Iterable<Coordinate> c_set = theShip.getCoordinates();
        for (Coordinate c : c_set) {
            if (theBoard.whatIsAt(c) != null) {
                return "That placement is invalid: the ship overlaps another ship.";
            }
        }
        return null;
    }
}
