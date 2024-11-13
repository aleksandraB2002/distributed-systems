import java.io.*;
import java.net.Socket;
import com.ghcp.logserver.LogMessageProtos.LogMessage;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("log.txt", true))) {

            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);

            if (bytesRead > 0) {
                LogMessage logMessage = LogMessage.parseFrom(in);

                String logEntry = String.format("%s %s %s %s %s%n",
                        logMessage.getTimestamp(),
                        logMessage.getCreator(),
                        logMessage.getLocation(),
                        logMessage.getSeverity(),
                        logMessage.getMessage());

                out.write(logEntry.getBytes());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
