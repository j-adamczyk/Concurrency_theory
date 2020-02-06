package com.company.ProducersConsumers;

public class Producer implements Runnable
{
    private Buffer buffer;
    private int no;

    public Producer(Buffer buffer, int no)
    {
        this.buffer = buffer;
        this.no = no;
    }

    public void run()
    {
        for (int i = 0;  i < this.no; i++)
        {
            buffer.put(System.nanoTime());
        }
    }
}
