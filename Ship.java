/**
*
*
*
*@author Derrick Lee
*
**/

public class Ship extends Cell{
    //ship data
    private int health;
    private boolean sunk = false;
    private int dir;
    
    public static int NORTH = 0;
    public static int SOUTH = 1;
    public static int EAST = 2;
    public static int WEST = 3;
    
    
    public enum Model{
        //ship model based on which number it is (first ship = A, second = 2, etc)
        A ("A", Board.WATER),B ("B", Board.WATER),C ("C", Board.WATER),
        D ("D", Board.WATER),E ("E", Board.WATER),F ("F", Board.WATER),
        G ("G", Board.WATER),H ("H", Board.WATER),I ("I", Board.WATER),
        J ("J", Board.WATER),K ("K", Board.WATER),L ("L", Board.WATER),
        M ("M", Board.WATER),N ("N", Board.WATER),O ("O", Board.WATER),
        P ("P", Board.WATER),Q ("Q", Board.WATER),R ("R", Board.WATER),
        S ("S", Board.WATER),T ("T", Board.WATER),U ("U", Board.WATER),
        V ("V", Board.WATER),W ("W", Board.WATER),X ("X", Board.WATER),
        Y ("Y", Board.WATER),Z ("Z", Board.WATER);
        

        private final String actualVal;
        private final String shownVal;
        
        Model(String actualV, String shownV){
            
            actualVal = actualV;
            shownVal = shownV;
        }
        
        public String actualVal(){
            return actualVal;
        }
        
        public String shownVal(){
            return shownVal;
        }
    }
    
    //Finish constructor
    public Ship(Model model, int health, int dir){
        super(model.actualVal, model.shownVal);
        this.health = health;
        this.dir = dir;
    }
    
    public boolean checkSpace(){
        return true;
    }
    
    public boolean getSunk(){
        return sunk;
    }
    
    public boolean wasHit(){
        return false;
    }
    
    public int getHealth(){
        return health;
    }
    
    public int getDirection(){
        return dir;
    }
    
    public void setDirection(int dir){
        this.dir = dir;
    }
    
    public void setSunk(boolean status){
        sunk = status;
    }
    
    public boolean fireShot(){
        System.out.println(Battleship.HIT);
        health--;
        if(health==0){
            System.out.println(Battleship.SUNK);
            sunk = true;
        }
        return true;
    }
    
    
}