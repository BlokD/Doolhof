/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class FrameDoolhof {

    public Spel_item[][] Doolhof;
    public ArrayList<JLabel> labels;
    public GamePanel frame;
    public JPanel jPanel1;
    public Speler S;
    public Doolhof Dh;
    public JButton startButton = new JButton("Start");
    public JButton pauzeButton = new JButton("Pauze");
    public JButton opnieuwButton = new JButton("Opnieuw");
    public boolean pauze;
    public JLabel stappenLabel = new JLabel();

    public void opbouw(int level) throws IOException {
        labels = new ArrayList<>();
        //objecten = new ArrayList<>();
        frame = new GamePanel();
        frame.setLayout(new BorderLayout());
        jPanel1 = new JPanel();
        Dh = new Doolhof();
        Dh.setDoolhof(level);
        Doolhof = Dh.getDoolhof();


        JPanel knopenPanel = new JPanel(new GridLayout(1, 6));
        ActionListener opnieuwKnop = new OpnieuwKnop();
        opnieuwButton.addActionListener(opnieuwKnop);
        ActionListener startknop = new StartKnop();
        startButton.addActionListener(startknop);
        ActionListener pauzeknop = new PauzeKnop();
        pauzeButton.addActionListener(pauzeknop);
        stappenLabel.setText("Stappen: " + (Dh.getMaxStappen()));
        knopenPanel.add(opnieuwButton);
        knopenPanel.add(pauzeButton);
        knopenPanel.add(startButton);
        knopenPanel.add(stappenLabel);

        int FRAME_WIDTH = 15 * Doolhof.length;
        int FRAME_HEIGHT = 15 * Doolhof.length + 30;
        frame.setVisible(false);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(knopenPanel, BorderLayout.NORTH);

        LevelCreater(level);
    }

    public void LevelCreater(int level) throws IOException {
        int FRAME_WIDTH = 18 * Doolhof.length;
        int FRAME_HEIGHT = 18 * Doolhof.length + 30;
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        labels.clear();
        jPanel1.removeAll();
        Dh.setDoolhof(level);
        Doolhof = Dh.getDoolhof();
        S = Dh.getSpeler();
        Dh.setStappen(0);
        stappenLabel.setText("Stappen: " + (Dh.getMaxStappen()));
        jPanel1.setLayout(new GridLayout(Doolhof.length, Doolhof.length));

        int positie = 0;
        for (int y = 0; y < Doolhof.length; y++) {
            for (int x = 0; x < Doolhof.length; x++) {
                JLabel label = new JLabel();
                labels.add(label);
                labels.get(positie).setText(Doolhof[y][x].toString());
                jPanel1.add(label);
                frame.add(jPanel1);
                positie++;
            }
        }
        labels.get(Dh.getSpeler().getY() * Doolhof.length + Dh.getSpeler().getX()).setText(Dh.getSpeler().toString());
        frame.setFocusable(true);
        frame.setVisible(true);
        jPanel1.setVisible(false);
        pauze = true;
        startButton.setFocusable(pauze);
    }

    private void beweegSpeler(int nX, int nY) {
        int X = S.getX();
        int Y = S.getY();
        int positie = Y * Doolhof.length + X;
        int nPositie = nY * Doolhof.length + nX;

        labels.get(positie).setText("   ");

        labels.get(nPositie).setText("S");

        if (Dh.setStappen(Dh.getStappen() + 1)) {
            stappenLabel.setText(" GAME OVER!");
            pauze = true;
            startButton.setFocusable(false);
        } else {
            stappenLabel.setText("Stappen: " + (Dh.getMaxStappen() - Dh.getStappen()));
        }
    }

    public boolean canMove(String direction) {
        int x = S.getX();
        int y = S.getY();
        switch (direction) {
            case "Rechts":
                x = S.getX() + 1;
                break;
            case "Links":
                x = S.getX() - 1;
                break;
            case "Omhoog":
                y = S.getY() - 1;
                break;
            case "Omlaag":
                y = S.getY() + 1;
                break;
        }

        if (Doolhof[y][x].pickUp() == 1) { //Uitgang
            pauzeButton.doClick();
            Dh.levelUp();
            try {
                LevelCreater(Dh.getLevel());
                opnieuwButton.doClick();
            } catch (IOException ex) {
                System.out.println(ex);
            }
            opnieuwButton.doClick();
            return false;
        } else if (Doolhof[y][x].pickUp() == 2) { //Valsspeler
            Dh.setMaxStappen(Dh.getMaxStappen());

            Pad P = new Pad();
            P.setP(x, y);
            Doolhof[y][x] = P;
            Doolhof[y][x].teken();
        } else if (Doolhof[y][x].pickUp() == 3) { //Bazooka
            S.setBazooka(true);

            Pad P = new Pad();
            P.setP(x, y);
            Doolhof[y][x] = P;
            Doolhof[y][x].teken();
        } else if (Doolhof[y][x].pickUp() == 4) { //Helper
            int result[] = coords();
            mazeSolver maze = new mazeSolver();
            maze.setGegevens(y, x, result[0], result[1], Doolhof);
//            Helper h = new Helper();
//            h.setDimensies(y, x, result[0], result[1]);
//            boolean solved = h.solve(Doolhof);
            Pad P = new Pad();
            P.setP(x, y);
//            int lengte = Doolhof.length;
            Doolhof[y][x] = P;
            Doolhof[y][x].teken();
            ArrayList<Spel_item> label = maze.solve();
            Pad p = new Pad();
            int i = 0;
            for (Spel_item a : label) {
                if (a.equals(p)) {
                    labels.get(i).setText(" ");
                    labels.get(i).setBackground(Color.red);
                    labels.get(i).setOpaque(true);
                }
                i++;
            }
        }
        return Doolhof[y][x].loopbaar;
    }

    public int[] coords() {
        Vriend U = new Vriend();
        int i = 0;
        int j = 0;
        for (i = 0; i < Doolhof.length; i++) {
            for (j = 0; j < Doolhof.length; j++) {
                if (Doolhof[i][j].equals(U)) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{11, 12};
    }

    public class GamePanel extends JFrame {

        public GamePanel() {
            addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(KeyEvent e) {
                    if (!pauze) {
                        if (e.getKeyCode() == 37) { //Links
                            S.setDirection('W');
                            if (canMove("Links")) {
                                int X = S.getX();
                                int Y = S.getY();
                                beweegSpeler(X - 1, Y);
                                S.setP(X - 1, Y);
                            }
                        }
                        if (e.getKeyCode() == 38) { //Omhoog
                            S.setDirection('N');
                            if (canMove("Omhoog")) {
                                int X = S.getX();
                                int Y = S.getY();
                                beweegSpeler(X, Y - 1);
                                S.setP(X, Y - 1);
                            }
                        }
                        if (e.getKeyCode() == 39) { //Rechts
                            S.setDirection('E');
                            if (canMove("Rechts")) {
                                int X = S.getX();
                                int Y = S.getY();
                                beweegSpeler(X + 1, Y);
                                S.setP(X + 1, Y);
                            }
                        }
                        if (e.getKeyCode() == 40) { //Omlaag
                            S.setDirection('S');
                            if (canMove("Omlaag")) {
                                int X = S.getX();
                                int Y = S.getY();
                                beweegSpeler(X, Y + 1);
                                S.setP(X, Y + 1);
                            }
                        }
                        if (e.getKeyCode() == 32) { //Spatie
                            if (S.getBazooka()) {
                                Bazooka b = new Bazooka();
                                S.setBazooka(false);
                                b.vuur(S.getDirection(), Doolhof, Doolhof.length, S.getX(), S.getY());
                            }
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }
            });
            setFocusable(
                    false);
        }
    }

    class OpnieuwKnop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            pauze = true;
            jPanel1.setVisible(false);
            try {
                LevelCreater(Dh.getLevel());
            } catch (IOException ex) {
                Logger.getLogger(FrameDoolhof.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class StartKnop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (pauze) {
                jPanel1.setVisible(true);
                pauze = false;
                startButton.setFocusable(pauze);
            }
        }
    }

    class PauzeKnop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (!pauze) {
                jPanel1.setVisible(false);
                pauze = true;
                startButton.setFocusable(pauze);
            }
        }
    }
}
