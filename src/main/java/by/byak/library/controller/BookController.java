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

    @GetMapping("/findByAuthorAndGenre")
    public ResponseEntity<List<BookDTO>> findByAuthorIdAndGenreId(@RequestParam Long authorId,
                                                                  @RequestParam Long genreId) {
        List<BookDTO> books = service.findByAuthorIdAndGenreId(authorId, genreId);
        if (books.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
        //return service.findByAuthorIdAndGenreId(authorId, genreId);
    }

    @GetMapping
    public List<BookDTO> findAllBooks() {
        return service.findAllBooks();
    }

    @GetMapping("/find")
    public ResponseEntity<BookDTO> findBookByTitle (@RequestParam String title) {
        BookDTO book = service.findBookByTitle(title);
        if (book==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBookById (@RequestParam Long id) {
        if (service.deleteBookById(id)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no book with that id", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    ResponseEntity<String> updateBook(@RequestParam Long id,
                                      @RequestBody Book book) {
        boolean updated = service.updateBook(id, book);
        if (updated) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("A book for the update has not been found", HttpStatus.NOT_FOUND);
        }
    }
}
