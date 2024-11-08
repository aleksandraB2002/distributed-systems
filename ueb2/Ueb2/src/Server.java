import java.io.*;
import java.net.*;

//telnet localhost *specified Port*

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4999, 0, InetAddress.getByName("127.0.0.1"));
        Socket s = serverSocket.accept();
        System.out.println("Client connected");
    }
}
