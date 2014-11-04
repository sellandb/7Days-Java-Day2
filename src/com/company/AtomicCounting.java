package com.company;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sellandb on 11/3/14.
 */
public class AtomicCounting {
    public static void main(String[] args) throws InterruptedException {

        final AtomicInteger counter = new AtomicInteger();

        class CountingThread extends Thread {
            public void run() {
                for (int i = 0; i < 10000; ++i) {
                    counter.incrementAndGet();
                }
            }
        }

        CountingThread t1 = new CountingThread();
        CountingThread t2 = new CountingThread();

        t1.start(); t2.start();
        t1.join();t2.join();

        System.out.println(counter.get());
    }
}
