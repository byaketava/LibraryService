package by.byak.library.dto.genre;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GenreNameDtoTest {

  @Test
  public void testAllArgsConstructor() {
    GenreNameDto genreNameDto = new GenreNameDto("Genre Name");

    assertEquals("Genre Name", genreNameDto.getName());
  }

  @Test
  public void testNoArgsConstructor() {
    GenreNameDto genreNameDto = new GenreNameDto();

    assertNotNull(genreNameDto);
    assertNull(genreNameDto.getName());
  }

  @Test
  public void testSetterGetter() {
    GenreNameDto genreNameDto = new GenreNameDto();

    genreNameDto.setName("Genre Name");

    assertEquals("Genre Name", genreNameDto.getName());
  }
}
