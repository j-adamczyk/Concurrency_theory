package com.company.Counter;

// uses synchronized methods
// should be slower than locks version
// because it allows only 1 thread at the time to modify c
public class SynchronizedCounterMethods implements ICounter
{
    private int value = 0;

    public SynchronizedCounterMethods()
    {
        this.value = 0;
    }

    public synchronized void increment()
    {
        this.value += 1;
    }

    public synchronized void decrement()
    {
        this.value -= 1;
    }

    public int getValue()
    {
        return value;
    }
}
