import java.io.*;
import java.net.*;
import com.ghcp.logserver.LogMessageProtos.LogMessage;

public class Client {

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.sendLogMessage("localhost", 8080);
    }

    public void sendLogMessage(String host, int port) throws IOException {
        LogMessage logMessage = LogMessage.newBuilder()
                .setTimestamp("2024-11-13T10:52:00Z")
                .setCreator("Client1")
                .setLocation("Client-Side")
                .setSeverity(LogMessage.Severity.INFO)
                .setMessage("Log Nachricht!")
                .build();

        byte[] data = logMessage.toByteArray();

        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream()) {
            System.out.println("Verbindung zu Server hergestellt.");
            out.write(data);
            out.flush();
            System.out.println("Daten gesendet.");
        }
    }
}
