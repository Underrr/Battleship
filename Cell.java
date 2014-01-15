/**
*
*
*
*@author Derrick Lee
*
**/

public abstract class Cell{
    
    public String actualVal;
    public String shownVal;
    
    public Cell(String actual, String displayed){
        actualVal = actual;
        shownVal = displayed;
    }
    
    public String getValue(Boolean show){
        if(show){
            return actualVal;
        }else{
            return shownVal;
        }
    }
    
    public abstract boolean checkSpace();
    
    public abstract boolean wasHit();
    
    public abstract boolean fireShot();
    
}