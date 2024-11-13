import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import io.grpc.dbserver.RPC_Request;
import io.grpc.dbserver.RPC_Response;
import io.grpc.dbserver.Operation;
import io.grpc.dbserver.DatabaseServiceOuterClass;

//telnet localhost *specified Port*

public class Server {
    private ServerSocket server;
    private Map<Integer, String> database;
    private int port = 0;
    
    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        database = new HashMap<>();
        database.put(0, "Test");
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
                System.out.println("Server started on port " + port);
                System.out.println("New connection from " + socket.getRemoteSocketAddress());
                new Thread(new ClientHandlingInstance(socket)).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


