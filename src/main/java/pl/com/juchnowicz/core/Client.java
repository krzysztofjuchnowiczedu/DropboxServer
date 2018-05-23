package pl.com.juchnowicz.core;

import io.reactivex.subjects.PublishSubject;
import pl.com.juchnowicz.core.db.DatabaseController;
import pl.com.juchnowicz.helpers.DisksFactory;
import pl.com.juchnowicz.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Client implements Runnable {
    private SocketChannel socketChannel;
    private String username;
    List<ServerTask> tasks = Collections.synchronizedList(new ArrayList<ServerTask>());
    public AtomicBoolean isBusy = new AtomicBoolean();

    public final static int FILE_SIZE = 10240000;
    PublishSubject<Message> messagePublishSubject;

    public Client(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        messagePublishSubject = PublishSubject.create();
        isBusy.set(false);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SocketChannel getSocket() {
        return socketChannel;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        handleMessages();
    }

    private void handleMessages() {
        if (socketChannel == null) {
            return;
        }
        while (socketChannel.isConnected()) {

            byte[] fileBuffer = new byte[FILE_SIZE];
            ByteArrayInputStream byteArrayInputStream = null;
            ObjectInputStream objectInputStream = null;
            ByteBuffer byteBuffer = ByteBuffer.wrap(fileBuffer);
            try {
                int numberOfBytes = socketChannel.read(byteBuffer);
                if (numberOfBytes > 0) {
                    byteArrayInputStream = new ByteArrayInputStream(fileBuffer);
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Message message = (Message) objectInputStream.readObject();
                    handleMessage(message);
                    System.out.println("Message received " + message.getUser());
                    //messagePublishSubject.onNext(message);
                }
            } catch (IOException e) {
                System.out.println("Connection error");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Parsing error");
                e.printStackTrace();
            } finally {
                try {
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    if (byteArrayInputStream != null) {
                        byteArrayInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(Message message){
        if(message.getMessageType() == MessageType.GET_FILES){
            ArrayList<FilesEntity> filesToDownload =  DatabaseController.getFilesForUser(message.getUser());
            for (FilesEntity entity: filesToDownload) {
                ServerTask task = new ServerTask(message.getUser(), entity.getDiskID(),false, entity.getFileUUID(), null);
                addTask(task);
                System.out.println("Task added to list!");
            }
        }
    }

    public void addTask(ServerTask task) {
        synchronized (tasks){
            if(!tasks.contains(task)){
                tasks.add(task);
            }
        }
    }

    public ServerTask getTaskForIDs(ArrayList<Integer> availableDisks){
        ServerTask task = null;
        synchronized (tasks){
            for (int i = 0; i < tasks.size(); i++) {
                ServerTask iteratorTask = tasks.get(i);
                if(iteratorTask.isUpload()){
                    if(task == null){
                        task = iteratorTask;
                    }
                } else {
                    if(availableDisks.contains(Integer.parseInt(iteratorTask.getDiskID()))){
                        task = iteratorTask;
                        break;
                    }
                }
            }
        }
        return task;
    }
}
