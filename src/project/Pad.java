/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Admin
 */
public class Pad extends Spel_item{
    
    @Override
    public Vierkant teken(){
        Vierkant plaatje = new Vierkant(this.getX() + 1, this.getY() + 1, "wit");
        return plaatje;
    }
    
    @Override
    public String toString(){
        return " ";
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Pad)) {
            return false;
        }
        return true;
    }
    
    public Vierkant snelste(){
        Vierkant plaatje = new Vierkant(this.getX() + 1, this.getY() + 1, "rood");
        return plaatje;
    }
    
}
