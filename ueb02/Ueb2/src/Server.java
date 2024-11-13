import java.io.*;
import java.net.*;


//telnet localhost *specified Port*

public class Server {
    private ServerSocket server;
    
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public static void main(String[] args) {
        try{
            Server server = new Server(8080);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Could not start server", e);
        }
    }

    public void start() {
        while (true) {
            //try establishing a connections and creating a new Thread
            try {
                Socket socket = server.accept();
                System.out.println("New connection from " + socket.getRemoteSocketAddress());
                new Thread(new ClientHandlingInstance(socket)).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


