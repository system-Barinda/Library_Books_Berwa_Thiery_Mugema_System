package ui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;

    public MainFrame() {
        setTitle("Library System");
        setSize(1200, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel(new GridLayout(10, 1));
        sidebar.setBackground(new Color(33, 37, 41));

        JButton btnDashboard = createMenuButton("Dashboard");
        JButton btnBooks = createMenuButton("Book");
        JButton btnCategory = createMenuButton("Category");

        sidebar.add(btnDashboard);
        sidebar.add(btnBooks);
        sidebar.add(btnCategory);

        contentPanel = new JPanel(new BorderLayout());

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        showDashboard();

        btnDashboard.addActionListener(e -> showDashboard());
        btnBooks.addActionListener(e -> showBooks());
        btnCategory.addActionListener(e -> showCategories());
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 58, 64));
        return btn;
    }

    private void showDashboard() {
        contentPanel.removeAll();
        contentPanel.add(new DashboardPanel(), BorderLayout.CENTER);
        refresh();
    }

    private void showBooks() {
        contentPanel.removeAll();
        contentPanel.add(new BookPanel(), BorderLayout.CENTER);
        refresh();
    }

    private void showCategories() {
        contentPanel.removeAll();
        contentPanel.add(new CategoryPanel(), BorderLayout.CENTER);
        refresh();
    }

    private void refresh() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
