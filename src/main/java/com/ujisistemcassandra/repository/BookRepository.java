//package com.ujisistemcassandra.repository;
//
//import com.ujisistemcassandra.entity.Book;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class BookRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//
//    public BookRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public void save(Book book) {
//        jdbcTemplate.update("INSERT INTO ujisistem.books (id, title, author) VALUES (?, ?, ?)",
//                book.getId(), book.getTitle(), book.getAuthor());
//    }
//
////    public List<Book> findAll() {
////        return jdbcTemplate.query("SELECT * FROM ujisistem.books",
////                (resultSet, rowNum) -> new Book(resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("author")));
////    }
//
//    public List<Book> findAll() {
//        return jdbcTemplate.query("SELECT * FROM ujisistem.books",
//                (resultSet, rowNum) -> {
//                    Book book = new Book();
//                    book.setId(resultSet.getString("id"));
//                    book.setTitle(resultSet.getString("title"));
//                    book.setAuthor(resultSet.getString("author"));
//                    return book;
//                });
//    }
//}
//
