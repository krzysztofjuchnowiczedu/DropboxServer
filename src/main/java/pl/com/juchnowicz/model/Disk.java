package pl.com.juchnowicz.model;

import pl.com.juchnowicz.core.Client;
import pl.com.juchnowicz.core.db.DatabaseController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.UUID;
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
            if(client.get() != null && task.get() != null){
                ServerTask serverTask = this.task.get();
                if(serverTask.isUpload()){
                    //send message to client to send
                    //TODO check is flag waiting for file up
                    //send message to client to send file
                    //check in lopp when waiting flag will be down
                    //TODO is busy false
                    //client null
                } else {
                    FilesEntity entity = getEntityByUUID(serverTask.getFileUUID());
                    byte[] content = getFileContent(serverTask.getFileUUID());
                    CustomFile customFile = new CustomFile(serverTask.getFileUUID(), entity.getFilename(), entity.getCreationTime(), content, ".jpg");
                    Message message = new Message(MessageType.DOWNLOAD_FILE, customFile,serverTask.getUsername());
                    sleepToSendFile();
                    client.get().sendMessage(message);
                    resetClient();
                }
            }
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

    private void resetDisk() {
        this.client.get().removeTaskForFile(this.task.get().getFileUUID());
        this.client.set(null);
        this.task.set(null);
    }

    private FilesEntity getEntityByUUID(UUID fileUUID){
        return DatabaseController.getFileBy(fileUUID);
    }

    private byte[] getFileContent(UUID uuid){
        CustomFile customFile = null;
        String path = "disks/" + String.valueOf(diskID) + "/" + uuid;
        File file = new File(path);
        byte[] content = new byte[0];
        if(file.exists()){
            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    private void sleepToSendFile(){
        Random rand = new Random();
        int minimalNumerOfSecondsToSllep = 3;
        int maximalNumerOfSecondsToSllep = 15;

        int  numberOfSecondsToSleep = rand.nextInt(maximalNumerOfSecondsToSllep) + minimalNumerOfSecondsToSllep;

        try {
            Thread.sleep(numberOfSecondsToSleep * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resetClient(){
        if(this.client != null){
            this.client.get().removeTaskForFile(this.task.get().getFileUUID());
            this.client.get().isBusy.set(false);
            this.client.set(null);
        }
    }
}
