package ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Book;
import service.BookService;

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

        // Table Setup
        model = new DefaultTableModel(
            new String[]{"ID", "Title", "Author", "ISBN", "Category", "Status"}, 0
        ) {
            // Makes ID column safe for Integer retrieval
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                return super.getColumnClass(columnIndex);
            }
            // Prevent direct editing in table
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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
        model.setRowCount(0);
        try {
            // The service method should throw an exception if the connection fails
            for (Book b : service.getAllBooks()) {
                model.addRow(new Object[]{
                    b.getId(), b.getTitle(), b.getAuthor(),
                    b.getIsbn(), b.getCategory(), b.getStatus()
                });
            }
        } catch (Exception e) {
             // FIX: Display a user-friendly error message on failure
             JOptionPane.showMessageDialog(
                this, 
                "Failed to load books. Check your database connection.\nDetails: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Safely retrieve ID and create Book object
        Book book = new Book(
            model.getValueAt(row, 1).toString(),
            model.getValueAt(row, 2).toString(),
            model.getValueAt(row, 3).toString(),
            model.getValueAt(row, 4).toString(),
            model.getValueAt(row, 5).toString()
        );
        // Cast the ID value safely
        book.setId((Integer) model.getValueAt(row, 0)); 

        new BookFormDialog(book, this).setVisible(true);
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a book first", "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Safely retrieve the book ID, knowing column 0 is Integer
                int bookId = (Integer) model.getValueAt(row, 0); 

                service.deleteBook(bookId);
                
                JOptionPane.showMessageDialog(this, "Book deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBooks(); // Refresh the table after successful deletion

            } catch (Exception e) {
                 // 🛑 CRITICAL FIX: Catch the exception if the database call fails
                JOptionPane.showMessageDialog(
                    this, 
                    "Failed to delete book due to database error.\nDetails: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        }
    }
}