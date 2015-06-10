/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Admin
 */
public class Vriend  extends Spel_item{
    
    @Override
    public Vierkant teken(){
        Vierkant plaatje = new Vierkant(this.getX() + 1, this.getY() + 1, "zwaart");
        return plaatje;
    }
    
    @Override
    public int pickUp(){
        return 1;
    }
    
    @Override
    public String toString(){
        return "V";
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vriend)) {
            return false;
        }
        return true;
    }
}
