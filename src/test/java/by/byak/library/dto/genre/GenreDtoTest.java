package by.byak.library.dto.genre;

import by.byak.library.dto.book.BookTitleDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GenreDtoTest {

  @Test
  void testAllArgsConstructor() {
    List<BookTitleDto> books = new ArrayList<>();
    books.add(new BookTitleDto("Book 1"));
    books.add(new BookTitleDto("Book 2"));

    GenreDto genreDto = new GenreDto(1L, "Genre", books);

    assertEquals(1L, genreDto.getId());
    assertEquals("Genre", genreDto.getName());
    assertEquals(2, genreDto.getBooks().size());
    assertEquals("Book 1", genreDto.getBooks().get(0).getTitle());
    assertEquals("Book 2", genreDto.getBooks().get(1).getTitle());
  }

  @Test
  void testNoArgsConstructor() {
    GenreDto genreDto = new GenreDto();

    assertNotNull(genreDto);
    assertNull(genreDto.getId());
    assertNull(genreDto.getName());
    assertNull(genreDto.getBooks());
  }

  @Test
  void testSetterGetter() {
    GenreDto genreDto = new GenreDto();

    genreDto.setId(1L);
    genreDto.setName("Genre");

    List<BookTitleDto> books = new ArrayList<>();
    books.add(new BookTitleDto("Book 1"));
    books.add(new BookTitleDto("Book 2"));

    genreDto.setBooks(books);

    assertEquals(1L, genreDto.getId());
    assertEquals("Genre", genreDto.getName());
    assertEquals(2, genreDto.getBooks().size());
    assertEquals("Book 1", genreDto.getBooks().get(0).getTitle());
    assertEquals("Book 2", genreDto.getBooks().get(1).getTitle());
  }
}
