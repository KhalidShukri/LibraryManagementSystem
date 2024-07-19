package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private static Logger logger = LoggerFactory.getLogger(BookDAO.class);
//    a method to add a book
    public void addBook(Book book)throws SQLException {
        String sql = "INSERT INTO books(title,author,isbn,published_date,quantity) VALUES(?,?,?,?,?)";
        try (Connection conn = DatabaseConfiguration.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setDate(4,new Date(book.getPublishedDate().getTime()));
            pstmt.setInt(5,book.getQuantity());
            int affectedRows = pstmt.executeUpdate();
            if(affectedRows == 0){
                throw new SQLException("Creating Book Failed,no rows affected");
            }
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if (generatedKeys.next()){
                    book.setId(generatedKeys.getInt(1));
                } else{
                    throw new SQLException("Creating Book Failed, no ID was obtained");
                }
            }
            logger.info("Book Created Successfully: {}",book);

        }
    }
    public Book getBookbyId(int id){
        String sql = "SELECT * FROM books WHERE id = ?";
        try(Connection conn = DatabaseConfiguration.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    return extractBookFromResultSet(rs);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
//    a method to return all available books
    public List<Book> getAllBooks() throws SQLException{
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConfiguration.getConnection();
        Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                books.add(extractBookFromResultSet(rs));
            }

                   }
        return books;
    }
//    method to update a book
    public void updateBook(Book book) throws SQLException{
        String sql = "UPDATE books SET title = ?,author = ?,isbn = ?,published_date = ?,quantity = ? WHERE id = ?";
        try(Connection conn = DatabaseConfiguration.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setDate(4,new Date(book.getPublishedDate().getTime()));
            pstmt.setInt(5,book.getQuantity());
            pstmt.setInt(6,book.getId());
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected == 0){
                throw new SQLException("Updating book Failed,no rows Affected");
            }
            logger.info("Book Updated Successfully: {}",book);
        }
    }
    public void deleteBook(int id) throws SQLException{
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DatabaseConfiguration.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("Deleting Books Failed,no rows affected");
            }
            logger.info("Books Deleted Successfully: {}",id);

        }
    }
//    method to return book from result set
    public Book extractBookFromResultSet(ResultSet rs) throws SQLException{
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublishedDate(rs.getDate("published_date"));
        book.setQuantity(rs.getInt("quantity"));
        return book;
    }
}
