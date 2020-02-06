package com.company.Printers;

import java.util.Random;

public class PrinterUser
{
    private Printer printer;

    public PrinterUser(Printer printer)
    {
        this.printer = printer;
    }

    public void print(String taskToPrint)
    {
        try
        {
            Thread.sleep(new Random().nextInt(2) * 1000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        this.printer.print(taskToPrint);
    }
}
