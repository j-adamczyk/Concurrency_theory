package com.company.random_producer_consumer;

public class Data
{
    private int ID;

    public Data(int ID)
    {
        this.ID = ID;
    }

    public Data()
    {
        this(0);
    }

    @Override
    public String toString()
    {
        return "Data ID=" + ID;
    }
}
