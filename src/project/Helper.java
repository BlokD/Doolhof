/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class Helper extends Spel_item {
    
    private int helperX;
    private int helperY;
    private int uitgangX;
    private int uitgangY;
    
    public void setDimensies(int y1, int x1, int x2, int y2) {
        helperX = x1;
        helperY = y1;
        uitgangX = x2;
        uitgangY = y2;
    }
    public ArrayList<JLabel> labels;
    
    @Override
    public Vierkant teken() {
        Vierkant plaatje = new Vierkant(this.getX() + 1, this.getY() + 1, "blauw");
        return plaatje;
    }
    
    @Override
    public int pickUp() {
        return 4;
    }
    
    @Override
    public String toString() {
        return "H";
    }
    final static int TRIED = 2;
    final static int PATH = 3;
    private Spel_item[][] grid;
    private int height;
    private int width;
    private int[][] map;
    
    public boolean solve(Spel_item[][] grid) {
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;
        this.map = new int[height][width];
        return traverse(helperY, helperX);
    }
    
    private boolean traverse(int i, int j) {
        if (!isValid(i, j)) {
            return false;
        }
        
        if (isEnd(i, j)) {
            map[i][j] = PATH;
            return true;
        } else {
            map[i][j] = TRIED;
        }

        // North
        if (traverse(i - 1, j)) {
            map[i - 1][j] = PATH;
            return true;
        }
        // East
        if (traverse(i, j + 1)) {
            map[i][j + 1] = PATH;
            return true;
        }
        // South
        if (traverse(i + 1, j)) {
            map[i + 1][j] = PATH;
            return true;
        }
        // West
        if (traverse(i, j - 1)) {
            map[i][j - 1] = PATH;
            return true;
        }
        
        return false;
    }
    
    private boolean isEnd(int i, int j) {
        return i == uitgangX && j == uitgangY;
    }
    
    private boolean isValid(int i, int j) {
        if (inRange(i, j) && isOpen(i, j) && !isTried(i, j)) {
            return true;
        }
        return false;
    }
    
    private boolean isOpen(int i, int j) {
        Muur m = new Muur(true);
        Muur x = new Muur(false);
        if (grid[i][j].equals(m) || grid[i][j].equals(x)) {
            return false;
        } else {
            return true;
        }
    }
    
    private boolean isTried(int i, int j) {
        return map[i][j] == TRIED;
    }
    
    private boolean inRange(int i, int j) {
        return inHeight(i) && inWidth(j);
    }
    
    private boolean inHeight(int i) {
        return i >= 0 && i < height;
    }
    
    private boolean inWidth(int j) {
        return j >= 0 && j < width;
    }
    
    public String toStringB() {
        String s = "";
        for (int[] row : map) {
            s += Arrays.toString(row) + "\n";
        }
        
        return s;
    }
    
    public ArrayList<Spel_item> toonDoolhof(int lengte) {
        ArrayList<Spel_item> snel = new ArrayList<Spel_item>();
        int i = 0;
        int j = 0;
        int positie;
        for (int[] row : map) {
            for (int nummer : row) {
                if (nummer == 3) {
                    positie = i * lengte + j;
                    Pad p = new Pad();
                    snel.add(p);
                } else {
                    if(i!=0){
                    positie = i * lengte + j;
                    }else{
                      positie = j;  
                    }
                    Muur m = new Muur(false);
                    snel.add(m);
                }
                j++;
            }
            j = 0;
            i++;
        }
        return snel;
    }
}
