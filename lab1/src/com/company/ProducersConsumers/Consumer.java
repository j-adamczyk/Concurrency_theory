package com.company.ProducersConsumers;

public class Consumer implements Runnable
{
    private Buffer buffer;
    private int no;

    public Consumer(Buffer buffer, int no)
    {
        this.buffer = buffer;
        this.no = no;
    }

    public void run()
    {
        for (int i = 0;  i < this.no; i++)
        {
            long message = buffer.take();
            long time = System.nanoTime() - message;

            synchronized (System.out)
            { System.out.println(time); }
        }
    }
}
