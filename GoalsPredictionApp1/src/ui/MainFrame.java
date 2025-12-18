package ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Premier League Goals Prediction");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new FanFormPanel());
        setVisible(true);
    }
}
