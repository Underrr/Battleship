/**
*
*
*
*@author Derrick Lee
*
**/

import java.util.*;
import java.io.*;

public class Battleship{
    private final static String INVALID_ARGS = "Usage: Battleship N config-file\nUsage: java Battleship N config-file";
    private final static String SIZE_TOO_SMALL = "Board must be at least 5 by 5.";
    private final static String SIZE_TOO_LARGE = "Board must be at most 26 by 26.";
    private final static String INVALID_COMMAND = "Illegal command.";
    private final static String INVALID_INPUT = "Cannot open file ";
    private final static String INVALID_VIEW_COMMAND = "Can only view game board or ships.";
    private final static String SAME_COORDINATES = "Coordinates previously fired upon.";
    private final static String INVALID_COORDINATES = "Illegal coordinates.";
    private final static String INVALID_SHIP = "Overlapping or out-of-bounds ships in file ";
	
    public final static String HIT = "Hit!";
    public final static String MISS = "Miss!";
    public final static String SUNK = "Sunk!";
    public final static String WIN = "You win!";
    
    private final static String EMPTY = "";
    private final static String VIEW = "view";
    private final static String BOARD = "board";
    private final static String SHIPS = "ships";
    private final static String FIRE = "fire";
	private final static String STATS = "stats";
	private final static String HELP = "help";
	private final static String QUIT = "quit";
    
    private final static String POSSIBLE_INPUTS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private Board board;
    private final static String inputPrompt = "> ";
    
    public static void main(String[] args) {
		
		try {
			if (!(args.length==2)) {
				throw new BException(INVALID_ARGS);
			}
			
			int size = Integer.parseInt(args[0]);
			if (size < Board.MIN_BOARD_SIZE) {
				throw new BException(SIZE_TOO_SMALL);
			} else if (size > Board.MAX_BOARD_SIZE) {
				throw new BException(SIZE_TOO_LARGE);
			}
			
			Battleship battleShip;
            String configFile;
            
            if(args.length==1){
                configFile=null;
            }else{
                configFile=args[1];
            }
            
			battleShip = new Battleship(size, configFile);
			
			battleShip.runthrough();
		}
		catch (BException e) {
			System.err.println(e.getMessage());
		}
		catch (NumberFormatException e) {
			System.err.println(INVALID_ARGS);
		} 
	}
    
    public Battleship(int size) throws BException{
        this(size, null);
    }
    
    public Battleship(int size, String configFile) throws BException{
        board = new Board(size, configFile);
    }
    
    private void showHelp(){
        System.out.println("Possible commands:");
        System.out.println("board - displays the user's board");
        System.out.println("ships - displays the placement of the ships");
        System.out.println("fire r c - fires a missile at the cell at [r,c]");
        System.out.println("stats - prints out the game statistics");
        System.out.println("quit - exits the game");
    }
    
    private void showStats(){
        int shotsFired = board.getShotsFired();
        
        System.out.println("Number of missiles fired: " + board.getShotsFired());
        System.out.println("Hit ratio: " + (double)board.getShotsHit()/board.getShotsFired()*100 + "%");
        System.out.println("Number of ships sunk: " + board.getShipsDestroyed());
    }
    
    private void runthrough(){
    
        board.display(false);
    
        Scanner lineScanner = new Scanner(System.in);
        String input;
    
        do{
            input = EMPTY;
            System.out.print(inputPrompt);
            Scanner wordScanner = new Scanner(lineScanner.nextLine());
            if(wordScanner.hasNext()){
                input = wordScanner.next();
                
                if(input.equals(BOARD)){
                    board.display(false);
                } else if(input.equals(SHIPS)){
                    board.display(true);
                } else if (input.equals(FIRE)) {//change to accept strings
                    String r=" ", c=" ";
                    int rI=0, cI=0;
                    if (wordScanner.hasNext()){
                        r = (wordScanner.next()).toUpperCase();
                        rI=POSSIBLE_INPUTS.indexOf(r);
                    }else {
                        System.out.println(INVALID_COMMAND);
                        continue;
                    }
                    if (wordScanner.hasNext()){
                        c = (wordScanner.next()).toUpperCase();
                        cI=POSSIBLE_INPUTS.indexOf(c);
                    }
                    else {
                        System.out.println(INVALID_COMMAND);
                        continue;
                    }
                    if (wordScanner.hasNext()) {
                        System.out.println(INVALID_ARGS);
                        continue;
                    }
                    if (!board.isValid(rI, cI)) System.out.println(INVALID_COORDINATES);
                    else {
                        if (board.fire(rI, cI)) {
                            if (board.checkWin()) {
                                System.out.println(WIN);
                                board.display(false);
                                showStats();
                                return;
                            }
                            board.display(false);
                        } else {
                            System.out.println(SAME_COORDINATES);
                        }
                    }   
                } else if (input.equals(STATS)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(INVALID_ARGS);
                    } else {
                        showStats();
                    }
                } else if (input.equals(HELP)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(INVALID_ARGS);
                    } else {
                        showHelp();
                    }
                } else if (input.equals(QUIT)) {
                    if (wordScanner.hasNext()) {
                        System.out.println(INVALID_ARGS);
                        input = EMPTY;
                    }
                } else if (!input.equals(EMPTY)){
                    System.out.println(INVALID_COMMAND);
                }
            }
        } while (!input.equals(QUIT));
    }
}