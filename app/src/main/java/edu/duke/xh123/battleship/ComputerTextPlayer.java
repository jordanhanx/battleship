package edu.duke.xh123.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.function.Function;

public class ComputerTextPlayer extends TextPlayer {
    private Random rand;

    /**
     * Constructs the object of TextPlayer type.
     * 
     * @param name        is the player's name
     * @param theBoard    is the specified text Board for our BattleShip Game.
     * @param inputSource is where to read input String.
     * @param out         is where to print Board's text view.
     * @param shipFactory is a Factory for building our version 1 BattleShips.
     */
    public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
            AbstractShipFactory<Character> shipFactory) {
        super(name, theBoard, inputSource, out, shipFactory);
        rand = new Random(0); // set random seed = 0
    }

    @Override
    public Coordinate readCoordinate(String prompt) {
        return new Coordinate(rand.nextInt(theBoard.getHeight()), rand.nextInt(theBoard.getWidth()));
    }

    @Override
    public Placement readPlacement(String prompt) {
        Coordinate c = new Coordinate(rand.nextInt(theBoard.getHeight()), rand.nextInt(theBoard.getWidth()));
        String alphabet = "HVURDL";
        return new Placement(c, alphabet.charAt(rand.nextInt(alphabet.length())));
    }

    @Override
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) {
        while (true) {
            try {
                Placement p = readPlacement("(This line should not be printed)" + "Player " + name
                        + " where do you want to place a " + shipName + "?");
                Ship<Character> s = createFn.apply(p);
                String msg = theBoard.tryAddShip(s);
                if (msg == null) {
                    return; // add ship successfully
                }
            } catch (IllegalArgumentException e) {
            }
        }
    }

    @Override
    public void doPlacementPhase() {
        for (String shipName : shipsToPlace) {
            doOnePlacement(shipName, shipCreationFns.get(shipName));
        }
    }

    @Override
    public void openFire(Board<Character> enemyBoard) throws IOException {
        Coordinate c = readCoordinate(
                "(This line should not be printed)" + "Player " + name + " where do you want to fire at?");
        Ship<Character> s = enemyBoard.fireAt(c);
        if (s != null) {
            out.println("Player " + name + " hit your " + s.getName() + " at " + c.toString() + "!");
        } else {
            out.println("Player " + name + " missed!");
        }
    }

    @Override
    public void moveShip() {
        Coordinate c = readCoordinate("(This line should not be printed)" + "Player " + name
                + " which ship do you want to move? (input one Coordinate occupied by the ship)");
        Ship<Character> ship = theBoard.whichShipIsAt(c);
        if (ship == null) {
            throw new IllegalArgumentException(
                    "(This line should not be printed)" + "Oops, there is no ship at " + c.toString());
        }
        Placement destination = readPlacement("(This line should not be printed)" + "Player " + name
                + " where do you want to place the " + ship.getName() + "?");
        theBoard.tryMoveShip(ship, destination);
    }

    @Override
    public void takeAction(Board<Character> enemyBoard) throws IOException {
        String alphabet = "FM";
        String s = "" + alphabet.charAt(rand.nextInt(alphabet.length()));
        if (s.equalsIgnoreCase("M") && moveShipToken > 0) {
            moveShip();
            moveShipToken--;
            out.println("Player " + name + " used a special action");
        } else {
            openFire(enemyBoard);
        }
    }

    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
        while (true) {
            try {
                takeAction(enemyBoard);
                return; // play one turn successfully
            } catch (IllegalArgumentException e) {
            }
        }
    }
}
