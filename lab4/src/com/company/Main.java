package com.company;

import com.company.pipeline.PipelineData;
import com.company.pipeline.DataBuffer;
import com.company.pipeline.DataWorker;
import com.company.random_producer_consumer.Fair.FairBuffer;
import com.company.random_producer_consumer.Fair.FairConsumer;
import com.company.random_producer_consumer.Fair.FairProducer;
import com.company.random_producer_consumer.Naive.NaiveBuffer;
import com.company.random_producer_consumer.Naive.NaiveConsumer;
import com.company.random_producer_consumer.Naive.NaiveProducer;

public class Main
{
    /*
    Pipeline processing with buffer of size N, where:
    - process 0 is a producer
    - process N-1 is a consumer
    - processes 1, 2, ..., N-2 are processing the data consecutively
    */
    private static void task1() throws InterruptedException
    {
        int N = 100;
        int threadNo = 7;

        PipelineData[] data = new PipelineData[N];
        for (int i = 0; i < N; i++)
            data[i] = new PipelineData(i);

        DataBuffer dataBuffer = new DataBuffer(data);
        DataWorker[] workers = new DataWorker[threadNo];

        Thread[] threads = new Thread[threadNo];
        for (int i = 0; i < threadNo; i++)
        {
            workers[i] = new DataWorker(i, i - 1, dataBuffer);
            threads[i] = new Thread(workers[i]);
        }

        for (Thread thread : threads)
            thread.start();

        for (Thread thread: threads)
            thread.join();
    }

    /*
    Producers and consumers with 2*M buffer and random produced/consumed units in range [1, M].
    Unfair (random) synchronization.
    */
    private static void task2Naive() throws InterruptedException
    {
        int M = 10000;
        int prodConNo = 10;
        int iters = 1000;

        NaiveBuffer buffer = new NaiveBuffer(M);
        Thread[] producerThreads = new Thread[prodConNo];
        Thread[] consumerThreads = new Thread[prodConNo];

        for (int i = 0; i < prodConNo; i++)
            producerThreads[i] = new Thread(new NaiveProducer(buffer, i, iters));

        for (int i = 0; i < prodConNo; i++)
            consumerThreads[i] = new Thread(new NaiveConsumer(buffer, i, iters));

        for (Thread thread : producerThreads)
            thread.start();

        for (Thread thread : consumerThreads)
            thread.start();

    }

    /*
    Producers and consumers with 2*M buffer and random produced/consumed units in range [1, M].
    Fair synchronization.
    */
    private static void task2Fair() throws InterruptedException
    {
        int M = 1000;
        int prodConNo = 10;
        int iters = 1000;

        FairBuffer buffer = new FairBuffer(M);
        Thread[] producerThreads = new Thread[prodConNo];
        Thread[] consumerThreads = new Thread[prodConNo];

        for (int i = 0; i < prodConNo; i++)
            producerThreads[i] = new Thread(new FairProducer(buffer, i, iters));

        for (int i = 0; i < prodConNo; i++)
            consumerThreads[i] = new Thread(new FairConsumer(buffer, i, iters));

        for (Thread thread : producerThreads)
            thread.start();

        for (Thread thread : consumerThreads)
            thread.start();

    }

    public static void main(String[] args) throws InterruptedException
    {
        task1();
        //task2Naive();
        //task2Fair();
    }
}
