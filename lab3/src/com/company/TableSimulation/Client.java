package com.company.TableSimulation;

import java.util.Random;

public class Client
{
    private final int id;
    private final int pairId;
    private final int pairedClientId;

    public Client(int id, int pairId, int pairedClientId)
    {
        this.id = id;
        this.pairId = pairId;
        this.pairedClientId = pairedClientId;
    }

    int getId()
    {
        return id;
    }

    int getPairId()
    {
        return pairId;
    }

    int getPairedClientId()
    {
        return pairedClientId;
    }

    public void doOwnThings()
    {
        try
        {
            Thread.sleep(new Random().nextInt(2) * 1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // dummy method representing using resource shared with another thread
    public void eat(Table table)
    {
        try
        {
            Thread.sleep(new Random().nextInt(10) * 100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
