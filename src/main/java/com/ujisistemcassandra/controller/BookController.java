package com.ujisistemcassandra.controller;

import com.ujisistemcassandra.entity.Book;
//import com.ujisistemcassandra.services.BookService;
import com.ujisistemcassandra.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookService) {
        this.bookRepository = bookService;
    }

//    @PostMapping
//    public ResponseEntity<Void> saveBook(@RequestBody Book book) {
//        bookService.saveBook(book);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/books")
//    public ResponseEntity<List<Book>> getAllBooks() {
//        List<Book> books = bookService.getAllBooks();
//        return ResponseEntity.ok(books);
//    }
    @GetMapping("/books")
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }


}
