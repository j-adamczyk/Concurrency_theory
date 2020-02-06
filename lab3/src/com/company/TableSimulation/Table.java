package com.company.TableSimulation;

import java.util.HashMap;
import java.util.Map;

// dummy class representing object simultaneously used by 2 Clients (threads)
// with access synchronized by Waiter monitor
public class Table
{
    private Map<Integer, Integer> clients;

    Table()
    {
        this.clients = new HashMap<>();
    }

    synchronized void addClient(int clientId)
    {
        this.clients.put(clientId, clientId);
        synchronized (System.out)
        {
            System.out.println(toString());
        }
    }

    synchronized void removeClient(int clientId)
    {
        this.clients.remove(clientId);
        synchronized (System.out)
        {
            System.out.println(toString());
        }
    }

    @Override
    public String toString()
    {
        String result = "Clients sitting at the table: ";
        for (Integer clientId : this.clients.values())
            result += clientId + " ";

        return result;
    }
}
