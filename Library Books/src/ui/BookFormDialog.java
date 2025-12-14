package ui;

import model.Book;
import service.BookService;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class BookFormDialog extends JDialog {

    private JTextField txtTitle, txtAuthor, txtIsbn, txtCategory;
    private JComboBox<String> cmbStatus;
    private BookService service = new BookService();
    private BookPanel parent;
    private Book book;

    public BookFormDialog(Book book, BookPanel parent) {
        this.book = book;
        this.parent = parent;

        setTitle(book == null ? "Add Book" : "Update Book");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new GridLayout(6, 2, 10, 10));

        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtIsbn = new JTextField();
        txtCategory = new JTextField();
        cmbStatus = new JComboBox<>(new String[]{"Available", "Borrowed"});

        add(new JLabel("Title"));
        add(txtTitle);
        add(new JLabel("Author"));
        add(txtAuthor);
        add(new JLabel("ISBN"));
        add(txtIsbn);
        add(new JLabel("Category"));
        add(txtCategory);
        add(new JLabel("Status"));
        add(cmbStatus);

        JButton btnSave = new JButton("Save");
        add(new JLabel());
        add(btnSave);

        if (book != null) {
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtIsbn.setText(book.getIsbn());
            txtCategory.setText(book.getCategory());
            cmbStatus.setSelectedItem(book.getStatus());
        }

        btnSave.addActionListener(e -> saveBook());
    }

    private void saveBook() {
        if (!validateData()) {
            JOptionPane.showMessageDialog(this, "Invalid input");
            return;
        }

        try {
            if (book == null) {
                service.addBook(new Book(
                        txtTitle.getText(),
                        txtAuthor.getText(),
                        txtIsbn.getText(),
                        txtCategory.getText(),
                        cmbStatus.getSelectedItem().toString()
                ));
            } else {
                book.setTitle(txtTitle.getText());
                book.setAuthor(txtAuthor.getText());
                book.setIsbn(txtIsbn.getText());
                book.setCategory(txtCategory.getText());
                book.setStatus(cmbStatus.getSelectedItem().toString());
                service.updateBook(book);
            }

            parent.loadBooks();
            dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateData() {
        return Pattern.matches("^[A-Za-z0-9 ]{2,150}$", txtTitle.getText())
                && Pattern.matches("^[A-Za-z ]{2,100}$", txtAuthor.getText())
                && Pattern.matches("^[0-9]{10,13}$", txtIsbn.getText());
    }
}
