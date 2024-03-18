package by.byak.library.controller;

import by.byak.library.dto.genre.GenreDTO;
import by.byak.library.entity.Genre;
import by.byak.library.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreService service;
    private static final String SUCCESS = "Completed successfully";

    @GetMapping
    public List<GenreDTO> findAllGenres() {
        return service.findAllGenres();
    }

    @GetMapping("/find")
    public ResponseEntity<GenreDTO> findByName(@RequestParam String name) {
        GenreDTO genre = service.findByName(name);
        if (genre == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addGenre(@RequestBody Genre genre) {
        Optional<Genre> savedGenre = service.addGenre(genre);

        if (savedGenre.isPresent()) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("A title with that name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteGenreByName(@PathVariable String name) {
        if (service.deleteGenreByName(name)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no genre with that name", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update")
    ResponseEntity<String> updateGenreName(@RequestParam Long id, @RequestParam String name) {
        boolean updated = service.updateGenreName(id, name);
        if (updated) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Publisher to update not found", HttpStatus.NOT_FOUND);
        }
    }
}
