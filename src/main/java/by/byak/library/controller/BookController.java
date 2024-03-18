package by.byak.library.controller;

import by.byak.library.dto.book.BookDTO;
import by.byak.library.entity.Book;
import by.byak.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
    private final BookService service;
    private static final String SUCCESS = "Completed successfully";

    @GetMapping
    public List<BookDTO> findAllBooks() {
        return service.findAllBooks();
    }

    @GetMapping("/find")
    public ResponseEntity<List<BookDTO>> findByTitle (@RequestParam String title) {
        List<BookDTO> books = service.findByTitle(title);
        if (books==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        Optional<Book> savedBook = service.addBook(book);

        if (savedBook.isPresent()) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("A book with that title already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBookById (@PathVariable Long id) {
        service.deleteBookById(id);
    }

    @PatchMapping("/update")
    ResponseEntity<String> updateBook(@RequestParam Long id, @RequestParam String title) {
        boolean updated = service.updateBook(id, title);
        if (updated) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("A book for the update has not been found", HttpStatus.NOT_FOUND);
        }
    }
}
