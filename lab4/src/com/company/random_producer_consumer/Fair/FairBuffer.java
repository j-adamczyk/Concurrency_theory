package com.company.random_producer_consumer.Fair;

import com.company.random_producer_consumer.Data;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer
{
    private int M;
    private int inBuffer;
    private Data[] data;
    private ReentrantLock lock;
    private Condition first_prod, first_cons, rest_prod, rest_cons;
    private boolean first_prod_flag, first_cons_flag;

    public FairBuffer(int M)
    {
        assert M > 0;
        this.M = M;
        this.inBuffer = 0;
        this.data = new Data[2 * M];
        this.lock = new ReentrantLock();
        this.first_prod = lock.newCondition();
        this.first_cons = lock.newCondition();
        this.rest_prod = lock.newCondition();
        this.first_prod_flag = this.first_cons_flag = false;
        this.rest_cons = lock.newCondition();
    }

    public int getM()
    {
        return M;
    }

    public void put(int k, Data[] dataToPlace)
    {
        assert k == dataToPlace.length && k <= M;

        long startTime = System.nanoTime();
        lock.lock();

        try
        {
            if (first_prod_flag)
                rest_prod.await();

            first_prod_flag = true;

            while (2 * M - inBuffer < k)
                first_prod.await();

            int putIndex = 0;
            for (int i = 0; i < 2 * M && putIndex < k; i++)
            {
                if (data[i] == null)
                {
                    data[i] = dataToPlace[putIndex];
                    putIndex++;
                    inBuffer++;
                }
            }

            first_prod_flag = false;

            rest_prod.signal();
            first_cons.signal();
        }
        catch (InterruptedException exc)
        {
            exc.printStackTrace();
        }
        finally
        {
            lock.unlock();

            System.out.println("P " + (System.nanoTime() - startTime));
        }
    }

    public Data[] get(int k)
    {
        assert 1 <= k && k <= M;

        long startTime = System.nanoTime();
        lock.lock();
        try
        {
            if (first_cons_flag)
                rest_cons.await();

            first_cons_flag = true;

            while (inBuffer < k)
                first_cons.await();

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

            first_cons_flag = false;
            rest_cons.signal();
            first_prod.signal();

            return dataToReturn;
        }
        catch (InterruptedException exc)
        {
            exc.printStackTrace();
            return null;
        }
        finally
        {
            lock.unlock();

            System.out.println("C " + (System.nanoTime() - startTime));
        }

    }
}
