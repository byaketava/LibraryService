package by.byak.library.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CounterService {
    private final AtomicInteger count = new AtomicInteger(0);

    public synchronized void increment() {
        count.incrementAndGet();
    }

    public synchronized int get() {
        return count.get();
    }
}