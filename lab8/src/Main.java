import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main
{
    // task 1.1: add 100 double spaces to data file
    private static void task1_1()
    {
        String inputFilename = "data.txt";
        String outputFilename = "processed_data.txt";

        try
        {
            BufferedReader file = new BufferedReader(new FileReader(inputFilename));
            StringBuilder inputBuffer = new StringBuilder();
            String line;

            int i = 0;
            while ((line = file.readLine()) != null)
            {
                if (i < 100)
                    line = line.replaceFirst(" ", "  ");
                i += 1;
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();

            FileOutputStream fileOut = new FileOutputStream(outputFilename);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        }
        catch (Exception e)
        {
            System.out.println("Problem reading file.");
        }
    }

    // task 1.2: using one thread and Stream interface count words in the file
    private static long task1_2()
    {
        String filename = "processed_data.txt";

        long startTime = System.nanoTime();
        int counter = 0;
        try (Stream<String> stream = Files.lines(Paths.get(filename)))
        {
            counter = stream.mapToInt(line -> line.split("\\s+").length).sum();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();

        //System.out.println("Single-threaded version");
        //System.out.println("Words in file: " + counter + ", counting took " + (endTime - startTime) + " ns");
        return endTime - startTime;
    }

    // task 1.3: count words using parallel operations
    private static long task1_3()
    {
        String filename = "processed_data.txt";

        long startTime = System.nanoTime();
        int counter = 0;
        try (Stream<String> stream = Files.lines(Paths.get(filename)))
        {
            counter = stream.mapToInt(line -> line.split("\\s+").length).parallel().sum();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();

        //System.out.println("Parallel version");
        //System.out.println("Words in file: " + counter + ", counting took " + (endTime - startTime) + " ns");
        return endTime - startTime;
    }

    // task 2: sum large array of numbers using RecursiveTask
    private static void task2()
    {
        int size = 10000000;
        double[] data = new double[size];

        Random r = new Random();
        for (int i = 0; i < size; i++)
            data[i] = r.nextDouble();

        ArraySummer summer = new ArraySummer(data, 0, size - 1, 1000);
        double sum = summer.compute();
        System.out.println("Sum = " + sum);
    }

    // task 3, version 1: get data from REST API synchronously (single thread)
    private static void task3()
    {
        RESTGetter api = new RESTGetter();
        List<String> syncQuotes = new ArrayList<>();
        List<String> asyncQuotes = new ArrayList<>();
        long syncTime = 0;
        long asyncTime = 0;
        try
        {
            long startTime = System.nanoTime();
            syncQuotes = IntStream.range(0, 200)
                    .mapToObj(i -> api.getQuoteSync())
                    .collect(Collectors.toList());
            long endTime = System.nanoTime();

            syncTime = endTime - startTime;
            System.out.println("Synchronous time: " + (endTime - startTime) + " ns");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            ExecutorService executorService = Executors.newFixedThreadPool(50);
            CompletableFuture<?>[] futures = new CompletableFuture<?>[200];
            long startTime = System.nanoTime();
            for (int i = 0; i < 200; i++)
            {
                CompletableFuture<String> f = api.getQuoteAsync(executorService);
                futures[i] = f;
                // do things with futures in whenComplete()
                //f.whenComplete((s, e) -> System.out.println(""));
            }
            CompletableFuture.allOf(futures).get();
            long endTime = System.nanoTime();

            asyncTime = endTime - startTime;
            System.out.println("Asynchronous time: " + (endTime - startTime) + " ns");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println((float) asyncTime / syncTime);
        /*
        System.out.println("\nSynchronous quotes:");
        for (String quote: syncQuotes)
            System.out.println(quote);
        System.out.println("\nAsynchronous quotes:");
        for (String quote: asyncQuotes)
            System.out.println(quote);
         */
    }

    public static void main(String[] args)
    {
        //task1_1();

        // compare single threaded and parallel streams
        //long single = task1_2();
        //long parallel = task1_3();

        /*
        for (int i = 0; i < 100; i++)
        {
            long single = task1_2();
            long parallel = task1_3();
            System.out.println((float) parallel / single);
        }
         */

        //task2();

        task3();
    }
}
