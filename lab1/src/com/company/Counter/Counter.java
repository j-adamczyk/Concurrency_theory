package com.company.Counter;

public class Counter implements ICounter
{
    private int value;

    public Counter()
    {
        this.value = 0;
    }

    @Override
    public void increment()
    {
        this.value += 1;
    }

    @Override
    public void decrement()
    {
        this.value -= 1;
    }

    @Override
    public int getValue()
    {
        return this.value;
    }
}
