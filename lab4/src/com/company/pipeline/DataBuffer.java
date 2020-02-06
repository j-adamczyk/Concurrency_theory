package com.company.pipeline;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DataBuffer
{
    private int dataSize;
    private PipelineData[] buffer;
    private int[] processIds;
    private ReentrantLock[] locks;
    private Condition[] conditions;

    public DataBuffer(PipelineData[] data)
    {
        if (data == null)
            throw new NullPointerException("Cannot create DataBuffer from null data.");
        this.buffer = data;
        this.dataSize = data.length;
        this.processIds = new int[dataSize];
        this.locks = new ReentrantLock[dataSize];
        this.conditions = new Condition[dataSize];
        for (int i = 0; i < dataSize; i++)
        {
            this.processIds[i] = -1;
            locks[i] = new ReentrantLock();
            conditions[i] = locks[i].newCondition();
        }
    }

    public PipelineData getData(int i, DataWorker worker)
    {
        if (i < 0 || i >= dataSize)
            throw new IndexOutOfBoundsException("Buffer index out of range [0, " + this.dataSize + "-1].");

        locks[i].lock();
        while (processIds[i] != worker.getPrevID())
        {
            try
            {
                conditions[i].await();
            }
            catch (InterruptedException exc)
            {
                exc.printStackTrace();
            }
        }

        return buffer[i];
    }

    public void finishProcessingData(int i, PipelineData pipelineData, DataWorker worker)
    {
        if (i < 0 || i >= dataSize)
            throw new IndexOutOfBoundsException("Buffer index out of range [0, " + this.dataSize + "-1].");

        if (!locks[i].isHeldByCurrentThread())
            throw new RuntimeException("Cannot unlock lock that is already unlocked.");

        buffer[i] = pipelineData;
        processIds[i] = worker.getID();
        conditions[i].signalAll();
        locks[i].unlock();
    }

    public int getDataSize()
    {
        return dataSize;
    }
}
