package com.company;

import java.util.concurrent.locks.ReentrantLock;

//Updated to now be interruptable
public class Uninteruptable {
    public static void main(String[] args) throws InterruptedException {

        //Use Re-entrant locks to allow the threads to be interrupted
        final ReentrantLock l1 = new ReentrantLock();
        final ReentrantLock l2 = new ReentrantLock();

        Thread t1 = new Thread() {
            public void run() {
                try {
                    l1.lockInterruptibly();
                    Thread.sleep(1000);
                    l2.lockInterruptibly();
                    System.out.println("Thread 1");

                } catch (InterruptedException e) {
                    System.out.println("t1 Interrupted");
                }
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                try {
                    l2.lockInterruptibly();
                    Thread.sleep(1000);
                    l1.lockInterruptibly();
                    System.out.println("Thread 2");
                } catch (InterruptedException e) {
                    System.out.println("t2 Interrupted");
                }
            }
        };

        t1.start();
        t2.start();
        Thread.sleep(2000);
        t1.interrupt();
        t2.interrupt();
        t1.join();
        t2.join();
        System.out.println("Thread 0");
    }
}
