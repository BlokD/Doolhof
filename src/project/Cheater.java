/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Random;

/**
 *
 * @author Admin
 */
public class Cheater extends Spel_item{
    private int waarde = 0;
    
    public Cheater(){
        Random generator = new Random();
        waarde = generator.nextInt(30) + 1;
    }
    
    @Override
    public Vierkant teken(){
        Vierkant plaatje = new Vierkant(this.getX() + 1, this.getY() + 1, "wit");
        return plaatje;
    }
    
    @Override
    public int pickUp(){
        return 2;
    }
    
    @Override
    public String toString(){
        return waarde + "";
    }
}
