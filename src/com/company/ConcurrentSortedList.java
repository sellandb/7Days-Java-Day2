package com.company;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by sellandb on 11/3/14.
 */
public class ConcurrentSortedList {
    private class Node {
        int value;
        Node prev;
        Node next;
        ReentrantLock lock = new ReentrantLock();

        Node() {}

        Node(int value) {
            this.value = value;
        }

        Node (int value, Node prev, Node next){
            this.value = value; this.prev = prev; this.next = next;
        }
    }

    private final Node head;
    private final Node tail;

    public ConcurrentSortedList(int low, int high){
        head = new Node(low); tail = new Node(high);
        head.next = tail; tail.prev = head;
    }

    public void insert(int value){
        Node current = head;
        current.lock.lock();
        Node next = current.next;
        next.lock.lock();
        try{
            if(current.value > value){
                Node newNode = new Node(value);
                current.prev = newNode;
                newNode.next = current;
                return;
            }
            while(next.value < value && current != tail){
                current.lock.unlock();
                next.lock.unlock();
                current = next;
                next = current.next;
                current.lock.lock();
                next.lock.lock();
            }
            Node newNode = new Node(value, current, next);
            current.next = newNode;
            next.prev = newNode;
        }
        finally {
            current.lock.unlock();
            next.lock.unlock();
        }
    }

    public String toString(){
        Node current = head;
        ReentrantLock lock = current.lock;
        String output = "";
        lock.lock();
        try {
            while (current != tail) {
                output = output + current.value + " ";
                current = current.next;
                lock.unlock();
                lock = current.lock;
                lock.lock();
            }
        } finally {
            lock.unlock();
            output = output + current.value + " ";
        }
        return  output;
    }
}
