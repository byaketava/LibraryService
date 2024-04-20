package by.byak.library.dto.book;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookTitleDtoTest {

  @Test
  public void testAllArgsConstructor() {
    BookTitleDto bookTitleDto = new BookTitleDto("Book Title");

    assertEquals("Book Title", bookTitleDto.getTitle());
  }

  @Test
  public void testNoArgsConstructor() {
    BookTitleDto bookTitleDto = new BookTitleDto();

    assertNotNull(bookTitleDto);
    assertNull(bookTitleDto.getTitle());
  }

  @Test
  public void testSetterGetter() {
    BookTitleDto bookTitleDto = new BookTitleDto();

    bookTitleDto.setTitle("Book Title");

    assertEquals("Book Title", bookTitleDto.getTitle());
  }
}
