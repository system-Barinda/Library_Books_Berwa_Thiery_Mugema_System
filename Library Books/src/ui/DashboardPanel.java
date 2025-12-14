package ui;

import service.BookService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private BookService service = new BookService();

    public DashboardPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2, 4, 20, 20));
        cards.setOpaque(false);

        cards.add(createCard("Total Books", service.countBooks(), new Color(25, 135, 84)));
        cards.add(createCard("Total Authors", service.countAuthors(), new Color(220, 53, 69)));
        cards.add(createCard("Total Categories", service.countCategories(), new Color(255, 193, 7)));
        cards.add(createCard("Available Books", service.countAvailableBooks(), new Color(13, 110, 253)));

        add(cards, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, int value, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblValue = new JLabel(String.valueOf(value), JLabel.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(Color.WHITE);

        JLabel lblTitle = new JLabel(title, JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTitle.setForeground(Color.WHITE);

        card.add(lblValue, BorderLayout.CENTER);
        card.add(lblTitle, BorderLayout.SOUTH);

        return card;
    }
}
