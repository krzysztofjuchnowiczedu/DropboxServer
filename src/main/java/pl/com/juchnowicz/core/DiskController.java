package pl.com.juchnowicz.core;

import pl.com.juchnowicz.model.ServerTask;

import java.util.concurrent.atomic.AtomicReference;

public class DiskController {

    public AtomicReference<Client> client;
    public AtomicReference<ServerTask> task;

    public DiskController() {
        client = new AtomicReference<>();
        task = new AtomicReference<>();
    }

    public void setTask(Client client, ServerTask serverTask){
        this.client.set(client);
        this.task.set(serverTask);
    }
}
