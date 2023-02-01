package edu.duke.xh123.battleship;

/**
 * This is a placement rule checker for out of bounds checking.
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
    /**
     * Constructs a placement in bounds rule checker followed by next placenment
     * rule checker.
     * 
     * @param next is the next placement rule checker in the chain of responsibility
     *             pattern
     */
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        Iterable<Coordinate> c_set = theShip.getCoordinates();
        for (Coordinate c : c_set) {
            // if (!(c.getRow() >= 0 && c.getRow() < theBoard.getHeight() &&
            // c.getColumn() >= 0 && c.getColumn() < theBoard.getWidth())) {
            // return false;
            // }
            if (c.getRow() < 0) {
                return "That placement is invalid: the ship goes off the top of the board.";
            }
            if (c.getRow() >= theBoard.getHeight()) {
                return "That placement is invalid: the ship goes off the bottom of the board.";
            }
            if (c.getColumn() < 0) {
                return "That placement is invalid: the ship goes off the left of the board.";
            }
            if (c.getColumn() >= theBoard.getWidth()) {
                return "That placement is invalid: the ship goes off the right of the board.";
            }
        }
        return null;
    }

}
