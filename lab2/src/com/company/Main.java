package com.company;

import com.company.ShopSimulation.Shop;

public class Main
{
    // task 1: use binary semaphore in counter to synchronize it
    // counter should be 0
    private static void task1()
    {
        Counter counter = new Counter();

        new Thread(() ->
        {
            for (int i = 0 ; i < 100000000; i++)
            {
                counter.increment();
            }
        }).start();

        new Thread(() ->
        {
            for (int i = 0 ; i < 100000000; i++)
                counter.decrement();
        }).start();

        System.out.println("Synchronized counter with binary semaphores equals " + counter.value());
    }

    // task 2: simulate shop with carts (resource) and clients using them (consumers)
    private static void task2() throws InterruptedException
    {
        int cart_no = 5;
        int client_no = 10;

        Shop shop = new Shop(cart_no, client_no);
        shop.simulate();
    }

    public static void main(String[] args) throws InterruptedException
    {
        //task1();
        //task2();
    }
}
