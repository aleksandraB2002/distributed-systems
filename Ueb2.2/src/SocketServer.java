import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                        String line;
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                            if (line.isEmpty()) {
                                break;
                            }
                        }

                        String httpResponse = "HTTP/1.1 200 OK\r\n\r\nHI!";
                        out.println(httpResponse);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
