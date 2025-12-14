package service;


import db.DBConnection;
import model.Book;
import java.sql.*;
import java.util.ArrayList;


public class BookService {


public void addBook(Book book) throws Exception {
String sql = "INSERT INTO books(title, author, isbn, category, status) VALUES (?,?,?,?,?)";
PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
ps.setString(1, book.getTitle());
ps.setString(2, book.getAuthor());
ps.setString(3, book.getIsbn());
ps.setString(4, book.getCategory());
ps.setString(5, book.getStatus());
ps.executeUpdate();
}


public ArrayList<Book> getAllBooks() throws Exception {
ArrayList<Book> list = new ArrayList<>();
String sql = "SELECT * FROM books";
Statement st = DBConnection.getConnection().createStatement();
ResultSet rs = st.executeQuery(sql);


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
return list;
}
}