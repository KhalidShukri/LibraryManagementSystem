package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class LibraryService {
    private static final Logger logger = LoggerFactory.getLogger(LibraryService.class);

    private BookDAO bookDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO();
    }
    public void addBook(Book book) throws SQLException {
        bookDAO.addBook(book);
    }
    public List<Book> getAllBooks() throws SQLException{
        return bookDAO.getAllBooks();
    }
}
