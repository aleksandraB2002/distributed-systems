package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import io.grpc.dbserver.*;

public class Server {
    private ServerSocket server;
    public static HashMap<Integer, String> database;
    private int port = 0;

    public Server(int pport) throws IOException {
        this.port = pport;
        server = new ServerSocket(port);
        database = new HashMap<>();
        database.put(0, "Test");
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                new Thread(new ClientHandlingInstance(socket)).start();
            } catch (IOException e) {
                throw new RuntimeException("Couldnt start Connection "+e);
            }
        }

    }
}



