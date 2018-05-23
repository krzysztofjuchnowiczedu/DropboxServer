package pl.com.juchnowicz.core;

import io.reactivex.subjects.PublishSubject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketConnectionFactory extends Thread{

    static final int PORT = 8081;
    private ServerSocketChannel serverSocketChannel;
    PublishSubject<Client> clientPublishSubject;
    ExecutorService executorService = Executors.newFixedThreadPool(10);


    public SocketConnectionFactory() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        clientPublishSubject = PublishSubject.create();
    }

    @Override
    public void run() {
        super.run();
        SocketChannel socketChannel = null;
        while (true) {
            try {
                socketChannel = serverSocketChannel.accept();
                if(socketChannel != null) {
                    System.out.println("New client");
                    Client client = new Client(socketChannel);
                    executorService.execute(client);
                    clientPublishSubject.onNext(client);
                }
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
        }
    }
}
