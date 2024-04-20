package by.byak.library.entity;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

  private Author author;

  @BeforeEach
  public void setup() {
    author = new Author();
    author.setId(1L);
    author.setName("Test Author");
  }

  @Test
  void testGetId() {
    assertEquals(1L, author.getId());
  }

  @Test
  void testSetId() {
    author.setId(2L);
    assertEquals(2L, author.getId());
  }

  @Test
  void testGetName() {
    assertEquals("Test Author", author.getName());
  }

  @Test
  void testSetName() {
    author.setName("New Author");
    assertEquals("New Author", author.getName());
  }

  @Test
  void testGetBooks() {
    assertNotNull(author.getBooks());
    assertTrue(author.getBooks().isEmpty());
  }

  @Test
  void testSetBooks() {
    Book book1 = new Book();
    book1.setId(1L);
    book1.setTitle("Book 1");

    Book book2 = new Book();
    book2.setId(2L);
    book2.setTitle("Book 2");

    List<Book> books = new ArrayList<>();
    books.add(book1);
    books.add(book2);

    author.setBooks(books);

    assertEquals(2, author.getBooks().size());
    assertTrue(author.getBooks().contains(book1));
    assertTrue(author.getBooks().contains(book2));
  }
}
