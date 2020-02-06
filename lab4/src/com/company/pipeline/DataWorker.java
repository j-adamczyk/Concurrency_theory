package com.company.pipeline;

import static java.lang.Math.abs;
import java.util.Random;

public class DataWorker implements Runnable
{
    private int ID;
    private int prevID;
    private int dataSize;
    private DataBuffer dataBuffer;

    public DataWorker(int ID, int prevID, DataBuffer dataBuffer)
    {
        this.ID = ID;
        this.prevID = prevID;
        this.dataBuffer = dataBuffer;
        this.dataSize = dataBuffer.getDataSize();
    }

    public int getID()
    {
        return ID;
    }

    public int getPrevID()
    {
        return prevID;
    }

    public void run()
    {
        int processingTime = abs((new Random()).nextInt()) % 700;
        PipelineData pipelineData;
        for (int i = 0; i < this.dataSize; i++)
        {
            pipelineData = this.dataBuffer.getData(i, this);
            try
            {
                Thread.sleep(processingTime);
            }
            catch (InterruptedException exc)
            {
                exc.printStackTrace();
            }
            pipelineData.incrementIteration();
            this.dataBuffer.finishProcessingData(i, pipelineData, this);
            System.out.println("Worker " + this.ID + " finished task " + pipelineData.toString());
        }
    }
}
