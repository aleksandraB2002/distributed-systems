import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Client c =  new Client();
        //String s = c.httpGet("GET / HTTP/1.1\r\n", "stud.fh-wedel.de", 80);
        c.httpGet("GET / HTTP/1.1\r\n", "localhost", 8080);
    }

    public void httpGet(String msg, String host, int port) throws IOException {
        StringBuilder ret = new StringBuilder();
        Socket s = new Socket(host, port);

        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.print(msg);
        out.print("Host: " + host + "\r\n");
        out.print("\r\n");
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line;
        while((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        out.close();
        s.close();
        System.out.println(ret.toString());

    }


}