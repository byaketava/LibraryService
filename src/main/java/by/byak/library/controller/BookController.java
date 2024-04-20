package by.byak.library.controller;

import by.byak.library.dto.book.BookDto;
import by.byak.library.entity.Book;
import by.byak.library.service.BookService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {
  private final BookService service;
  private static final String SUCCESS = "Completed successfully";

  @GetMapping("/findByAuthorAndGenre")
  public ResponseEntity<List<BookDto>> findByAuthorIdAndGenreId(
      @RequestParam Long authorId, @RequestParam Long genreId) {
    List<BookDto> books = service
        .findByAuthorIdAndGenreId(authorId, genreId);
    return ResponseEntity.ok(books);
  }

  @GetMapping
  public ResponseEntity<List<BookDto>> findAllBooks() {
    return ResponseEntity.ok(service.findAllBooks());
  }

  @GetMapping("/find")
  public ResponseEntity<BookDto> findBookByTitle(@RequestParam String title) {
    BookDto book = service.findBookByTitle(title);
    return ResponseEntity.ok(book);
  }

  @PostMapping("/add")
  public ResponseEntity<String> addBook(@RequestBody Book book) {
    service.addBook(book);
    return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteBookById(@RequestParam Long id) {
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
