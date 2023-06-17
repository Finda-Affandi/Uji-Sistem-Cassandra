package com.ujisistemcassandra.controller;


//import com.ujisistemcassandra.services.BookService;
import com.ujisistemcassandra.entity.Mahasiswa;
import com.ujisistemcassandra.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {
//    Fandi
    private final MahasiswaRepository mahasiswaRepository;
    @Autowired
    public BookController(MahasiswaRepository mahasiswaRepository) {
        this.mahasiswaRepository = mahasiswaRepository;
    }

//    private final BookRepository bookRepository;

//    public BookController(BookRepository bookService) {
//        this.bookRepository = bookService;
//    }

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
//    @GetMapping("/books")
//    public List<Book> getAllBook() {
//        return bookRepository.findAll();
//    }


    //Fandi
    @GetMapping("/mahasiswa")
    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaRepository.findAll();
    }

    @PostMapping("/mahasiswa")
    public ResponseEntity<Void> saveMahasiswa(@RequestBody Mahasiswa mahasiswa) {
        mahasiswaRepository.saveMahasiswa(mahasiswa);
        return ResponseEntity.ok().build();
    }


}
