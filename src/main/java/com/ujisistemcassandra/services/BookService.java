package com.ujisistemcassandra.services;

import com.ujisistemcassandra.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class BookService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        String query = "SELECT * FROM books";
        return jdbcTemplate.query(query, new BookRowMapper());
    }

    public Book getBookById(String id) {
        String query = "SELECT * FROM books WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, new BookRowMapper());
    }

    public void saveBook(Book book) {
        String query = "INSERT INTO books (id, title, author) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, book.getId(), book.getTitle(), book.getAuthor());
    }

    public void deleteBook(String id) {
        String query = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            String id = rs.getString("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            return new Book(id, title, author);
        }
    }
}

