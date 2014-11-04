package com.company;

public class Main {

    public static void main(String[] args) {
        ConcurrentSortedList cs = new ConcurrentSortedList(1,100);
        cs.insert(50);
        cs.insert(75);
        cs.insert(25);

        System.out.println(cs.toString());
    }
}
