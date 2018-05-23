package pl.com.juchnowicz.core;

import io.reactivex.disposables.CompositeDisposable;
import pl.com.juchnowicz.helpers.DisksFactory;
import pl.com.juchnowicz.helpers.TaskManager;
import pl.com.juchnowicz.model.Disk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainService {

    List<Client> clients = Collections.synchronizedList(new ArrayList<Client>());
    ArrayList<Disk> disks;
    private CompositeDisposable compositeDisposable;
    private SocketConnectionFactory socketConnectionFactory;
    private TaskManager taskManager;
    ExecutorService executorService = Executors.newFixedThreadPool(5);


    public MainService() {
        disks = DisksFactory.getDisks(5);
        taskManager = new TaskManager(disks, clients);
        compositeDisposable = new CompositeDisposable();
        try {
            socketConnectionFactory = new SocketConnectionFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initObservables();
        startThreads();
    }

    private void initObservables(){
        compositeDisposable.add(socketConnectionFactory.clientPublishSubject.subscribe(client -> {
            synchronized (clients){
                clients.add(client);
            }
        }));
    }

    private void startThreads(){
        executorService.execute(disks.get(0));
        executorService.execute(disks.get(1));
        executorService.execute(disks.get(2));
        executorService.execute(disks.get(3));
        executorService.execute(disks.get(4));

        socketConnectionFactory.start();
        taskManager.start();
    }

    // disk threads
    // tasks queues
    // load balancer
}
