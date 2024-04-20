package by.byak.library.exception;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExceptionDetailsTest {

  @Test
  void testExceptionDetailsConstructorWithArgs() {
    Date timestamp = new Date();

    ExceptionDetails exceptionDetails =
        new ExceptionDetails(timestamp, "Test message", "Test details");

    assertEquals(timestamp, exceptionDetails.getTimestamp());
    assertEquals("Test message", exceptionDetails.getMessage());
    assertEquals("Test details", exceptionDetails.getDetails());
  }

  @Test
  void testExceptionDetailsConstructorWithoutArgs() {
    ExceptionDetails exceptionDetails = new ExceptionDetails();

    assertNull(exceptionDetails.getTimestamp());
    assertNull(exceptionDetails.getMessage());
    assertNull(exceptionDetails.getDetails());
  }

  @Test
  void testExceptionDetailsSetters() {
    ExceptionDetails exceptionDetails = new ExceptionDetails();

    Date timestamp = new Date();
    exceptionDetails.setTimestamp(timestamp);
    exceptionDetails.setMessage("Test message");
    exceptionDetails.setDetails("Test details");

    assertEquals(timestamp, exceptionDetails.getTimestamp());
    assertEquals("Test message", exceptionDetails.getMessage());
    assertEquals("Test details", exceptionDetails.getDetails());
  }
}
