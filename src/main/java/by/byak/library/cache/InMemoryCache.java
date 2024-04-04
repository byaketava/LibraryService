package by.byak.library.cache;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryCache<K, V> {
    private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();
    private static final int MAX_SIZE = 100;

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        if (cache.size() >= MAX_SIZE) {
            clear();
        }
        cache.put(key, value);
    }

    public void remove(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }
}
