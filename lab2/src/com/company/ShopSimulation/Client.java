package com.company.ShopSimulation;

class Client implements Runnable
{
    private Shop shop;
    private int shoppingTime;
    private int clientId;

    Client(Shop shop, int shoppingTime, int clientId)
    {
        this.shop = shop;
        this.shoppingTime = shoppingTime;
        this.clientId = clientId;
    }

    public void run()
    {
        long enterTime = System.nanoTime();
        Cart cart = this.shop.takeCart();
        synchronized (System.out)
        {
            System.out.println("Client " + this.clientId + " got cart" + cart.toString());
        }
        long gotCartTime = System.nanoTime();
        try
        {
            // simulate using Cart object
            Thread.sleep(shoppingTime * 1000);
        }
        catch (InterruptedException e)
        { return; }

        long returnedCartTime = System.nanoTime();
        this.shop.returnCart(cart);
        synchronized (System.out)
        {
            System.out.println("Client " + clientId + " returned cart" + cart.toString());
        }
        long exitTime = System.nanoTime();

        synchronized (System.out)
        {
            System.out.println("Client " + this.clientId + " waited " + (gotCartTime - enterTime) +
                    "ns for cart, shopped for " + this.shoppingTime + "s and waited " +
                    (exitTime - returnedCartTime) + "ns for cart return.");
        }
    }
}
