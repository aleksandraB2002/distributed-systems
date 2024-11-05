import java.io.*;
import java.net.*;


public class sclient {

    public static void main(String[] args) throws IOException {
        try {

            String hostn = "stud.fh-wedel.de";
             int port = 8080;
             Socket socket = new Socket(hostn, port);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
              out.println("GET / HTTP/1.1");
              out.println("Host: " + hostn);
              out.println("");
              out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              String line;
              while ((line = in.readLine()) != null) {
                System.out.println(line);
              }

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}