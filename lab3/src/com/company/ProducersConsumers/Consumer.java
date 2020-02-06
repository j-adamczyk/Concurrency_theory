package com.company.ProducersConsumers;

public class Consumer implements Runnable
{
    private BoundedBuffer buffer;
    private int no;

    public Consumer(BoundedBuffer buffer, int no)
    {
        this.buffer = buffer;
        this.no = no;
    }

    public void run()
    {
        for (int i = 0;  i < this.no; i++)
        {
            long message = -1;
            try
            { message = (long) buffer.take(); }
            catch (InterruptedException e)
            { e.printStackTrace(); }

            long time = System.nanoTime() - message;

            synchronized (System.out)
            { System.out.println(time); }
        }
    }
}
