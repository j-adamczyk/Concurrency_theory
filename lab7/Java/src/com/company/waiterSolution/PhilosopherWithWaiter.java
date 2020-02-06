package com.company.waiterSolution;

public class PhilosopherWithWaiter implements Runnable
{
    int N;
    int num;
    Fork[] forks;
    Waiter waiter;

    // waiting time - return value
    public long time = 0;

    public PhilosopherWithWaiter(int N, int num, Waiter waiter, Fork[] forks)
    {
        this.N = N;
        this.num = num;
        this.waiter = waiter;
        this.forks = forks;
    }

    private void think() throws InterruptedException
    {
        Thread.sleep(1);
    }

    private void eat() throws InterruptedException
    {
        Thread.sleep(1);
    }

    @Override
    public void run()
    {
        try
        {
            for (int i = 0; i < 100; i++)
            {
                think();

                try
                {
                    long startWaiting = System.nanoTime();
                    waiter.goEat();

                    forks[num].take();
                    forks[(num + 1) % N].take();
                    long stopWaiting = System.nanoTime();
                    this.time += (stopWaiting - startWaiting);
                }
                catch (InterruptedException ignored)
                { }

                eat();

                forks[num].put();
                forks[(num + 1) % N].put();

                waiter.goThink();
            }
        }
        catch (InterruptedException ignored)
        { }
    }

}
