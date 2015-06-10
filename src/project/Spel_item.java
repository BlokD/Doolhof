/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Admin
 */
public abstract class Spel_item{
    private Vakje vakje;
    public boolean loopbaar = true;
    public boolean vernietigbaar = false;
    public Doolhof Dh;
    private Spel_item Valsspeler;
    private Spel_item Uitgang;
    private Spel_item Muur;
    private Spel_item Helper;
    private Spel_item Bazooka;
    private int X;
    private int Y;
    
    public int getX(){
        return X;
    }
    
    public int getY(){
        return Y;
    }
    
    public void setP(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    
    public void setDH(Doolhof Dh){
        this.Dh = Dh;
    }
    
    public int pickUp(){
        return 0;
    }
    
    public Vierkant teken(){
        Vierkant plaatje = new Vierkant(X + 1, Y + 1, "wit");
        return plaatje;
    }
    
    public Spel_item setVoowerp(String voorwerp){
        switch (voorwerp) {
            case "V":
                return Valsspeler;
            case "M":
                return Muur;
            case "X":
                return Muur;
            case "H":
                return Helper;
            case "U":
                return Uitgang;
            default:
                return null;
        }
    }
    
    public Spel_item getVoowerp(Spel_item voorwerp){
        if (voorwerp == Valsspeler) {
            return Valsspeler;
        } else if (voorwerp == Helper) {
            return Helper;
        } else if (voorwerp == Bazooka) {
            return Bazooka;
        } else if (voorwerp == Uitgang) {
            return Uitgang;
        } else if (voorwerp == Muur) {
            return Muur;
        } else {
            return null;
        }
    }
    
    @Override
    public String toString(){
        return "";
    }
}
