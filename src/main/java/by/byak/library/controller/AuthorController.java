package by.byak.library.controller;

import by.byak.library.dto.author.AuthorDTO;
import by.byak.library.entity.Author;
import by.byak.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService service;
    private static final String SUCCESS = "Completed successfully";

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAllAuthors() {
        return ResponseEntity.ok(service.findAllAuthors());
    }

    @GetMapping("/find")
    public ResponseEntity<AuthorDTO> findByName(@RequestParam String name) {
        AuthorDTO author = service.findByName(name);
        return ResponseEntity.ok(author);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAuthor(@RequestBody Author author) {
        service.addAuthor(author);
        return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAuthorById(@RequestParam Long id) {
        service.deleteAuthorById(id);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<String> updateAuthor(@RequestParam Long id, @RequestBody Author author) {
        service.updateAuthor(id, author);
        return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
    }
}
