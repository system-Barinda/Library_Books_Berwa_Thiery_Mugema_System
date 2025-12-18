package ui;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Premier League Goals Prediction");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new FanPanel());
        setVisible(true);
    }
}
