package by.byak.library.controller;

import by.byak.library.dto.author.AuthorDTO;
import by.byak.library.entity.Author;
import by.byak.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService service;
    private static final String SUCCESS = "Completed successfully";

    @GetMapping
    public List<AuthorDTO> findAllAuthors() {
        return service.findAllAuthors();
    }

    @GetMapping("/find")
    public ResponseEntity<AuthorDTO> findByName (@RequestParam String name) {
        AuthorDTO author = service.findByName(name);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAuthor(@RequestBody Author author) {
        Optional<Author> savedAuthor = service.addAuthor(author);

        if (savedAuthor.isPresent()) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("An author with that name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAuthorById (@RequestParam Long id) {
        if (service.deleteAuthorById(id)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no author with that name", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    ResponseEntity<String> updateAuthor(@RequestParam Long id, @RequestBody Author author) {
        boolean updated = service.updateAuthor(id, author);
        if (updated) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("An author for the update has not been found", HttpStatus.NOT_FOUND);
        }
    }
}
