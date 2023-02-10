package edu.duke.xh123.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is an type of text player
 */
public class TextPlayer {
    final String name;
    final Board<Character> theBoard;
    final BoardTextView view;
    private final BufferedReader inputReader;
    private final PrintStream out;
    private final AbstractShipFactory<Character> shipFactory;
    protected final ArrayList<String> shipsToPlace;
    private final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
    protected int moveShipToken;
    protected int sonarScanToken;

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
            AbstractShipFactory<Character> shipFactory) {
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
        setupTokens();
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
     * Setup initial moveShipToken and sonarScanToken.
     */
    protected void setupTokens() {
        moveShipToken = 3;
        sonarScanToken = 3;
    }

    /**
     * This makes the program read input String from inputSource.
     * 
     * @param prompt is the message to prompt user input some String.
     * @return the Coordinate information.
     * @throws IOException if we have IO errors when reading or printing.
     */
    public Coordinate readCoordinate(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        if (s == null) {
            throw new EOFException("inputReader.readLine() return null, EOF!");
        }
        return new Coordinate(s);
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
            throw new EOFException("inputReader.readLine() return null");
        }
        return new Placement(s);
    }

    /**
     * This makes the program read input String from inputSource, create a new ship
     * and try to add it into the Board, then print the current view of the Board.
     * 
     * @param shipName is the name of ship that will be built.
     * @param createFn is the lambda function to create the ship.
     * @throws IOException if We Have Io Errors When Reading Or Printing.
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
        while (true) {
            try {
                Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
                Ship<Character> s = createFn.apply(p);
                String msg = theBoard.tryAddShip(s);
                if (msg == null) {
                    out.print(view.displayMyOwnBoard());
                    return; // add ship successfully
                } else {
                    out.println(msg);
                }
            } catch (IllegalArgumentException e) {
                out.println("That placement is invalid: it does not have the correct format.");
            }
        }
    }

    /**
     * This makes the player put all ships on the board.
     * 
     * @throws IOException if We Have Io Errors When Reading Or Printing.
     */
    public void doPlacementPhase() throws IOException {
        out.print(view.displayMyOwnBoard());
        StringBuilder msg = new StringBuilder("");
        msg.append("Player " + name + ": you are going to place the following ships (which are not all\n");
        msg.append("rectangular). For each ship, type the coordinate of the upper left\n");
        msg.append("side of the ship, followed by either H (for horizontal) or V (for\n");
        msg.append("vertical) for Submarines/Destroyers and one in U (for Up), D (for\n");
        msg.append("Down), L (for Left) and R (for Rigth) for Battleships/Carriers.\n");
        msg.append("For example M4H would place a ship horizontally starting\n");
        msg.append("at M4 and going to the right.  You have\n" + "\n");
        msg.append("2 \"Submarines\" ships that are 1x2\n");
        msg.append("3 \"Destroyers\" that are 1x3\n");
        msg.append("3 \"Battleships\" that that are now shaped as shown below\n");
        msg.append("      b      OR    b         bbb         b\n");
        msg.append("     bbb           bb   OR    b     OR  bb\n");
        msg.append("                   b                     b\n\n");
        msg.append("      Up         Right       Down      Left\n");
        msg.append("2 \"Carriers\" that are now shaped as shown below\n");
        msg.append("      c                       c             \n");
        msg.append("      c           cccc        cc         ccc\n");
        msg.append("      cc   OR    ccc      OR  cc   OR  cccc\n");
        msg.append("      cc                       c\n");
        msg.append("       c                       c\n\n");
        msg.append("      Up         Right       Down      Left\n");
        out.print(msg.toString());
        for (String shipName : shipsToPlace) {
            doOnePlacement(shipName, shipCreationFns.get(shipName));
        }
    }

    /**
     * Checks if this player is lost(all ships have sunk).
     * 
     * @return ture if all ships are sunk.
     */
    public boolean isLost() {
        return theBoard.shipsAreAllSunk();
    }

    /**
     * Announce the winner of the game
     */
    public void announceVictory() {
        out.println("Player " + name + " is the winner!");
    }

    /**
     * Fire at enemy's board
     * 
     * @param enemyBoard is the enmey's board.
     * @throws IOException if We Have Io Errors When Reading Or Printing.
     */
    public void openFire(Board<Character> enemyBoard) throws IOException {
        Coordinate c = readCoordinate("Player " + name + " where do you want to fire at?");
        Ship<Character> s = enemyBoard.fireAt(c);
        if (s != null) {
            out.println("You hit a " + s.getName() + "!");
        } else {
            out.println("You missed!");
        }
    }

    /**
     * Move a ship to new placement.
     * 
     * @throws IOException              if We Have Io Errors When Reading Or
     *                                  Printing.
     * @throws IllegalArgumentException when there is no ship at the given
     *                                  coordinate.
     */
    public void moveShip() throws IOException {
        Coordinate c = readCoordinate(
                "Player " + name + " which ship do you want to move? (input one Coordinate occupied by the ship)");
        Ship<Character> ship = theBoard.whichShipIsAt(c);
        if (ship == null) {
            throw new IllegalArgumentException("Oops, there is no ship at " + c.toString());
        }
        Placement destination = readPlacement(
                "Player " + name + " where do you want to place the " + ship.getName() + "?");
        theBoard.tryMoveShip(ship, destination);
    }

    /**
     * Scan enemy's board with sonar.
     * 
     * @param enemyBoard is the enmey's board.
     * @throws IOException if We Have Io Errors When Reading Or Printing.
     */
    public void scanSonar(Board<Character> enemyBoard) throws IOException {
        Coordinate c = readCoordinate(
                "Player " + name + " where do you want to scan with sonar?");
        Map<String, Integer> scanResult = enemyBoard.sonarScanAt(c);
        for (Map.Entry<String, Integer> pair : scanResult.entrySet()) {
            out.println(pair.getKey() + "s occupy " + pair.getValue() + " squares");
        }
    }

    /**
     * Prompt all available actions to player.
     */
    public void printAvailableActions() {
        if (moveShipToken > 0 || sonarScanToken > 0) {
            out.print("Possible actions for Player " + name + ":\n\n");
            out.println(" " + "F Fire at a square");
            if (moveShipToken > 0) {
                out.println(" " + "M Move a ship to another square (" + moveShipToken + " remaining)");
            }
            if (sonarScanToken > 0) {
                out.println(" " + "S Sonar scan (" + sonarScanToken + " remaining)");
            }
            out.print("\nPlayer " + name + ", what would you like to do?\n");
        }
    }

    /**
     * Take action based on player's input.
     * 
     * @param enemyBoard is the enmey's board.
     * @throws IOException              if We Have Io Errors When Reading Or
     *                                  Printing.
     * @throws IllegalArgumentException if player selects invalid action.
     */
    public void takeAction(Board<Character> enemyBoard) throws IOException {
        if (moveShipToken > 0 || sonarScanToken > 0) {
            String s = inputReader.readLine();
            if (s.equalsIgnoreCase("F")) {
                openFire(enemyBoard);
            } else if (s.equalsIgnoreCase("M") && moveShipToken > 0) {
                moveShip();
                moveShipToken--;
            } else if (s.equalsIgnoreCase("S") && sonarScanToken > 0) {
                scanSonar(enemyBoard);
                sonarScanToken--;
            } else {
                throw new IllegalArgumentException("Invalid action input:" + s + ", please try a again.");
            }
        } else {
            openFire(enemyBoard);
        }
    }

    /**
     * Plays one turn.
     * 
     * @param enemyBoard is the enmey's board.
     * @param enemyView  is the enemy's view.
     * @param enemyName  is the enemy's name.
     * @throws IOException if We Have Io Errors When Reading Or Printing.
     */
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
        out.println("Player " + name + "'s turn:");
        out.print(view.displayMyBoardWithEnemyNextToIt(enemyView, "Your ocean",
                "Player " + enemyName + "'s ocean"));
        while (true) {
            try {
                printAvailableActions();
                takeAction(enemyBoard);
                return; // play one turn successfully
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
            }
        }
    }
}
