package com.company.Semaphores;

public class BinSemaphore
{
    private boolean available = true;

    synchronized public void acquire()
    {
        while (!this.available)
        {
            try
            { wait(); }
            catch (InterruptedException ignored)
            {}
        }
        this.available = false;
    }

    synchronized public void release()
    {
        if (!this.available)
        {
            this.available = true;
            notifyAll();
        }
    }
}
