package ui;

import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoryPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private BookService service = new BookService();

    public CategoryPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Categories");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Category Name", "Total Books"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadCategories();
    }

    private void loadCategories() {
        model.setRowCount(0);
        service.getCategoriesWithCount().forEach(row ->
                model.addRow(new Object[]{row[0], row[1]})
        );
    }
}
