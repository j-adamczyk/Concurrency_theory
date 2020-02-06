package com.company;

import com.company.Counter.Counter;
import com.company.Counter.ICounter;
import com.company.Counter.SynchronizedCounterLocks;
import com.company.Counter.SynchronizedCounterMethods;
import com.company.ProducersConsumers.Buffer;
import com.company.ProducersConsumers.Consumer;
import com.company.ProducersConsumers.Producer;

import java.util.ArrayList;

public class Main
{
    private static int iter_no = 100000000;

    // task 1: inc() and dec() counter 100 milion times; is it 0?
    // often it isn't
    private static void task1()
    {
        ICounter counter = new Counter();

        new Thread(() ->
        {
            for (int i = 0 ; i < iter_no; i++)
                counter.increment();
        }).start();

        new Thread(() ->
        {
            for (int i = 0 ; i < iter_no; i++)
                counter.decrement();
        }).start();

        System.out.println("Unsynchronized counter equals " + counter.getValue());
    }

    // task 2: add "synchronized" keyword to task 1
    // now Counter is 0
    private static void task22()
    {
        ICounter counter = new SynchronizedCounterMethods();

        new Thread(() ->
        {
            for (int i = 0 ; i < iter_no; i++)
                counter.increment();
        }).start();

        new Thread(() ->
        {
            for (int i = 0 ; i < iter_no; i++)
                counter.decrement();
        }).start();

        System.out.println("Synchronized counter equals " + counter.getValue());
    }

    // task 2: add "synchronized" keyword to task 1
    // now Counter is 0
    private static void task2()
    {
        ICounter counter = new SynchronizedCounterLocks();

        new Thread(() ->
        {
            for (int i = 0 ; i < iter_no; i++)
                counter.increment();
        }).start();

        new Thread(() ->
        {
            for (int i = 0 ; i < iter_no; i++)
                counter.decrement();
        }).start();

        System.out.println("Synchronized counter equals " + counter.getValue());
    }

    // task 3: producer/consumer pattern with 1-element buffer
    // synchronizes with Buffer monitor
    private static void task3() throws InterruptedException
    {
        Buffer buffer = new Buffer();
        int consumerNo = 2;
        int producerNo = 2;
        int messageNo = 10000;

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < producerNo; i++)
        {
            Thread newProducer = new Thread(new Producer(buffer, messageNo));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i < consumerNo; i++)
        {
            Thread newConsumer = new Thread(new Consumer(buffer, messageNo));
            newConsumer.start();
            threads.add(newConsumer);
        }

        for (Thread thread: threads)
        { thread.join(); }
    }

    public static void main(String[] args) throws InterruptedException
    {
        iter_no = 2147483647;

        //task1();

        /*
        long startTime = System.nanoTime();
        task2();
        System.out.println("Synchronized methods time: " + (System.nanoTime() - startTime));

        startTime = System.nanoTime();
        task22();
        System.out.println("Synchronized blocks time: " + (System.nanoTime() - startTime));
         */

        //task3();
    }
}
