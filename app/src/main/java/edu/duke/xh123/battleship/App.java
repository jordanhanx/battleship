/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.xh123.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;

public class App {
    private final TextPlayer player1;
    private final TextPlayer player2;

    /**
     * Constructs the object of App type.
     * 
     * @param width       is the width of the Board
     * @param height      is the height of the Board
     * @param inputSource is where to read input String.
     * @param out         is where to print Board's text view.
     */
    public App(int width, int height, Reader inputSource, PrintStream out) throws IOException {
        Board<Character> b1 = new BattleShipBoard<>(width, height, 'X');
        Board<Character> b2 = new BattleShipBoard<>(width, height, 'X');
        BufferedReader input = new BufferedReader(inputSource);
        // V1ShipFactory factory = new V1ShipFactory();
        V2ShipFactory factory = new V2ShipFactory();

        if (isComputerPlayer("A", input, out)) {
            this.player1 = new ComputerTextPlayer("A (Computer)", b1, input, out, factory);
        } else {
            this.player1 = new TextPlayer("A", b1, input, out, factory);
        }
        if (isComputerPlayer("B", input, out)) {
            this.player2 = new ComputerTextPlayer("B (Computer)", b2, input, out, factory);
        } else {
            this.player2 = new TextPlayer("B", b2, input, out, factory);
        }
    }

    /**
     * Read the setting for whether the play is a computer player.
     * 
     * @param name        is the player's name.
     * @param inputSource is where to read input String.
     * @param out         is where to print.
     * @return whether the play is a computer player.
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    static boolean isComputerPlayer(String name, BufferedReader inputReader, PrintStream out) throws IOException {
        while (true) {
            out.println("Is player " + name + " a computer player? (y/N)");
            String s = inputReader.readLine();
            if (s.equalsIgnoreCase("y")) {
                return true;
            } else if (s.equalsIgnoreCase("N")) {
                return false;
            }
            out.println("Invalid choice!");
        }
    }

    /**
     * This makes the program read input String from inputSource, create a new ship
     * and try to add it into the Board, then print the current view of the Board.
     * 
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    public void doPlacementPhase() throws IOException {
        player1.doPlacementPhase();
        player2.doPlacementPhase();
    }

    /**
     * Play attacking phase of the game, until one player is lost.
     * 
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    public void doAttackingPhase() throws IOException {
        TextPlayer current = player1;
        TextPlayer next = player2;
        while (true) {
            current.playOneTurn(next.theBoard, next.view, next.name);
            if (next.isLost()) {
                current.announceVictory();
                return;
            } else {
                TextPlayer temp = current;
                current = next;
                next = temp;
            }
        }
    }

    /**
     * The entry point of the entire program.
     * 
     * @throws IOException If We Have Io Errors When Reading Or Printing.
     */
    public static void main(String[] args) throws IOException {
        App app = new App(10, 20, new InputStreamReader(System.in), System.out);
        app.doPlacementPhase();
        app.doAttackingPhase();
        return;
    }
}
