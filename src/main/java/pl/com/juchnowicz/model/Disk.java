package pl.com.juchnowicz.model;

import pl.com.juchnowicz.core.Client;

import java.util.concurrent.atomic.AtomicReference;

public class Disk implements Runnable {

    private int diskID;
    public AtomicReference<Client> client;
    public AtomicReference<ServerTask> task;
    public static final int NUMBER_OF_MILISECONDS_FOR_THRESD_SLEEP = 3000;


    public Disk(int diskID) {
        this.diskID = diskID;
        client = new AtomicReference<>();
        task = new AtomicReference<>();

    }

    @Override
    public void run() {
        while (true){
            System.out.println("Disk tick: " + diskID);
            try {
                Thread.sleep(NUMBER_OF_MILISECONDS_FOR_THRESD_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getDiskID() {
        return diskID;
    }

    public void setClientWithTask(Client client, ServerTask serverTask){
        this.client.set(client);
        this.task.set(serverTask);
    }
}
