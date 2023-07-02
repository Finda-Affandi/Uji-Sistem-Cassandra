//package com.ujisistemcassandra.services;
//
//
//import com.ujisistemcassandra.entity.Book;
//import com.ujisistemcassandra.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookService {
//
//    private final BookRepository bookRepository;
//
//    @Autowired
//    public BookService(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    public void saveBook(Book book) {
//        bookRepository.save(book);
//    }
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }
//}
//
