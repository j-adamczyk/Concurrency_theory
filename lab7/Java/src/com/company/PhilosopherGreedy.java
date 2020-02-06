package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherGreedy implements Runnable
{
    private int num;  // current philosopher number

    // for i-th philosopher:
    // left fork = i-th fork
    // right fork = (i+1)-th fork
    private ReentrantLock[] locks;
    private Object[] forks;

    private Object leftFork = null;
    private Object rightFork = null;

    // waiting time - return value
    long time = 0;

    public PhilosopherGreedy(int num, ReentrantLock[] locks, Object[] forks)
    {
        this.num = num;
        this.locks = locks;
        this.forks = forks;
    }

    private void think() throws InterruptedException
    {
        Thread.sleep(1);
    }


    private void eat() throws InterruptedException
    {
        Thread.sleep(1);
    }

    @Override
    public void run()
    {
        try
        {
            int i = 0;
            while (i < 100)
            {
                think();

                int left = num;
                int right = num + 1;
                if (num == locks.length - 1)
                    right = 0;

                long startWaiting = System.nanoTime();
                if (locks[left].tryLock())
                {
                    try
                    {
                        if (locks[right].tryLock())
                        {
                            try
                            {
                                long stopWaiting = System.nanoTime();
                                this.time += (stopWaiting - startWaiting);

                                this.leftFork = forks[left];
                                this.rightFork = forks[right];

                                eat();
                                i += 1;
                            }
                            finally
                            {
                                this.rightFork = null;
                                locks[right].unlock();
                            }
                        }
                    }
                    finally
                    {
                        this.leftFork = null;
                        locks[left].unlock();
                    }
                }
            }
        }
        catch (InterruptedException ignored)
        { }
    }
}