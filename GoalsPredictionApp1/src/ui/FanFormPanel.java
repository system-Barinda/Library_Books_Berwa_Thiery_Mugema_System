package ui;

import java.awt.*;
import javax.swing.*;

public class FanFormPanel extends JPanel {

    public FanFormPanel() {
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Fan Name:"));
        add(new JTextField());

        add(new JLabel("Team:"));
        add(new JTextField());

        add(new JLabel("Player One Goals:"));
        add(new JTextField());

        add(new JLabel("Player Two Goals:"));
        add(new JTextField());

        add(new JLabel("Player Three Goals:"));
        add(new JTextField());

        add(new JButton("Save"));
        add(new JButton("Display All"));
    }
}
