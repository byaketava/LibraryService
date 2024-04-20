package by.byak.library.dto.book;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookTitleDtoTest {

  @Test
  void testAllArgsConstructor() {
    BookTitleDto bookTitleDto = new BookTitleDto("Book Title");

    assertEquals("Book Title", bookTitleDto.getTitle());
  }

  @Test
  void testNoArgsConstructor() {
    BookTitleDto bookTitleDto = new BookTitleDto();

    assertNotNull(bookTitleDto);
    assertNull(bookTitleDto.getTitle());
  }

  @Test
  void testSetterGetter() {
    BookTitleDto bookTitleDto = new BookTitleDto();

    bookTitleDto.setTitle("Book Title");

    assertEquals("Book Title", bookTitleDto.getTitle());
  }
}
