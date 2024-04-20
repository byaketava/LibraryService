package by.byak.library.controller;

import by.byak.library.dto.genre.GenreDto;
import by.byak.library.entity.Genre;
import by.byak.library.service.GenreService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenreController {
  private final GenreService service;
  private static final String SUCCESS = "Completed successfully";

  @GetMapping
  public ResponseEntity<List<GenreDto>> findAllGenres() {
    return ResponseEntity.ok(service.findAllGenres());
  }

  @GetMapping("/find")
  public ResponseEntity<GenreDto> findGenreByName(@RequestParam String name) {
    GenreDto genre = service.findGenreByName(name);
    return ResponseEntity.ok(genre);
  }

  @PostMapping("/add")
  public ResponseEntity<String> addGenre(@RequestBody Genre genre) {
    service.addGenre(genre);
    return new ResponseEntity<>(SUCCESS, HttpStatus.CREATED);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteGenreById(@RequestParam Long id) {
    service.deleteGenreById(id);
    return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
  }

  @PutMapping("/update")
  ResponseEntity<String> updateGenre(@RequestParam Long id,
                                     @RequestBody Genre genre) {
    service.updateGenre(id, genre);
    return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
  }
}
