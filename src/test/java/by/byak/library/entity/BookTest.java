package by.byak.library.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class BookTest {

  private Book book;
  private Author author;

  @BeforeEach
  public void setup() {
    book = new Book();
    book.setId(1L);
    book.setTitle("Test Book");

    author = new Author();
    author.setId(1L);
    author.setName("Test Author");

    book.setAuthor(author);
  }

  @Test
  public void testGetId() {
    assertEquals(1L, book.getId());
  }

  @Test
  public void testSetId() {
    book.setId(2L);
    assertEquals(2L, book.getId());
  }

  @Test
  public void testGetTitle() {
    assertEquals("Test Book", book.getTitle());
  }

  @Test
  public void testSetTitle() {
    book.setTitle("New Title");
    assertEquals("New Title", book.getTitle());
  }

  @Test
  public void testGetAuthor() {
    assertEquals(author, book.getAuthor());
  }

  @Test
  public void testSetAuthor() {
    Author newAuthor = new Author();
    newAuthor.setId(2L);
    newAuthor.setName("New Author");

    book.setAuthor(newAuthor);
    assertEquals(newAuthor, book.getAuthor());
  }

  @Test
  public void testGetGenres() {
    assertNotNull(book.getGenres());
    assertTrue(book.getGenres().isEmpty());
  }

  @Test
  public void testSetGenres() {
    Genre genre1 = new Genre();
    genre1.setId(1L);
    genre1.setName("Genre 1");

    Genre genre2 = new Genre();
    genre2.setId(2L);
    genre2.setName("Genre 2");

    List<Genre> genres = new ArrayList<>();
    genres.add(genre1);
    genres.add(genre2);

    book.setGenres(genres);

    assertEquals(2, book.getGenres().size());
    assertTrue(book.getGenres().contains(genre1));
    assertTrue(book.getGenres().contains(genre2));
  }
}
