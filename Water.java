/**
*
*
*
*@author Derrick Lee
*
**/

public class Water extends Cell{
    
    public Water(String actual, String displayed){
        super(actual, displayed);
    }
    
    public boolean checkSpace(){
        return false;
    }
    
    public boolean wasHit(){
        return false;
    }
    
    public boolean fireShot(){
        System.out.println(Battleship.MISS);
        return false;
    }
    
}