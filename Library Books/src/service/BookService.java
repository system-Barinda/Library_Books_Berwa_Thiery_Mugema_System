package service;

import db.DBConnection;
import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    /* =========================
       CRUD OPERATIONS (BOOK)
       ========================= */

    public void addBook(Book book) throws Exception {
        String sql = "INSERT INTO books(title, author, isbn, category, status) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getCategory());
            ps.setString(5, book.getStatus());
            ps.executeUpdate();
        }
    }

    public ArrayList<Book> getAllBooks() throws Exception {
        ArrayList<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setIsbn(rs.getString("isbn"));
                b.setCategory(rs.getString("category"));
                b.setStatus(rs.getString("status"));
                list.add(b);
            }
        }
        return list;
    }

    public void updateBook(Book book) throws Exception {
        String sql = "UPDATE books SET title=?, author=?, isbn=?, category=?, status=? WHERE id=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getIsbn());
            ps.setString(4, book.getCategory());
            ps.setString(5, book.getStatus());
            ps.setInt(6, book.getId());
            ps.executeUpdate();
        }
    }

    public void deleteBook(int id) throws Exception {
        String sql = "DELETE FROM books WHERE id=?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /* =========================
       DASHBOARD COUNTERS
       ========================= */

    public int countBooks() {
        return getSingleInt("SELECT COUNT(*) FROM books");
    }

    public int countAuthors() {
        return getSingleInt("SELECT COUNT(DISTINCT author) FROM books");
    }

    public int countCategories() {
        return getSingleInt("SELECT COUNT(DISTINCT category) FROM books WHERE category <> ''");
    }

    public int countAvailableBooks() {
        return getSingleInt("SELECT COUNT(*) FROM books WHERE status='Available'");
    }

    /* =========================
       CATEGORY PAGE
       ========================= */

    public List<Object[]> getCategoriesWithCount() {
        List<Object[]> list = new ArrayList<>();

        String sql = "SELECT category, COUNT(*) AS total " +
                     "FROM books " +
                     "WHERE category <> '' " +
                     "GROUP BY category " +
                     "ORDER BY category";

        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Object[]{
                        rs.getString("category"),
                        rs.getInt("total")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* =========================
       HELPER METHOD
       ========================= */

    private int getSingleInt(String sql) {
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
