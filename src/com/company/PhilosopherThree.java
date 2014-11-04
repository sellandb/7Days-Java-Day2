package com.company;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sellandb on 11/3/14.
 */
public class PhilosopherThree extends Thread {
    private boolean eating;
    private PhilosopherThree left;
    private PhilosopherThree right;
    private ReentrantLock table;
    private Condition condition;
    private Random random;

    public PhilosopherThree(ReentrantLock table) {
        eating = false;
        this.table = table;
        condition = table.newCondition();
        random = new Random();
    }

    public void setLeft(PhilosopherThree left) {this.left = left; }
    public void setRight(PhilosopherThree right) {this.right = right; }

    public void run() {
        try {
            while(true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {}
    }

    private void eat() throws InterruptedException {
        table.lock();
        try {
            while (left.eating || right.eating)
                condition.await();
            eating = true;
        } finally {
            table.unlock();
        }
        Thread.sleep(1000);
        System.out.println("Philosopher Eats");
    }

    private void think() throws InterruptedException {
        table.lock();
        try {
            eating = false;
            left.condition.signal();
            right.condition.signal();
        } finally { table.unlock(); }
        Thread.sleep(1000);
        System.out.println("Philosopher Thinks");
    }

}
