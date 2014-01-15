/**
*
*
*
*@author Derrick Lee
*
**/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Board{
    public final static String WATER = "-";
    public final static String MISS = "O";
    public final static String HIT = "X";
    private final static String POSSIBLE_INPUTS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private Water water = new Water(WATER, WATER);
    private Crater wCrater = new Crater(WATER, MISS);
    private Ship[] ships;
    
    private Cell board[][];
    private int size;
    
    public final static int MIN_BOARD_SIZE = 5;
    public final static int MAX_BOARD_SIZE = 26;
    private int shotsFired = 0;
    private int shotsHit = 0;
    
    public Board(int size, String configFile) throws BException{
        this.size = size;
        board = new Cell[size][size];
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                set(i,j,water);
            }
        }
        
        //checks to see if valid configFile
        if(configFile == null){
            System.err.println("Usage: Battleship N config-file\nUsage: java Battleship N config-file");
        
        //create the ships based on information given from configFile
        }else{
            
            try{
                Scanner lines = new Scanner(new FileInputStream(configFile));
				Scanner words = new Scanner (lines.nextLine());
                
                int numShips = words.nextInt();
				if (numShips > 0) {
					ships = new Ship [numShips];
				} else {
					throw new BException("No ships given in file " + configFile + ".");
				}
                
                //check to make sure there are the correct number of ships
                for (int i=0; i<numShips; i++) {
					if (!lines.hasNext()) {
						throw new BException(
								"Incorrect number of ships in file " + configFile + ".");
					}
					
                    //figure out the placement of the ship
					words = new Scanner (lines.nextLine());
					String startRowS = (words.next()).toUpperCase();
					String startColS = (words.next()).toUpperCase();
					String endRowS = (words.next()).toUpperCase();
                    String endColS = (words.next()).toUpperCase();
                    
                    int startRowI = POSSIBLE_INPUTS.indexOf(startRowS);
                    int startColI = POSSIBLE_INPUTS.indexOf(startColS);
                    int endRowI = POSSIBLE_INPUTS.indexOf(endRowS);
                    int endColI = POSSIBLE_INPUTS.indexOf(endColS);
                    
                    int dir = Ship.NORTH;
                    
                    if(startRowI>endRowI){
                        dir = Ship.NORTH;
                    }else if(startRowI<endRowI){
                        dir = Ship.SOUTH;
                    }else if(startColI>endColI){
                        dir = Ship.WEST;
                    }else if(startColI<endColI){
                        dir = Ship.EAST;
                    }
                        
                    int health = Math.abs(startRowI - endRowI + startColI - endColI)+1;
					
					if(words.hasNext()) {
						throw new BException(
								"File " + configFile + " has corrupted contents.");
					}
                    
                    if(!validCoord(startRowI, startColI)){
                        throw new BException("Overlapping or out-of-bound ships in file " + configFile + ".");
                    }
                    
                    Ship.Model model;
                    if(POSSIBLE_INPUTS.charAt(i)=='A'){
                        model = Ship.Model.A;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='B'){
                        model = Ship.Model.B;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='C'){
                        model = Ship.Model.C;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='D'){
                        model = Ship.Model.D;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='E'){
                        model = Ship.Model.E;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='F'){
                        model = Ship.Model.F;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='G'){
                        model = Ship.Model.G;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='H'){
                        model = Ship.Model.H;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='I'){
                        model = Ship.Model.I;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='J'){
                        model = Ship.Model.J;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='K'){
                        model = Ship.Model.K;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='L'){
                        model = Ship.Model.L;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='M'){
                        model = Ship.Model.M;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='N'){
                        model = Ship.Model.N;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='O'){
                        model = Ship.Model.O;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='P'){
                        model = Ship.Model.P;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='Q'){
                        model = Ship.Model.Q;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='R'){
                        model = Ship.Model.R;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='S'){
                        model = Ship.Model.S;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='T'){
                        model = Ship.Model.T;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='U'){
                        model = Ship.Model.U;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='V'){
                        model = Ship.Model.V;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='W'){
                        model = Ship.Model.W;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='X'){
                        model = Ship.Model.Y;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='Y'){
                        model = Ship.Model.X;
                    }else if(POSSIBLE_INPUTS.charAt(i)=='Z'){
                        model = Ship.Model.Z;
                    }else{
                        throw new BException("Illegal number of ships in file " + configFile + ".");
                    }
                    
                    //create and set ship as long as nothing conflicts
                    Ship ship = new Ship(model, health, dir);
                    
                    if(validShipPosition(ship, startRowI, startColI, dir)){
                        ships[i]=ship;
                        setShip(ship, startRowI, startColI);
                    }else{
                        throw new BException("Overlapping or out-of-bounds ships in file " + configFile + ".");
                    }
                }
                
                if (lines.hasNextLine()) {
					throw new BException(
							"Incorrect number of ships in file " + configFile + ".");
				}
            }
            catch(FileNotFoundException e){
                throw new BException("Cannot open file " + configFile + ".");
            }
            catch(NoSuchElementException e){
                throw new BException("File " + configFile + " has corrupted contents.");
            }
            catch(IOException e){
                throw new BException("File " + configFile + " has corrupted contents.");
            }
        }
    }
        
    public int getSize(){
        return size;
    }
        
    public Cell getPiece(int x, int y){
        if(isValid(x,y)){
            return board[x][y];
        }else return null;
    }
        
    public int getShotsFired(){
        return shotsFired;
    }
        
    public int getShotsHit(){
        return shotsHit;
    }
        
    public int getShipsDestroyed(){
        int destroyed = 0;
            
        for(int i=0; i<ships.length; i++){
            if(ships[i].getSunk()){
                destroyed++;
            }
        }
        return destroyed;
    }
        
    public int getShips(){
        return ships.length;
    }
          
    public void set(int x, int y, Cell piece){
        board[x][y]=piece;
    }
    
    public boolean isValid(int x, int y) {
		return x>=0 && x<size && y>=0 && y<size;
	}
        
    public boolean checkWin(){
        return getShips()==getShipsDestroyed();
    }
        
    public void display(boolean cheatMode){
        System.out.print("   ");
        for(int i=0; i<size; i++){
            System.out.print(POSSIBLE_INPUTS.charAt(i) + "  ");
        }
        System.out.println();
         
        for(int j=0; j<size; j++){
            System.out.print(POSSIBLE_INPUTS.charAt(j));
            for(int i=0; i<size; i++){
                Cell piece = getPiece(j,i);
                System.out.printf("%3s",piece.getValue(cheatMode));
            }
            System.out.println();
        }
    }

        
    public boolean fire(int x, int y){
        if(!validCoord(x,y)||getPiece(x,y).wasHit()){
            return false;
        }
        
        shotsFired++;
        
        if(getPiece(x,y).fireShot()){
            String shipVal = getPiece(x,y).getValue(true);
            shotsHit++;
                
            set(x,y, new Crater(shipVal, HIT));
        }else{
            set(x,y, wCrater);
        }
            
        return true;
    }
    
    private void setShip(Ship ship, int startRow, int startCol){
        for (int i=0; i<ship.getHealth(); i++) {
			if (ship.getDirection() == Ship.NORTH) set(startRow-i, startCol, ship);
			else if (ship.getDirection() == Ship.SOUTH) set(startRow+i, startCol, ship);
			else if (ship.getDirection() == Ship.EAST) set(startRow, startCol+i, ship);
			else set(startRow, startCol-i, ship);
		}	
    }
    
    
    
    public boolean validCoord(int x, int y){
		return (x>=0 && x<size && y>=0 && y<size);
    }
    
    private boolean validShipPosition(Ship ship, int row, int col, int dir) {
        int rowInc = 0;
        int colInc = 0;
		
        if (dir == Ship.NORTH) rowInc = -1;
        else if (dir == Ship.SOUTH) rowInc = 1;
        else if (dir == Ship.EAST) colInc = 1;
		else if (dir == Ship.WEST) colInc = -1;
		
        int rowBound = row + (rowInc * (ship.getHealth() - 1));
        int colBound = col + (colInc * (ship.getHealth() - 1));
        if (rowBound < 0 || rowBound > getSize() - 1) 
            return false;
        else if (colBound < 0 || colBound > getSize() - 1)
            return false;
		
		// make sure all cells are empty
        int count=0;
        while (count<ship.getHealth() && !getPiece(row, col).checkSpace()) {
            row += rowInc;
            col += colInc;
            count++;
        } 
		//if nothing conflicts, then count should equal health
        return count == ship.getHealth();
    }
}