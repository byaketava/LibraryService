package by.byak.library.controller;

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

    @GetMapping
    public List<Book> findAllBooks() {
        return service.findAllBooks();
    }

    @GetMapping("/find")
    public Book findByTitle (@RequestParam String title) {
        return service.findByTitle(title);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        Optional<Book> savedBook = service.addBook(book);

        if (savedBook.isPresent()) {
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("A book with that title already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteBookByTitle (@PathVariable String name) {
        if (service.deleteBookByTitle(name)) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no book with that name", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update")
    ResponseEntity<String> updateBook(@RequestParam Long id, @RequestParam String title) {
        boolean updated = service.updateBook(id, title);
        if (updated) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("A book for the update has not been found", HttpStatus.NOT_FOUND);
        }
    }
}
