package com.company.random_producer_consumer.Fair;

import com.company.random_producer_consumer.Data;

import java.util.Random;

import static java.lang.Math.abs;

public class FairConsumer implements Runnable
{
    private FairBuffer buffer;
    private int ID;
    private int iters;

    public FairConsumer(FairBuffer buffer, int ID, int iters)
    {
        this.buffer = buffer;
        this.ID = ID;
        this.iters = iters;
    }

    @Override
    public void run()
    {
        Random random = new Random();

        for (int i = 0 ; i < this.iters; i++)
        {
            int numberOfElements = abs(random.nextInt()) % (this.buffer.getM() - 1) + 1;
            Data[] dataToGet = buffer.get(numberOfElements);
        }
    }

    @Override
    public String toString()
    {
        return "Consumer " + this.ID;
    }
}
