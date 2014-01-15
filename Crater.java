/**
*
*
*
*@author Derrick Lee
*
**/

public class Crater extends Cell{
    
    public Crater(String actual, String displayed){
        super(actual, displayed);
    }
    
    public boolean checkSpace(){
        return true;
    }
    
    public boolean wasHit(){
        return true;
    }
    
    public boolean fireShot(){
        System.err.println("Already fired at!");
        return false;
    }

}