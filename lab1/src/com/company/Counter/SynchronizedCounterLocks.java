package com.company.Counter;

/* uses locks for synchronization
 * should be faster than synchronized methods version
 * because it allows 2 threads at the time to increment and decrement c
 * it's possible, because it returns difference as result
 */
public class SynchronizedCounterLocks implements ICounter
{
    final private Object lock1 = new Object();
    final private Object lock2 = new Object();
    private int c1 = 0;
    private int c2 = 0;

    public void increment()
    {
        synchronized (this.lock1)
        { c1 += 1; }
    }

    public void decrement()
    {
        synchronized (this.lock2)
        { c2 -= 1; }
    }

    public int getValue()
    {
        synchronized (this.lock1)
        {
            synchronized (this.lock2)
            {
                return this.c1 + this.c2;
            }
        }
    }
}
