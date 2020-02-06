package com.company.Semaphores;

public class Semaphore
{
    private int value;

    public Semaphore(int initValue)
    {
        if (initValue > 0)
            this.value = initValue;
        else
            throw new IllegalArgumentException("initValue should be > 0!");
    }

    public void acquire()
    {
        synchronized (this)
        {
            while(value == 0)
            {
                try
                { wait(); }
                catch (InterruptedException e)
                { e.printStackTrace(); }
            }
            this.value-=1;
        }
        notifyAll();
    }

    public void release()
    {
        synchronized (this)
        { value += 1; }
        notifyAll();
    }
}
