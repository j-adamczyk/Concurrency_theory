package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    static int readerCount = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore access = new Semaphore(1);

    static class TraditionalReader implements Runnable
    {
        int iters;
        long avgTime;

        TraditionalReader(int iters)
        {
            this.iters = iters;
            this.avgTime = 0;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 0; i < iters; i++)
                {
                    long startTime = System.nanoTime();
                    mutex.acquire();
                    readerCount++;
                    if (readerCount == 1)
                        access.acquire();
                    mutex.release();
                    long endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;

                    // reading
                    //System.out.println("Reader " + Thread.currentThread().getName() + " reading");

                    startTime = System.nanoTime();
                    mutex.acquire();
                    readerCount--;
                    if (readerCount == 0)
                        access.release();
                    mutex.release();
                    endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;
                }
            }
            catch (InterruptedException ignored) { }

            this.avgTime /= iters;
        }
    }

    static class TraditionalWriter implements Runnable
    {
        int iters;
        long avgTime;

        TraditionalWriter(int iters)
        {
            this.iters = iters;
            this.avgTime = 0;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 0; i < iters; i++)
                {
                    long startTime = System.nanoTime();
                    access.acquire();
                    long endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;

                    //System.out.println("Writer " + Thread.currentThread().getName() + " started");
                    // writing
                    //System.out.println("Writer " + Thread.currentThread().getName() + " finished");

                    startTime = System.nanoTime();
                    access.release();
                    endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;
                }
            }
            catch (InterruptedException ignored) { }

            this.avgTime /= iters;
        }
    }

    static class PaperReader implements Runnable
    {
        int iters;
        int avgTime;

        PaperReader(int iters)
        {
            this.iters = iters;
            this.avgTime = 0;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 0; i < iters; i++)
                {
                    long startTime = System.nanoTime();
                    access.acquire();
                    long endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;

                    // reading
                    //System.out.println("Reader " + Thread.currentThread().getName() + " reading");

                    startTime = System.nanoTime();
                    access.release();
                    endTime = System.nanoTime();
                    avgTime += endTime - startTime;
                }
            }
            catch (InterruptedException ignored) { }

            this.avgTime /= iters;
        }
    }

    static class PaperWriter implements Runnable
    {
        int iters;
        int avgTime;

        PaperWriter(int iters)
        {
            this.iters = iters;
            this.avgTime = 0;
        }

        @Override
        public void run()
        {
            try
            {
                for (int i = 0; i < iters; i++)
                {
                    long startTime = System.nanoTime();
                    mutex.acquire();

                    for (int k = 0; k < readerCount; k++)
                        access.acquire();
                    long endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;

                    //System.out.println("Writer " + Thread.currentThread().getName() + " started");
                    // writing
                    //System.out.println("Writer " + Thread.currentThread().getName() + " finished");

                    startTime = System.nanoTime();
                    for (int k = 0; k < readerCount; k++)
                        access.release();

                    mutex.release();
                    endTime = System.nanoTime();
                    this.avgTime += endTime - startTime;
                }
            }
            catch (InterruptedException ignored) { }

            this.avgTime /= iters;
        }
    }

    private static void traditionalReadersWriters() throws InterruptedException
    {
        int threadNo = 1000;
        int iters = 100;

        List<TraditionalWriter> writers = new ArrayList<>();
        List<TraditionalReader> readers = new ArrayList<>();
        for (int i = 0; i < threadNo; i++)
        {
            writers.add(new TraditionalWriter(iters));
            readers.add(new TraditionalReader(iters));
        }

        List<Thread> writerThreads = new ArrayList<>();
        List<Thread> readerThreads = new ArrayList<>();
        for (int i = 0; i < threadNo; i++)
        {
            writerThreads.add(new Thread(writers.get(i)));
            readerThreads.add(new Thread(readers.get(i)));
        }

        for (int i = 0; i < threadNo; i++)
        {
            writerThreads.get(i).start();
            readerThreads.get(i).start();
        }

        for (Thread writer: writerThreads)
            writer.join();

        for (Thread reader: readerThreads)
            reader.join();

        try
        {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("traditional_writers.txt", true));
            for (TraditionalWriter writer: writers)
                fileWriter.append(String.valueOf(writer.avgTime)).append("\n");
            fileWriter.append("\n");
            fileWriter.close();
        }
        catch (IOException ignored)
        { }

        try
        {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("traditional_readers.txt", true));
            for (TraditionalReader reader: readers)
                fileWriter.append(String.valueOf(reader.avgTime)).append("\n");
            fileWriter.append("\n");
            fileWriter.close();
        }
        catch (IOException ignored)
        { }
    }

    private static void paperReadersWriters() throws InterruptedException
    {
        int threadNo = 1000;
        int iters = 100;

        List<PaperWriter> writers = new ArrayList<>();
        List<PaperReader> readers = new ArrayList<>();
        for (int i = 0; i < threadNo; i++)
        {
            writers.add(new PaperWriter(iters));
            readers.add(new PaperReader(iters));
        }

        List<Thread> writerThreads = new ArrayList<>();
        List<Thread> readerThreads = new ArrayList<>();
        for (int i = 0; i < threadNo; i++)
        {
            writerThreads.add(new Thread(writers.get(i)));
            readerThreads.add(new Thread(readers.get(i)));
        }

        for (int i = 0; i < threadNo; i++)
        {
            writerThreads.get(i).start();
            readerThreads.get(i).start();
        }

        for (Thread writer: writerThreads)
            writer.join();

        for (Thread reader: readerThreads)
            reader.join();

        try
        {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("paper_writers.txt", true));
            for (PaperWriter writer: writers)
                fileWriter.append(String.valueOf(writer.avgTime)).append("\n");
            fileWriter.append("\n");
            fileWriter.close();
        }
        catch (IOException ignored)
        { }

        try
        {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("paper_readers.txt", true));
            for (PaperReader reader: readers)
                fileWriter.append(String.valueOf(reader.avgTime)).append("\n");
            fileWriter.append("\n");
            fileWriter.close();
        }
        catch (IOException ignored)
        { }
    }

    public static void main(String[] args) throws InterruptedException
    {
	    traditionalReadersWriters();
        paperReadersWriters();
    }
}
