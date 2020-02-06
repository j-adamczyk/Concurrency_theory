package com.company.Printers;

import com.company.Printers.Printer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintersMonitor
{
    private List<Printer> printers;
    private Lock lock = new ReentrantLock();
    private Lock getPrinterLock = new ReentrantLock();
    private Condition notEmptyCondition = lock.newCondition();

    public PrintersMonitor(int printersNo)
    {
        this.printers = new LinkedList<>();
        for (int i = 0; i < printersNo; i++)
            this.printers.add(new Printer(i));
    }

    public Printer take() throws InterruptedException
    {
        lock.lock();
        try
        {
            while (printers.isEmpty())
                notEmptyCondition.await();

            getPrinterLock.lock();
            Printer printer = printers.remove(0);
            getPrinterLock.unlock();

            return printer;
        }
        finally
        {
            lock.unlock();
        }
    }

    public void put(Printer printer) throws IllegalStateException
    {
        lock.lock();
        try
        {
            printers.add(printer);
            notEmptyCondition.signal();
        }
        finally
        {
            lock.unlock();
        }
    }
}