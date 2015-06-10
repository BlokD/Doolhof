/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class startFrame {

    public JFrame frame = new JFrame();
    public JTextField level = new JTextField(10);
    public JLabel error = new JLabel("voer een geldig level in");

    public void FrameMaken() {
        final int FRAME_WIDTH = 200;
        final int FRAME_HEIGHT = 200;
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        JButton startLevel = new JButton("start level");
        JLabel label = new JLabel("voer level in");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(level);
        panel.add(error);
        error.setVisible(false);
        panel.add(startLevel);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        ActionListener startLVL = new StartLevelKnop();
        startLevel.addActionListener(startLVL);

    }

    class StartLevelKnop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            int LVL = Integer.parseInt(level.getText());
            if (LVL != 1 && LVL != 2 && LVL !=3 && LVL !=4 && LVL != 5) {
                error.setVisible(true);
            } else {
                frame.dispose();
                try {
                    ProjectDDoolhof.beginLevel(LVL);
                } catch (IOException ex) {
                    Logger.getLogger(startFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
