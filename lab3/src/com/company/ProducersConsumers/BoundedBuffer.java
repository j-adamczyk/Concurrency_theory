package com.company.ProducersConsumers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// copied from Java documentation for task 1
public class BoundedBuffer
{
    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final Object[] items;
    private int putptr, takeptr, count;

    public BoundedBuffer(int bufferSize)
    {
        this.items = new Object[bufferSize];
    }

    Object take() throws InterruptedException
    {
        lock.lock();
        try
        {
            while (count == 0)
                notEmpty.await();

            Object x = items[takeptr];
            if (++takeptr == items.length)
                takeptr = 0;

            count -= 1;
            notFull.signal();

            return x;
        }
        finally
        {
            lock.unlock();
        }
    }

    void put(Object x) throws InterruptedException
    {
        lock.lock();
        try
        {
            while (count == items.length)
                notFull.await();

            items[putptr] = x;
            if (++putptr == items.length)
                putptr = 0;

            count += 1;
            notEmpty.signal();
        }
        finally
        {
            lock.unlock();
        }
    }
}
