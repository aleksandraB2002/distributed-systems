import java.io.*;
import java.net.*;
import com.ghcp.logserver.LogMessageProtos.LogMessage;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server läuft...");

        while (true) {
            try (Socket clientSocket = serverSocket.accept();
                 InputStream in = clientSocket.getInputStream()) {

                System.out.println("Verbindung von Client empfangen.");

                // Empfangen der Daten
                byte[] data = in.readAllBytes();

                System.out.println("Daten empfangen.");

                // Deserialisieren der LogMessage
                LogMessage logMessage = LogMessage.parseFrom(data);

                // Ausgeben der LogMessage in der Konsole
                System.out.println("Empfangene LogMessage:");
                System.out.println("Zeitstempel: " + logMessage.getTimestamp());
                System.out.println("Ersteller: " + logMessage.getCreator());
                System.out.println("Standort: " + logMessage.getLocation());
                System.out.println("Schweregrad: " + logMessage.getSeverity());
                System.out.println("Nachricht: " + logMessage.getMessage());

                // Schreiben der LogMessage in eine Datei
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("logfile.txt", true))) {
                    writer.write("Zeitstempel: " + logMessage.getTimestamp() + "\n");
                    writer.write("Ersteller: " + logMessage.getCreator() + "\n");
                    writer.write("Standort: " + logMessage.getLocation() + "\n");
                    writer.write("Schweregrad: " + logMessage.getSeverity() + "\n");
                    writer.write("Nachricht: " + logMessage.getMessage() + "\n");
                    writer.write("-----\n"); // Trennung zwischen Log-Einträgen
                }
            }
        }
    }
}
