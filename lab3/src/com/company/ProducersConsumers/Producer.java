package com.company.ProducersConsumers;

public class Producer implements Runnable
{
    private BoundedBuffer buffer;
    private int no;

    public Producer(BoundedBuffer buffer, int no)
    {
        this.buffer = buffer;
        this.no = no;
    }

    public void run()
    {
        for (int i = 0;  i < this.no; i++)
        {
            try
            {
                buffer.put(System.nanoTime());
            }
            catch (InterruptedException e)
            { e.printStackTrace(); }
        }
    }
}
