package ru.ag78.api.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple time & iteration counter;
 * @author alexey
 *
 */
public class Counter {

    private String name = "";
    private AtomicInteger count = new AtomicInteger();
    private long startTime = System.currentTimeMillis();

    public Counter() {

    }

    public Counter(String name) {

        this.name = name;
    }

    public int increment() {

        return count.incrementAndGet();
    }

    public int getCount() {

        return count.get();
    }

    public long getDuration() {

        return System.currentTimeMillis() - startTime;
    }

    @Override
    public String toString() {

        return "Counter " + name + "[count=" + getCount() + ", duration=" + getDuration() + "]";
    }
}
