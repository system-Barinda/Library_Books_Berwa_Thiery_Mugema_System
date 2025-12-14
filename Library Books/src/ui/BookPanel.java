package ui;

import model.Book;
import service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BookPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private BookService service = new BookService();

    public BookPanel() {
        setLayout(new BorderLayout());

        // Header
        JLabel title = new JLabel("Books", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(
                new String[]{"ID", "Title", "Author", "ISBN", "Category", "Status"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel();

        JButton btnAdd = new JButton("Add Book");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        add(buttons, BorderLayout.SOUTH);

        loadBooks();

        // Actions
        btnAdd.addActionListener(e ->
                new BookFormDialog(null, this).setVisible(true)
        );

        btnUpdate.addActionListener(e -> updateBook());

        btnDelete.addActionListener(e -> deleteBook());
    }

    public void loadBooks() {
        try {
            model.setRowCount(0);
            for (Book b : service.getAllBooks()) {
                model.addRow(new Object[]{
                        b.getId(), b.getTitle(), b.getAuthor(),
                        b.getIsbn(), b.getCategory(), b.getStatus()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first");
            return;
        }

        Book book = new Book(
                model.getValueAt(row, 1).toString(),
                model.getValueAt(row, 2).toString(),
                model.getValueAt(row, 3).toString(),
                model.getValueAt(row, 4).toString(),
                model.getValueAt(row, 5).toString()
        );
        book.setId(Integer.parseInt(model.getValueAt(row, 0).toString()));

        new BookFormDialog(book, this).setVisible(true);
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Delete this book?", "Confirm", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            service.deleteBook(
                    Integer.parseInt(model.getValueAt(row, 0).toString())
            );
            loadBooks();
        }
    }
}
