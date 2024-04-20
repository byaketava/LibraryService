package by.byak.library.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotFoundExceptionTest {

  @Test
  public void testConstructor() {
    NotFoundException exception = new NotFoundException("Test message");

    assertEquals("Test message", exception.getMessage());
  }
}