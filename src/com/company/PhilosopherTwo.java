package com.company;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sellandb on 11/2/14.
 */
public class PhilosopherTwo extends Thread{
    private ReentrantLock leftChopstick, rightChopstick;
    private Random random;

    public PhilosopherTwo(ReentrantLock leftChopstick, ReentrantLock rightChopstick) {
        this.leftChopstick = leftChopstick; this.rightChopstick = rightChopstick;
        random = new Random();
    }

    public void run() {
        try {
            while(true){
                Thread.sleep(random.nextInt(1000)); //Think for a while
                leftChopstick.lock(); //Pickup the left Chopstick
                try{
                    //Try to acquire a lock on the right Chopstick, timeout after 1 sec
                    if(rightChopstick.tryLock(1000, TimeUnit.MILLISECONDS)){
                        try{
                            //Eat
                            Thread.sleep(random.nextInt(1000));
                        }
                        finally {
                            //Release Right Chopstick Lock
                            rightChopstick.unlock();
                        }
                    }
                    else { // Couldn't get the right chopstick, think again.
                    }
                }
                finally {
                    //Release Left chopstick lock
                    leftChopstick.unlock();
                }

            }
        }
        catch (InterruptedException e) {}
    }

}

