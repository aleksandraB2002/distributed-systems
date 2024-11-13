package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Client c =  new Client();
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

    public void httpPost(String msg, String host, int port, String data) throws IOException {
        Socket s = new Socket(host, port);

        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.print("POST / HTTP/1.1\r\n");
        out.print("Host: " + host + "\r\n");
        out.print("Content-Length: " + data.length() + "\r\n");
        out.print("Content-Type: text/plain\r\n");
        out.print("\r\n");
        out.print(data);
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        out.close();
        s.close();
    }

}