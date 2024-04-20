package by.byak.library.dto.author;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthorNameDtoTest {

  @Test
  public void testAllArgsConstructor() {
    AuthorNameDto authorNameDto = new AuthorNameDto("John Doe");

    assertEquals("John Doe", authorNameDto.getName());
  }

  @Test
  public void testNoArgsConstructor() {
    AuthorNameDto authorNameDto = new AuthorNameDto();

    assertNotNull(authorNameDto);
    assertNull(authorNameDto.getName());
  }

  @Test
  public void testSetterGetter() {
    AuthorNameDto authorNameDto = new AuthorNameDto();

    authorNameDto.setName("John Doe");

    assertEquals("John Doe", authorNameDto.getName());
  }
}
