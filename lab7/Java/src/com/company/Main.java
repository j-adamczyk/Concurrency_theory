package com.company;

import com.company.waiterSolution.Fork;
import com.company.waiterSolution.PhilosopherWithWaiter;
import com.company.waiterSolution.Waiter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Main
{
    // task 1 - naive philosophers
    private static void task1(int N) throws InterruptedException
    {
        List<Long> avgTimes = new ArrayList<>();

        for (int j = 0; j < 100; j++)
        {
            ReentrantLock[] moving = new ReentrantLock[N];
            boolean[] taken = new boolean[N];
            Object[] forks = new Object[N];

            for (int i = 0; i < N; i++)
            {
                moving[i] = new ReentrantLock();
                taken[i] = false;
                forks[i] = new Object();
            }

            PhilosopherNaive[] philosophers = new PhilosopherNaive[N];
            for (int i = 0; i < N; i++)
                 philosophers[i] = new PhilosopherNaive(i, moving, taken, forks);

            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < N; i++)
            {
                Thread newProducer = new Thread(philosophers[i]);
                newProducer.start();
                threads.add(newProducer);
            }

            for (Thread thread : threads)
                thread.join();

            long timesSum = 0;
            for (int i = 0; i < N; i++)
                timesSum += philosophers[i].time;

            long avgTime = timesSum / N;
            avgTimes.add(avgTime);
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("naivePhilosophers.txt", true));
            for (Long avgTime: avgTimes)
                writer.append(String.valueOf(avgTime)).append("\n");
            writer.append("\n");
            writer.close();
        }
        catch (IOException ignored)
        { }
    }

    // task 2 - greedy (unfair) philosophers
    private static void task2(int N) throws InterruptedException
    {
        List<Long> avgTimes = new ArrayList<>();

        for (int j = 0; j < 100; j++)
        {
            ReentrantLock[] taking = new ReentrantLock[N];
            Object[] forks = new Object[N];

            for (int i = 0; i < N; i++)
            {
                taking[i] = new ReentrantLock();
                forks[i] = new Object();
            }

            PhilosopherGreedy[] philosophers = new PhilosopherGreedy[N];
            for (int i = 0; i < N; i++)
                philosophers[i] = new PhilosopherGreedy(i, taking, forks);

            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < N; i++)
            {
                Thread newProducer = new Thread(philosophers[i]);
                newProducer.start();
                threads.add(newProducer);
            }

            for (Thread thread : threads)
                thread.join();

            long timesSum = 0;
            for (int i = 0; i < N; i++)
                timesSum += philosophers[i].time;

            long avgTime = timesSum / N;
            avgTimes.add(avgTime);
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("greedyPhilosophers.txt", true));
            for (Long avgTime: avgTimes)
                writer.append(String.valueOf(avgTime)).append("\n");
            writer.append("\n");
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // task 3 - naive philosophers
    private static void task3(int N) throws InterruptedException
    {
        List<Long> avgTimes = new ArrayList<>();

        for (int j = 0; j < 100; j++)
        {
            ReentrantLock[] taking = new ReentrantLock[N];
            boolean[] taken = new boolean[N];
            Object[] forks = new Object[N];

            for (int i = 0; i < N; i++)
            {
                taking[i] = new ReentrantLock();
                taken[i] = false;
                forks[i] = new Object();
            }

            PhilosopherAsynchronous[] philosophers = new PhilosopherAsynchronous[N];
            for (int i = 0; i < N; i++)
                philosophers[i] = new PhilosopherAsynchronous(i, taking, taken, forks);

            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < N; i++)
            {
                Thread newProducer = new Thread(philosophers[i]);
                newProducer.start();
                threads.add(newProducer);
            }

            for (Thread thread : threads)
                thread.join();

            long timesSum = 0;
            for (int i = 0; i < N; i++)
                timesSum += philosophers[i].time;

            long avgTime = timesSum / N;
            avgTimes.add(avgTime);
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("asynchronousPhilosophers.txt", true));
            for (Long avgTime: avgTimes)
                writer.append(String.valueOf(avgTime)).append("\n");
            writer.append("\n");
            writer.close();
        }
        catch (IOException ignored)
        { }
    }

    private static void task4(int N) throws InterruptedException
    {
        List<Long> avgTimes = new ArrayList<>();

        for (int j = 0; j < 100; j++)
        {
            Waiter waiter = new Waiter(N - 1);
            Fork[] forks = new Fork[N];

            for (int i = 0; i < N; i++)
                forks[i] = new Fork();

            PhilosopherWithWaiter[] philosophers = new PhilosopherWithWaiter[N];
            for (int i = 0; i < N; i++)
                philosophers[i] = new PhilosopherWithWaiter(N, i, waiter, forks);

            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < N; i++)
            {
                Thread newProducer = new Thread(philosophers[i]);
                newProducer.start();
                threads.add(newProducer);
            }

            for (Thread thread : threads)
                thread.join();

            long timesSum = 0;
            for (int i = 0; i < N; i++)
                timesSum += philosophers[i].time;

            long avgTime = timesSum / N;
            avgTimes.add(avgTime);
        }

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("waiterPhilosophers.txt", true));
            for (Long avgTime: avgTimes)
                writer.append(String.valueOf(avgTime)).append("\n");
            writer.append("\n");
            writer.close();
        }
        catch (IOException ignored)
        { }
    }

    public static void main(String[] args) throws InterruptedException
    {
        int[] philosopherNumbers = {5, 10, 20};
        for (int N: philosopherNumbers)
        {
            //task1(N);
            task2(N);
            //task3(N);
            //task4(N);
        }
    }
}
