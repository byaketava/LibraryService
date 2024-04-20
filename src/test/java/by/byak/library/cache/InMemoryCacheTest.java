package by.byak.library.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCacheTest {

  private InMemoryCache<String, Integer> cache;

  @BeforeEach
  public void setup() {
    cache = new InMemoryCache<>();
  }

  @Test
  public void testGet() {
    cache.put("key", 1);

    Integer value = cache.get("key");

    assertEquals(1, value);
  }

  @Test
  public void testPut() {
    cache.put("key", 1);

    Integer value = cache.get("key");

    assertEquals(1, value);
  }

  @Test
  public void testRemove() {
    cache.put("key", 1);

    cache.remove("key");

    assertNull(cache.get("key"));
  }

  @Test
  public void testClear() {
    cache.put("key1", 1);
    cache.put("key2", 2);

    cache.clear();

    assertNull(cache.get("key1"));
    assertNull(cache.get("key2"));
  }
}
