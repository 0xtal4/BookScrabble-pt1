package test;

import test.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer{
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;
    private ExecutorService threadPool;

    public MyServer(int port, ClientHandler handler) {
        this.port = port;
        this.ch = handler;
        this.stop = false;
        this.threadPool = Executors.newCachedThreadPool();
    }

    public void start() {
        new Thread(() -> {
            try {
                this.runServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void close() {
        this.stop = true;
        this.threadPool.shutdown();
    }

    private void runServer() throws Exception {
        ServerSocket server = new ServerSocket(this.port);
        server.setSoTimeout(1000);

        while (!this.stop) {
            try {
                Socket aClient = server.accept();
                this.threadPool.execute(() -> handleClient(aClient));
            } catch (SocketTimeoutException ignored) {
            }
        }

        server.close();
    }

    private void handleClient(Socket clientSocket) {
        try {
            this.ch.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            // Handle or log the exception accordingly
        }
    }
}
