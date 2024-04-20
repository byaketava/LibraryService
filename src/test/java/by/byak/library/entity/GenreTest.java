package by.byak.library.entity;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

  private Genre genre;
  private Book book1;
  private Book book2;

  @BeforeEach
  public void setup() {
    genre = new Genre();
    genre.setId(1L);
    genre.setName("Test Genre");

    book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Book 1");

    book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Book 2");

    List<Book> books = new ArrayList<>();
    books.add(book1);
    books.add(book2);

    genre.setBooks(books);
  }

  @Test
  public void testGetId() {
    assertEquals(1L, genre.getId());
  }

  @Test
  public void testSetId() {
    genre.setId(2L);
    assertEquals(2L, genre.getId());
  }

  @Test
  public void testGetName() {
    assertEquals("Test Genre", genre.getName());
  }

  @Test
  public void testSetName() {
    genre.setName("New Genre");
    assertEquals("New Genre", genre.getName());
  }

  @Test
  public void testGetBooks() {
    assertEquals(2, genre.getBooks().size());
    assertTrue(genre.getBooks().contains(book1));
    assertTrue(genre.getBooks().contains(book2));
  }

  @Test
  public void testSetBooks() {
    Book newBook = new Book();
    newBook.setId(3L);
    newBook.setTitle("Book 3");

    List<Book> books = new ArrayList<>();
    books.add(newBook);

    genre.setBooks(books);

    assertEquals(1, genre.getBooks().size());
    assertTrue(genre.getBooks().contains(newBook));
  }
}
