package com.company.ProducersConsumers;

public class Buffer
{
    private long message;
    private boolean empty = true;

    synchronized long take()
    {
        // must be while, not if, to make sure that right exception caused it
        while (empty)
        {
            try
            {  wait(); }
            catch (InterruptedException ignored)
            {}
        }

        empty = true;
        notifyAll();

        return message;
    }

    synchronized void put(long message)
    {
        while (!empty)
        {
            try
            { wait(); }
            catch (InterruptedException ignored)
            {}
        }

        empty = false;
        this.message = message;
        notifyAll();
    }
}
