package edu.duke.xh123.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

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

    /**
     * Constructs the object of TextPlayer type.
     * 
     * @param name        is the player's name
     * @param theBoard    is the specified text Board for our BattleShip Game.
     * @param inputSource is where to read input String.
     * @param out         is where to print Board's text view.
     */
    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out,
            V1ShipFactory shipFactory) {
        this.name = name;
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputSource;
        this.out = out;
        this.shipFactory = shipFactory;
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
        return new Placement(s);
    }

    /**
     * This makes the program read input String from inputSource, create a new ship
     * and try to add it into the Board, then print the current view of the Board.
     * 
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    public void doOnePlacement() throws IOException {
        Placement p = readPlacement("Player " + name + " where do you want to place a Destroyer?");
        Ship<Character> s = shipFactory.makeDestroyer(p);
        theBoard.tryAddShip(s);
        out.print(view.displayMyOwnBoard());
    }

    /**
     * This makes the player put 1 ship on the board.
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
                + "2 \"Destroyers\" that are 1x3\n"
                + "3 \"Battleships\" that are 1x4\n"
                + "2 \"Carriers\" that are 1x6\n";
        out.print(msg);
        doOnePlacement();
    }
}
