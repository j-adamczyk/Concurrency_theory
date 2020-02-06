package com.company.TableSimulation;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter
{
    private Table table;

    private boolean[] isInQueue;

    private Lock[] pairLocks;
    private Condition[] pairConditions;

    private ReentrantLock firstInQueueLock;
    private ReentrantLock firstSeatLock;
    private ReentrantLock secondSeatLock;

    public Waiter(int pairNo)
    {
        table = new Table();

        int customerNo = 2 * pairNo;

        this.isInQueue = new boolean[customerNo];
        for (int i = 0; i < customerNo; i++)
            this.isInQueue[i] = false;

        this.pairLocks = new ReentrantLock[pairNo];
        this.pairConditions = new Condition[pairNo];
        for (int i = 0; i < pairNo; i++)
        {
            pairLocks[i] = new ReentrantLock();
            pairConditions[i] = pairLocks[i].newCondition();
        }

        this.firstInQueueLock = new ReentrantLock();
        this.firstSeatLock = new ReentrantLock();
        this.secondSeatLock = new ReentrantLock();
    }

    public Table requestTable(Client client) throws InterruptedException
    {
        int clientId = client.getId();
        int pairId = client.getPairId();
        int pairedClientId = client.getPairedClientId();

        isInQueue[clientId] = true;

        if (!isInQueue[pairedClientId])
        {
            pairLocks[pairId].lock();
            try
            {
                // wait for partner to arrive to queue
                while (!isInQueue[pairedClientId])
                    pairConditions[pairId].await();

                firstInQueueLock.lock();

                // wait for both places to be free
                firstSeatLock.lock();
                firstSeatLock.unlock();
                secondSeatLock.lock();
                secondSeatLock.unlock();

                // no longer waiting and have table
                isInQueue[clientId] = false;
                this.table.addClient(clientId);

                // inform partner about table
                pairConditions[pairId].signal();

                // take a seat
                firstSeatLock.lock();
            }
            finally
            {
                pairLocks[pairId].unlock();
            }
        }
        else
        {
            pairLocks[pairId].lock();
            try
            {
                // inform partner about arrival to the queue
                pairConditions[pairId].signal();
                pairConditions[pairId].await();

                // no longer waiting and have table
                isInQueue[clientId] = false;
                this.table.addClient(clientId);

                // take a seat
                secondSeatLock.lock();
            }
            finally
            {
                pairLocks[pairId].unlock();
            }
        }

        return this.table;
    }

    public void leaveTable(Client client)
    {
        this.table.removeClient(client.getId());

        ReentrantLock[] locks = new ReentrantLock[3];
        locks[0] = this.firstInQueueLock;
        locks[1] = this.firstSeatLock;
        locks[2] = this.secondSeatLock;

        for (ReentrantLock lock : locks)
            if (lock.isHeldByCurrentThread())
                lock.unlock();
    }
}