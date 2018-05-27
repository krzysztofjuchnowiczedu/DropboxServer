package pl.com.juchnowicz.helpers;

import pl.com.juchnowicz.core.Client;
import pl.com.juchnowicz.model.Disk;
import pl.com.juchnowicz.model.ServerTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskManager extends Thread {

    final List<Disk> disks;
    final List<Client> clients;

    public static final int NUMBER_OF_MILISECONDS_FOR_THRESD_SLEEP = 2000;

    public TaskManager(List<Disk> disks, List<Client> clients) {
        this.disks = disks;
        this.clients = clients;
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<Integer> availableDisks = new ArrayList<>();
            Iterator<Disk> iterator = disks.iterator();

            while (iterator.hasNext()) {
                Disk disk = iterator.next();
                if (disk.client.get() == null) {
                    availableDisks.add(disk.getDiskID());
                }
            }
            if (availableDisks.size() > 0) {
                synchronized (clients) {
                    Iterator<Client> clientsIterator = clients.iterator();

                    while (clientsIterator.hasNext()) {
                        if (availableDisks.size() > 0) {
                            Client client = clientsIterator.next();
                            if (!client.isBusy.get()) {
                                ServerTask task = client.getTaskForIDs(availableDisks);
                                if (task != null) {
                                    String diskIDText = task.getDiskID();
                                    Integer diskID = Integer.parseInt(task.getDiskID());
                                    if (diskIDText == null) {
                                        diskID = availableDisks.get(0);
                                    }
                                    client.isBusy.set(true);
                                    for (Disk disk: disks) {
                                        if(diskID == disk.getDiskID()){
                                            disk.setClientWithTask(client, task);
                                            break;
                                        }
                                    }
                                    availableDisks.remove(diskID);
                                }
                            }
                        } else {
                            try {
                                System.out.println("Number of all disks: " + disks.size());
                                System.out.println("Number of available disks: " + availableDisks.size());
                                System.out.println("------------");
                                Thread.sleep(NUMBER_OF_MILISECONDS_FOR_THRESD_SLEEP);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            System.out.println("Number of all disks: " + disks.size());
            System.out.println("Number of available disks: " + availableDisks.size());
            System.out.println("------------");
            try {
                Thread.sleep(NUMBER_OF_MILISECONDS_FOR_THRESD_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
