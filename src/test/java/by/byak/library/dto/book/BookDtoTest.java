package by.byak.library.dto.book;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import by.byak.library.dto.author.AuthorNameDto;
import by.byak.library.dto.genre.GenreNameDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class BookDtoTest {

  @Test
  void testAllArgsConstructor() {
    List<GenreNameDto> genres = new ArrayList<>();
    genres.add(new GenreNameDto("Genre 1"));
    genres.add(new GenreNameDto("Genre 2"));

    AuthorNameDto author = new AuthorNameDto("John Doe");

    BookDto bookDto = new BookDto(1L, "Book Title", genres, author);

    Assertions.assertEquals(1L, bookDto.getId());
    Assertions.assertEquals("Book Title", bookDto.getTitle());
    Assertions.assertEquals(2, bookDto.getGenres().size());
    Assertions.assertEquals("Genre 1", bookDto.getGenres().get(0).getName());
    Assertions.assertEquals("Genre 2", bookDto.getGenres().get(1).getName());
    Assertions.assertEquals("John Doe", bookDto.getAuthor().getName());
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    BookDto bookDto = new BookDto();

    // Assert
    assertNotNull(bookDto);
    assertNull(bookDto.getId());
    assertNull(bookDto.getTitle());
    assertNull(bookDto.getGenres());
    assertNull(bookDto.getAuthor());
  }

  @Test
  void testSetterGetter() {
    BookDto bookDto = new BookDto();

    bookDto.setId(1L);
    bookDto.setTitle("Book Title");

    List<GenreNameDto> genres = new ArrayList<>();
    genres.add(new GenreNameDto("Genre 1"));
    genres.add(new GenreNameDto("Genre 2"));

    bookDto.setGenres(genres);

    AuthorNameDto author = new AuthorNameDto("John Doe");
    bookDto.setAuthor(author);

    Assertions.assertEquals(1L, bookDto.getId());
    Assertions.assertEquals("Book Title", bookDto.getTitle());
    Assertions.assertEquals(2, bookDto.getGenres().size());
    Assertions.assertEquals("Genre 1", bookDto.getGenres().get(0).getName());
    Assertions.assertEquals("Genre 2", bookDto.getGenres().get(1).getName());
    Assertions.assertEquals("John Doe", bookDto.getAuthor().getName());
  }

  @Test
  void testEqualsAndHashCode() {
    BookDto book1 = new BookDto(1L, "Book 1", List.of(new GenreNameDto("Fiction")), new AuthorNameDto("John Doe"));
    BookDto book2 = new BookDto(1L, "Book 1", List.of(new GenreNameDto("Fiction")), new AuthorNameDto("John Doe"));
    BookDto book3 = new BookDto(2L, "Book 2", List.of(new GenreNameDto("Fantasy")), new AuthorNameDto("Jane Doe"));

    Assertions.assertEquals(book1, book2);
    Assertions.assertNotEquals(book1, book3);

    Assertions.assertEquals(book1.hashCode(), book2.hashCode());
    Assertions.assertNotEquals(book1.hashCode(), book3.hashCode());
  }

  @Test
  void testToString() {
    BookDto book = new BookDto(1L, "Test Book", List.of(new GenreNameDto("Fiction")), new AuthorNameDto("John Doe"));
    String expectedToString = "BookDto(id=1, title=Test Book, genres=[GenreNameDto(name=Fiction)], author=AuthorNameDto(name=John Doe))";
    Assertions.assertEquals(expectedToString, book.toString());
  }
}
