package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherNaive implements Runnable
{
    private int num;  // current philosopher number

    // for i-th philosopher:
    // left fork = i-th fork
    // right fork = (i+1)-th fork
    private ReentrantLock[] moving;
    private boolean[] taken;
    private Object[] forks;

    private Object leftFork = null;
    private Object rightFork = null;

    // waiting time - return value
    long time = 0;

    public PhilosopherNaive(int num, ReentrantLock[] moving, boolean[] taken, Object[] forks)
    {
        this.num = num;
        this.moving = moving;
        this.taken = taken;
        this.forks = forks;
    }

    private void think() throws InterruptedException
    {
        Thread.sleep(1);
    }

    private void getLeftFork(int i)
    {
        moving[i].lock();
            taken[i] = true;
            this.leftFork = forks[i];
        moving[i].unlock();
    }

    private void getRightFork(int i)
    {
        if (i == moving.length)
            i = 0;

        moving[i].lock();
            taken[i] = true;
            this.rightFork = forks[i];
        moving[i].unlock();
    }

    private void eat() throws InterruptedException
    {
        Thread.sleep(1);
    }

    private void returnLeftFork(int i)
    {
        moving[i].lock();
            taken[i] = false;
            this.leftFork = null;
        moving[i].unlock();
    }

    private void returnRightFork(int i)
    {
        if (i == moving.length)
            i = 0;

        moving[i].lock();
            taken[i] = false;
            this.rightFork = false;
        moving[i].unlock();
    }

    @Override
    public void run()
    {
        try
        {
            for (int i = 0; i < 100; i++)
            {
                think();

                long startWaiting = System.nanoTime();
                getLeftFork(num);
                getRightFork(num + 1);
                long stopWaiting = System.nanoTime();

                this.time += (stopWaiting - startWaiting);

                eat();

                returnLeftFork(num);
                returnRightFork(num + 1);
            }
        }
        catch (InterruptedException ignored)
        { }
    }
}
