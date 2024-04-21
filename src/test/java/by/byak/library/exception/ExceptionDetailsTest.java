package by.byak.library.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionDetailsTest {

  @Test
  void testExceptionDetailsConstructorWithArgs() {
    final Date timestamp = new Date();
    final String message = "Test message";
    final String details = "Test details";

    ExceptionDetails exceptionDetails = new ExceptionDetails(timestamp, message, details);

    assertEquals(timestamp, exceptionDetails.getTimestamp());
    assertEquals(message, exceptionDetails.getMessage());
    assertEquals(details, exceptionDetails.getDetails());
  }

  @Test
  void testExceptionDetailsConstructorWithoutArgs() {
    ExceptionDetails exceptionDetails = new ExceptionDetails();

    assertNotNull(exceptionDetails);
  }

  @Test
  void testGetterAndSetter() {
    final Date timestamp = new Date();
    final String message = "Test message";
    final String details = "Test details";

    ExceptionDetails exceptionDetails = new ExceptionDetails();
    exceptionDetails.setTimestamp(timestamp);
    exceptionDetails.setMessage(message);
    exceptionDetails.setDetails(details);

    assertEquals(timestamp, exceptionDetails.getTimestamp());
    assertEquals(message, exceptionDetails.getMessage());
    assertEquals(details, exceptionDetails.getDetails());
  }

  @Test
  void testEqualsAndHashCode() {
    ExceptionDetails exceptionDetails1 = new ExceptionDetails(new Date(), "Test message", "Test details");
    ExceptionDetails exceptionDetails2 = new ExceptionDetails(new Date(), "Test message", "Test details");
    ExceptionDetails exceptionDetails3 = new ExceptionDetails(new Date(), "Different message", "Different details");

    Assertions.assertEquals(exceptionDetails1, exceptionDetails2);
    Assertions.assertNotEquals(exceptionDetails1, exceptionDetails3);

    Assertions.assertEquals(exceptionDetails1.hashCode(), exceptionDetails2.hashCode());
    Assertions.assertNotEquals(exceptionDetails1.hashCode(), exceptionDetails3.hashCode());
  }

  @Test
  void testToString() {
    ExceptionDetails exceptionDetails = new ExceptionDetails(new Date(), "Test message", "Test details");
    String expectedToString = "ExceptionDetails(timestamp=" + exceptionDetails.getTimestamp() + ", message=Test message, details=Test details)";
    Assertions.assertEquals(expectedToString, exceptionDetails.toString());
  }
}
