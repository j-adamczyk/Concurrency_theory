package com.company.waiterSolution;

public class Waiter
{
    int semaphore = 0;
    int maxPhilosophersNo;

    public Waiter(int maxPhilosophersNo)
    {
        this.maxPhilosophersNo = maxPhilosophersNo;
    }

    public synchronized void goEat() throws InterruptedException
    {
        while (semaphore == maxPhilosophersNo)
            wait();
        semaphore++;
    }

    public synchronized void goThink()
    {
        if (semaphore > 0 )
            notify();
        semaphore--;
    }
}