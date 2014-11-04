package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //Concurrent Sort Demo
        ConcurrentSortedList cs = new ConcurrentSortedList(1,100);
        cs.insert(50);
        cs.insert(75);
        cs.insert(25);
        System.out.println(cs.toString());

        //Philosopher Three Demo
        ReentrantLock table = new ReentrantLock();
        PhilosopherThree p1 = new PhilosopherThree(table);
        PhilosopherThree p2 = new PhilosopherThree(table);
        PhilosopherThree p3 = new PhilosopherThree(table);
        PhilosopherThree p4 = new PhilosopherThree(table);
        PhilosopherThree p5 = new PhilosopherThree(table);

        p1.setRight(p2);
        p1.setLeft(p5);
        p2.setRight(p3);
        p2.setLeft(p1);
        p3.setRight(p4);
        p3.setLeft(p2);
        p4.setRight(p5);
        p4.setLeft(p3);
        p5.setRight(p1);
        p5.setLeft(p4);

        try{
            p1.start();p2.start();p3.start();p4.start();p5.start();
            p1.join();p2.join();p3.join();p4.join();p5.join();
        } catch (InterruptedException e) {

        }


    }
}
