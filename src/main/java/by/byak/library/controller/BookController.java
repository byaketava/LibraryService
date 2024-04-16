package by.byak.library.controller;

import by.byak.library.dto.book.BookDTO;
import by.byak.library.entity.Book;
import by.byak.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService service;
    private static final String SUCCESS = "Completed successfully";

    @GetMapping("/findByAuthorAndGenre")
    public ResponseEntity<List<BookDTO>> findByAuthorIdAndGenreId(@RequestParam Long authorId,
                                                                  @RequestParam Long genreId) {
        List<BookDTO> books = service.findByAuthorIdAndGenreId(authorId, genreId);
        return ResponseEntity.ok(books);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAllBooks() {
        return ResponseEntity.ok(service.findAllBooks());
    }

    @GetMapping("/find")
    public ResponseEntity<BookDTO> findBookByTitle (@RequestParam String title) {
        BookDTO book = service.findBookByTitle(title);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        service.addBook(book);
        return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBookById (@RequestParam Long id) {
        service.deleteBookById(id);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<String> updateBook(@RequestParam Long id,
                                      @RequestBody Book book) {
        service.updateBook(id, book);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}
