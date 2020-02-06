package com.company;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main
{
    /* task: test Mandelbrot calculation speed in terms of thread number in thread pool.
       Average time and standard deviation are calculated for:
       - 1 thread
       - thread number = cores number
       - thread number = 2 * cores number

       For every case above 3 variants are calculated:
       - as many workers (tasks) as threads
       - 10 times as many workers (tasks) as threads
       - 1 worker (task) per 1 pixel

       Calculations are done for 200, 1000, 5000 and 10000 max iterations.
    */
    private static void task1() throws ExecutionException, InterruptedException
    {
        int coreNo = Runtime.getRuntime().availableProcessors();
        int[] threadNumbers = {1, coreNo, 2 * coreNo};
        int[] maxIters = {200, 1000, 5000, 10000};

        final int runs = 10;
        final int width = 500;
        final int height = 500;
        final int zoom = 150;

        ArrayList<Result> results = new ArrayList<>();

        for (int maxIter: maxIters)
        {
            for (int threadNo: threadNumbers)
            {
                int[] taskNumbers = {threadNo, 10 * threadNo, width * height};
                for (int taskNo: taskNumbers)
                {
                    ArrayList<Long> times = new ArrayList<>();
                    for (int i = 0; i < runs; i++)
                    {
                        MandelbrotSimulator simulator = new MandelbrotSimulator(threadNo, taskNo, maxIter, height, width, zoom);
                        long startTime = System.nanoTime();
                        simulator.simulate();
                        times.add(System.nanoTime() - startTime);
                    }

                    int avgTime = (int) mean(times);
                    int stDev = (int) stDev(times, avgTime);

                    Result result = new Result(maxIter, threadNo, taskNo, avgTime, stDev);
                    results.add(result);
                }
            }
        }

        String toPrint = formatOutput(results);
        System.out.println(toPrint);

        try (PrintStream out = new PrintStream(new FileOutputStream("results.txt")))
        {
            out.print(toPrint);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static double mean(List<Long> times)
    {
        if (times.isEmpty())
            return 0;

        double sum = 0;
        for(Long num: times)
            sum += num;

        return sum / times.size();
    }

    private static double stDev(List<Long> times, double mean)
    {
        return Math.sqrt(1.0 * (times.stream().mapToDouble(i -> (i - mean) * (i - mean)).sum()) / (times.size()));
    }

    private static String formatOutput(List<Result> results)
    {
        StringBuilder resultString = new StringBuilder();
        Formatter fmt = new Formatter(resultString);
        fmt.format("   maxIter|  threadNo|    taskNo|   avgTime|     stDev\n");

        for (Result result: results)
        {
            fmt.format("%10d|", result.getMaxIter());
            fmt.format("%10d|", result.getThreadNo());
            fmt.format("%10d|", result.getTaskNo());
            fmt.format("%10d|", result.getAvgTime());
            fmt.format("%10d\n", result.getStDev());
        }

        return resultString.toString();
    }

    // show example Mandelbrot set
    private static void showExampleMandelbrotSet() throws ExecutionException, InterruptedException
    {
        MandelbrotSimulator simulator = new MandelbrotSimulator(4, 40, 200, 500, 500, 150);
        simulator.simulate();
        simulator.showImage();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
        task1();
        //showExampleMandelbrotSet();
    }
}
