/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Admin
 */
public class Muur extends Spel_item {

    Muur(boolean verwoestbaar) {
        this.vernietigbaar = verwoestbaar;
        this.loopbaar = false;
    }

    @Override
    public Vierkant teken(){
        Vierkant plaatje = new Vierkant(this.getX() + 1, this.getY() + 1, "grijs");
        return plaatje;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Muur)) {
            return false;
        }
        return true;
    }

    public void vernietig() {
        System.out.println("muur word vernietigd");
    }

    public boolean getVernietigbaar() {
        return vernietigbaar;
    }
    
    @Override
    public String toString(){
        return "O";
    }
}
