package com.company;

import com.company.Printers.Printer;
import com.company.Printers.PrinterUser;
import com.company.Printers.PrintersMonitor;
import com.company.ProducersConsumers.BoundedBuffer;
import com.company.ProducersConsumers.Consumer;
import com.company.ProducersConsumers.Producer;
import com.company.TableSimulation.Client;
import com.company.TableSimulation.Table;
import com.company.TableSimulation.Waiter;

import java.util.ArrayList;

public class Main
{
    // task 1: producer/consumer pattern with bounded buffer
    // synchronizes with Buffer monitor
    private static void task1() throws InterruptedException
    {
        BoundedBuffer buffer = new BoundedBuffer(2);
        int consumerNo = 2;
        int producerNo = 2;
        int messageNo = 10000;

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < producerNo; i++)
        {
            Thread newProducer = new Thread(new Producer(buffer, messageNo));
            newProducer.start();
            threads.add(newProducer);
        }

        for (int i = 0; i < consumerNo; i++)
        {
            Thread newConsumer = new Thread(new Consumer(buffer, messageNo));
            newConsumer.start();
            threads.add(newConsumer);
        }

        for (Thread thread: threads)
        { thread.join(); }
    }

    /* task 2: N threads uses M printers (N > M). Thread:
    forever
    {
        createTaskToPrint();
        printerNo = printerMonitor.take();
        print(printerNo);
        printerMonitor.put(printerNo);
    }
    Use only lock, condition, await, signal from java.util.concurrent.locks
     */
    private static void task2() throws InterruptedException
    {
        int threadNo = 3;
        int printerNo = 5;
        PrintersMonitor printersMonitor = new PrintersMonitor(printerNo);

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadNo; i++)
        {
            Thread newThread = new Thread(() ->
            {
                for (int j = 0 ;; j++)
                {
                    String taskToPrint = Integer.toString(j);
                    try
                    {
                        Printer printer = printersMonitor.take();
                        PrinterUser user = new PrinterUser(printer);
                        user.print(taskToPrint);
                        printersMonitor.put(printer);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            newThread.start();
            threads.add(newThread);
        }

        for (Thread thread: threads)
        { thread.join(); }
    }

    /*
    Waiter monitor synchronizes use of a two person table, used by N pairs of people.
    For person from j-th pair:
    forever
    {
        doOwnThings();
        Waiter.requestTable();
        eat();
        Waiter.leaveTable();
    }
    Table is given to pair of people when they both request it.
    Table doesn't need to be freed by both people simultaneously.
     */
    private static void task3() throws InterruptedException
    {
        int pairNo = 3;
        ArrayList<Client> clients = new ArrayList<>(pairNo * 2);
        for (int id = 0; id < pairNo; id += 2)
        {
            int pairId = id / 2;
            int id2 = id + 1;

            Client client = new Client(id, pairId, id2);
            Client client2 = new Client(id2, pairId, id);
            clients.add(client);
            clients.add(client2);
        }

        Waiter waiter = new Waiter(pairNo);

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < pairNo; i += 2)
        {
            Client client = clients.get(i);
            Client client2 = clients.get(i+1);

            Thread newThread = new Thread(() ->
            {
                while (true)
                {
                    client.doOwnThings();
                    try
                    {
                        Table table = waiter.requestTable(client);
                        client.eat(table);
                        waiter.leaveTable(client);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            newThread.start();
            threads.add(newThread);

            Thread newThread2 = new Thread(() ->
            {
                while (true)
                {
                    client2.doOwnThings();
                    try
                    {
                        Table table = waiter.requestTable(client2);
                        client2.eat(table);
                        waiter.leaveTable(client2);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            newThread2.start();
            threads.add(newThread2);
        }

        for (Thread thread: threads)
        { thread.join(); }
    }

    public static void main(String[] args) throws InterruptedException
    {
        //task1();
        //task2();
        //task3();
    }
}
