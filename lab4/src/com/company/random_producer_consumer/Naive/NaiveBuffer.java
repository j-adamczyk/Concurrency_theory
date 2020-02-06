package com.company.random_producer_consumer.Naive;

import com.company.random_producer_consumer.Data;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer
{
    private int M;
    private int inBuffer;
    private Data[] data;
    private ReentrantLock lock;
    private Condition prod;
    private Condition cons;

    public NaiveBuffer(int M)
    {
        if (M <= 0)
            throw new IllegalArgumentException("M has to be > 0!");

        this.M = M;
        this.inBuffer = 0;
        this.data = new Data[2 * M];
        this.lock = new ReentrantLock();
        this.prod = lock.newCondition();
        this.cons = lock.newCondition();
    }

    public int getM()
    {
        return M;
    }

    public void put(int k, Data[] dataToPut)
    {
        assert k == dataToPut.length && k <= M;

        long startTime = System.nanoTime();
        lock.lock();

        while (2 * M - inBuffer < k)
        {
            try
            {
                prod.await();
            }
            catch (InterruptedException exc)
            {
                exc.printStackTrace();
            }
        }

        int putIndex = 0;
        for (int i = 0; i < 2 * M && putIndex < k; i++)
            if (data[i] == null)
            {
                data[i] = dataToPut[putIndex];
                putIndex++;
                inBuffer++;
            }

        cons.signal();
        lock.unlock();

        System.out.println("P " + (System.nanoTime() - startTime));
    }

    public Data[] get(int k)
    {
        assert 1 <= k && k <= M;

        long startTime = System.nanoTime();
        lock.lock();

        while (inBuffer < k)
        {
            try
            {
                cons.await();
            }
            catch (InterruptedException exc)
            {
                exc.printStackTrace();
            }
        }

        Data[] dataToReturn = new Data[k];
        int getIndex = 0;
        for (int i = 0; i < 2 * M && getIndex < k; i++)
            if (data[i] != null)
            {
                dataToReturn[getIndex] = data[i];
                data[i] = null;
                getIndex++;
                inBuffer--;
            }

        prod.signal();
        lock.unlock();

        System.out.println("C " + (System.nanoTime() - startTime));

        return dataToReturn;
    }
}
