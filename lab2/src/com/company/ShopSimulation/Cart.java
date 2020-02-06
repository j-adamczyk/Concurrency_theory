package com.company.ShopSimulation;

// dummy class to represent objects used by consumers with limited resource
// (shop with limited number of carts)
// attributes and methods exist to verify work of shop simulation
public class Cart
{
    private final int number;

    Cart(int number)
    {
        this.number = number;
    }

    public int getNumber()
    {
        return number;
    }

    @Override
    public String toString()
    {
        return "Shopping cart " + this.number;
    }
}
