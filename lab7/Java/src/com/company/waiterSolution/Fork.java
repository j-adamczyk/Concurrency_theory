package com.company.waiterSolution;

public class Fork {
    boolean lock = false;

    public synchronized void take() throws InterruptedException
    {
        while (lock)
            wait();
        lock = true;
    }

    public synchronized void put()
    {
        if (lock)
            notify();
        lock = false;
    }
}