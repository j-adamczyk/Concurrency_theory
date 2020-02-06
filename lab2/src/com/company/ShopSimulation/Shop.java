package com.company.ShopSimulation;

import com.company.Semaphores.BinSemaphore;
import com.company.Semaphores.Semaphore;
import com.company.ShopSimulation.Cart;
import com.company.ShopSimulation.Client;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Shop
{
    private Semaphore freeCartsNo;
    private BinSemaphore movingCart;

    private int clientNo;

    private List<Cart> shoppingCarts;

    private int minShoppingTime = 1;
    private int maxShoppingTime = 3;

    public Shop(int cartsNo, int clientNo)
    {
        this.freeCartsNo = new Semaphore(cartsNo);
        this.movingCart = new BinSemaphore();
        this.clientNo = clientNo;
        this.shoppingCarts = new LinkedList<>();

        for (int i = 0; i < cartsNo; i++)
            this.shoppingCarts.add(new Cart(i));
    }

    Cart takeCart()
    {
        this.freeCartsNo.acquire();
        this.movingCart.acquire();
        Cart cart = this.shoppingCarts.remove(0);
        this.movingCart.release();
        return cart;
    }

    void returnCart(Cart cart)
    {
        this.movingCart.acquire();
        this.shoppingCarts.add(cart);
        this.movingCart.release();
        this.freeCartsNo.release();
    }

    public void simulate() throws InterruptedException
    {
        Thread[] clients = new Thread[this.clientNo];
        for (int i = 0 ; i < this.clientNo; i++)
        {
            final int iCopy = i;
            Thread newClient = new Thread(() ->
            {
                int shoppingTime = new Random().nextInt(
                        (maxShoppingTime - minShoppingTime) + 1) + minShoppingTime;
                Client client = new Client(this, shoppingTime, iCopy);
                client.run();
            });
            newClient.start();
            clients[i] = newClient;
        }

        for (Thread thread: clients)
        { thread.join(); }
    }
}
