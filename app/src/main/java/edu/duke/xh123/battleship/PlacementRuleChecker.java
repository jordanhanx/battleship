package edu.duke.xh123.battleship;

/**
 * This is a generic abstract class for placement rules checking on the Chain of
 * Responsibility pattern
 */
public abstract class PlacementRuleChecker<T> {
    private final PlacementRuleChecker<T> next;

    /**
     * Constructs a placement rule checker followed by next placenment rule checker.
     * 
     * @param next is the next placement rule checker in the chain of responsibility
     *             pattern
     */
    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    /**
     * Checks if the ship's placement on the board complies with this rule.
     * 
     * @param theShip  is the checked ship.
     * @param theBoard is the checked borad.
     * @return true if the ship's placement on the board complies with this rule,
     *         otherwise false.
     */
    protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

    /**
     * Checks all of the rules on the chain together.
     * 
     * @param theShip  is the checked ship.
     * @param theBoard is the checked borad.
     * @return true if the ship's placement on the board complies with all of the
     *         rules on the Chain, otherwise false.
     */
    public boolean checkPlacement(Ship<T> theShip, Board<T> theBoard) {
        // if we fail our own rule: stop the placement is not legal
        if (!checkMyRule(theShip, theBoard)) {
            return false;
        }
        // other wise, ask the rest of the chain.
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        // if there are no more rules, then the placement is legal
        return true;
    }

}
