package project;


import java.awt.*;
import javax.swing.*;

/* Create a new graphics component that draws two <strong class="highlight">rectangles</strong> 
*/
public class Vierkant extends JPanel
{
private int y;
private int x;
   
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;	
        Rectangle box = new Rectangle(x, y, 18, 18);
        g2.draw(box);
    }

    public Vierkant(int x, int y, String kleur)
    {	   
        this.x = x;
        this.y = y + 30;   
    }
}
