package com.company.Printers;

import java.util.Random;

public class Printer
{
    private int id;
    private static Random random = new Random();

    Printer(int id)
    { this.id = id; }

    void print(String message)
    {
        System.out.println("Printer number " + this.id + " printing message: '" + message + "'");
    }

    @Override
    public String toString()
    {
        return "Printer number " + id;
    }
}
