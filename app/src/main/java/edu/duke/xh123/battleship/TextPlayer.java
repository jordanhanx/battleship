package edu.duke.xh123.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This is an type of text player
 */
public class TextPlayer {
    private final String name;
    private final Board<Character> theBoard;
    private final BoardTextView view;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final AbstractShipFactory<Character> shipFactory;
    protected final ArrayList<String> shipsToPlace;
    private final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

    /**
     * Constructs the object of TextPlayer type.
     * 
     * @param name        is the player's name
     * @param theBoard    is the specified text Board for our BattleShip Game.
     * @param inputSource is where to read input String.
     * @param out         is where to print Board's text view.
     * @param shipFactory is a Factory for building our version 1 BattleShips.
     */
    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
            V1ShipFactory shipFactory) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
        this.shipFactory = shipFactory;
        this.shipsToPlace = new ArrayList<String>();
        this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
        setupShipCreationList();
        setupShipCreationMap();
    }

    /**
     * Setup the ArrayList of the ship names that we want to work from.
     */
    protected void setupShipCreationList() {
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    /**
     * Setup the map from ship name to the lambda to create it.
     */
    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
        shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
        shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
        shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    }

    /**
     * This makes the program read input String from inputSource.
     * 
     * @param prompt is the message to prompt user input some String.
     * @return the Placement information.
     * @throws IOException if we have IO errors when reading or printing.
     */
    public Placement readPlacement(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new IllegalArgumentException("inputReader.readLine return null");
        }
        return new Placement(s);
    }

    /**
     * This makes the program read input String from inputSource, create a new ship
     * and try to add it into the Board, then print the current view of the Board.
     * 
     * @param shipName is the name of ship that will be built.
     * @param createFn is the lambda function to create the ship.
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        try {
            Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
            Ship<Character> s = createFn.apply(p);
            String msg = theBoard.tryAddShip(s);
            if (msg == null) {
                out.print(view.displayMyOwnBoard());
            } else {
                out.println(msg);
            }
        } catch (IllegalArgumentException e) {
            out.println("That placement is invalid: it does not have the correct format.");
        }
    }

    /**
     * This makes the player put all ships on the board.
     * 
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    public void doPlacementPhase() throws IOException {
        out.print(view.displayMyOwnBoard());
        String msg = "Player " + name + ": you are going to place the following ships (which are all\n"
                + "rectangular). For each ship, type the coordinate of the upper left\n"
                + "side of the ship, followed by either H (for horizontal) or V (for\n"
                + "vertical).  For example M4H would place a ship horizontally starting\n"
                + "at M4 and going to the right.  You have\n" + "\n"
                + "2 \"Submarines\" ships that are 1x2\n"
                + "3 \"Destroyers\" that are 1x3\n"
                + "3 \"Battleships\" that are 1x4\n"
                + "2 \"Carriers\" that are 1x6\n";
        out.print(msg);
        for (String shipName : shipsToPlace) {
            doOnePlacement(shipName, shipCreationFns.get(shipName));
        }
    }
}
