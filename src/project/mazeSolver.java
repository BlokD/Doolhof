/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLabel;

/**
 *
 * @author bram
 */
public class mazeSolver {

    private int spelerX;
    private int spelerY;
    private int uitgangX;
    private int uitgangY;
    private Spel_item[][] Doolhof;
    public ArrayList<String> paden = new ArrayList<>();
    public ArrayList<Integer> length = new ArrayList<>();
    public ArrayList<int[]> bezocht = new ArrayList<>();
    public ArrayList<Boolean> actief = new ArrayList<>();

    public ArrayList<Spel_item> solve() {
        int[] eerste = new int[3];
        eerste[0] = spelerY;
        eerste[1] = spelerX;
        eerste[2] = 0;
        bezocht.add(eerste);
        int a = 0;
        length.add(a);
        String begin = String.valueOf(spelerX) + "," + String.valueOf(spelerY) + "/";
        paden.add(begin);
        actief.add(true);
        String answer = checkNodes();
        return showPad(answer);
    }

    private ArrayList<Spel_item> showPad(String answer) {
//        ArrayList<int[]> coordinaten = new ArrayList<>();
//        coordinaten = getLaatsteCoords(getIndexOfMin());
        ArrayList<Spel_item> snel = new ArrayList<>();
        Muur m = new Muur(true);
        for (int i = 0; i < Doolhof.length; i++) {
            for (int j = 0; j < Doolhof.length; j++) {
                snel.add(m);
            }

        }
        int positie;
        int[] nummers = new int[2];
        String numbers[] = answer.split("/");
        for (String part : numbers) {
            nummers[0] = Integer.parseInt(part.split(",")[0]);
            nummers[1] = Integer.parseInt(part.split(",")[1]);
            positie = nummers[0] * Doolhof.length + nummers[1];
            Pad p = new Pad();
            snel.set(positie, p);
        }
        return snel;
    }

    public void setGegevens(int sX, int sY, int uX, int uY, Spel_item[][] d) {
        spelerX = sX;
        spelerY = sY;
        uitgangX = uX;
        uitgangY = uY;
        Doolhof = d;
    }

    private int selectNode() {
        int number = getIndexOfMin();
        return number;
    }

    public int getIndexOfMin() {
        int min = 9999;
        int minIndex = 0;
        for (int i = 0; i < length.size(); i++) {
            int j = length.get(i);
            if (j < min && actief.get(i)) {
                min = j;
                minIndex = i;
            }
        }
        return minIndex;
    }

    private String checkNodes() {
        int x;
        int y;
        int[] coords = new int[2];
        Muur m = new Muur(true);
        boolean first;
        while (checkEinde() == false) {
            System.out.println("a");
            first = true;
            int node = selectNode();
            int afstand = length.get(node) + 1;
            String pad = paden.get(node);
            ArrayList<int[]> coordinaten;
            coordinaten = getLaatsteCoords(node);
            int aantal = coordinaten.size();
            final int X = coordinaten.get(aantal - 1)[1];
            final int Y = coordinaten.get(aantal - 1)[0];
            y = Y;
            x = X;
            if (Doolhof[y - 1][x].equals(m) || oudKleinerDanNieuw(x, (y - 1), afstand, node)) {
            } else {
                String NieuwPad = pad + (y - 1) + "," + x + "/";
                if (first) {
                    paden.set(node, NieuwPad);
                    length.set(node, (length.get(node) + 1));
                    first = false;
                } else {
                    paden.add(NieuwPad);
                    length.add(length.get(node) + 1);
                    actief.add(true);
                }
            }
            y = Y;
            x = X;
            if (Doolhof[y][x + 1].equals(m) || oudKleinerDanNieuw((x + 1), y, afstand, node)) {
            } else {
                String NieuwPad = pad + y + "," + (x + 1) + "/";
                if (first) {
                    first = false;
                    paden.set(node, NieuwPad);
                    length.set(node, (length.get(node) + 1));
                } else {
                    paden.add(NieuwPad);
                    length.add(length.get(node) + 1);
                    actief.add(true);
                }
            }
            y = Y;
            x = X;
            if (Doolhof[y + 1][x].equals(m) || oudKleinerDanNieuw(x, (y + 1), afstand, node)) {
            } else {
                String NieuwPad = pad + (y + 1) + "," + x + "/";
                if (first) {
                    first = false;
                    paden.set(node, NieuwPad);
                    length.set(node, (length.get(node) + 1));
                } else {
                    paden.add(NieuwPad);
                    length.add(length.get(node) + 1);
                    actief.add(true);
                }
            }
            y = Y;
            x = X;
            if (Doolhof[y][x - 1].equals(m) || oudKleinerDanNieuw((x - 1), y, afstand, node)) {
            } else {
                String NieuwPad = pad + y + "," + (x - 1) + "/";
                if (first) {
                    first = false;
                    paden.set(node, NieuwPad);
                    length.set(node, (length.get(node) + 1));
                } else {
                    paden.add(NieuwPad);
                    length.add(length.get(node) + 1);
                    actief.add(true);
                }
            }
            if (first) {
                actief.set(node, false);
            }
        }
        return (paden.get(selectNode()));
    }

    private boolean checkEinde() {
        int node = selectNode();
        ArrayList<int[]> coordinaten = new ArrayList<>();
        coordinaten = getLaatsteCoords(node);
        for (int i = 0; i < 10; i++) {
            
        }
        System.out.println(node);
        System.out.println(coordinaten.size());
        if(node>=coordinaten.size()){
            node = (coordinaten.size()-1);
            System.out.println("groter");
        }
        int x = coordinaten.get(node)[0];
        int y = coordinaten.get(node)[1];
        if (uitgangX == x && uitgangY == y) {
            return true;
        }
        return false;
    }

    private ArrayList<int[]> getLaatsteCoords(int node) {
        int[] nummers = new int[2];
        ArrayList<int[]> coords = new ArrayList<>();
        int[] out = new int[2];
        String numbers[] = paden.get(node).split("/");
        for (String part : numbers) {
            nummers[0] = Integer.parseInt(part.split(",")[0]);
            nummers[1] = Integer.parseInt(part.split(",")[1]);
            coords.add(nummers);
        }
        return coords;
    }

    private boolean oudKleinerDanNieuw(int x, int y, int nieuweLengte, int node) {
        final boolean NIEUW_IS_KLEINER_DAN_OUD = false;
        final boolean OUD_IS_KLEINER_DAN_NIEUW = true;
        final boolean NODE_IS_NOG_NIET_BEZOCHT = false;
        int lengte = bezocht.size();
        int lengtePaden;
        int[] lengtes = new int[3];
        for (int i = 0; i < lengte; i++) {
            if (bezocht.get(i)[0] == x && bezocht.get(i)[1] == y) {
                lengtePaden = bezocht.get(i)[2];
                if (nieuweLengte < lengtePaden) {
                    lengtes[0] = x;
                    lengtes[1] = y;
                    lengtes[2] = nieuweLengte;

                    bezocht.set(node, lengtes);
                    return NIEUW_IS_KLEINER_DAN_OUD;
                } else {
                    return OUD_IS_KLEINER_DAN_NIEUW;
                }
            }
        }
        lengtes[0] = x;
        lengtes[1] = y;
        lengtes[2] = nieuweLengte;
        bezocht.add(lengtes);
        return NODE_IS_NOG_NIET_BEZOCHT;
    }
}
