import java.io.*;
import java.net.Socket;

public class ClientHandlingInstance implements Runnable {
    Socket socket;

    public ClientHandlingInstance(Socket pSocket) throws IOException {
        this.socket = pSocket;
    }

    @Override
    public void run(){
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            int length = in.readInt();
            byte[] buffer = new byte[length];
            in.readFully(buffer);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}