package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import io.grpc.dbserver.RPC_Request;
import io.grpc.dbserver.RPC_RequestOrBuilder;
import io.grpc.dbserver.RPC_Response;
import io.grpc.dbserver.DbServer;

public class Client {
    private Socket socket;
    private OutputStream out;
    private InputStream in;

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost",8080);
            client.start();
        } catch (IOException e) {
            System.err.println("Could not start client: " + e.getMessage());
        }
    }

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
        System.out.println("Connected to " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("1 = GET_RECORD, 2 = ADD_RECORD, 3 = GET_SIZE, 9 = END PROCESS");

            int selection = scanner.nextInt();
            //scanner.nextLine();
            try {
                switch (selection) {
                    case 1:
                        getRecord(scanner);
                        break;
                    case 2:
                        addRecord(scanner);
                        break;
                    case 3:
                        getSize(scanner);
                        scanner.nextLine();
                        break;
                    case 9:
                        socket.close();
                        break;
                    default:
                        System.out.println("Invalid selection");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getRecord(Scanner scanner) throws IOException {
        System.out.println("Enter record number: ");
        int number = scanner.nextInt();


        RPC_Request r = RPC_Request.newBuilder()
                .setOperation(RPC_Request.Operation.GET_RECORD)
                .setIndex(number)
                .build();

        r.writeDelimitedTo(out);
        out.flush();
        RPC_Response response = rpcResponse();

        System.out.println(response + " was found at index " + number);
    }

    private void addRecord(Scanner scanner) throws IOException {
        System.out.println("Enter record number: ");
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter a String: ");
        String string = scanner.nextLine();

        RPC_Request r = RPC_Request.newBuilder()
                .setOperation(RPC_Request.Operation.ADD_RECORD)
                .setIndex(number)
                .setRecord(string)
                .build();

        r.writeDelimitedTo(out);
        out.flush();

        RPC_Response response = rpcResponse();
        System.out.println(response);
    }

    private void getSize(Scanner scanner) throws IOException {
        RPC_Request r = RPC_Request.newBuilder()
                .setOperation(RPC_Request.Operation.GET_SIZE)
                .build();

        r.writeDelimitedTo(out);
        out.flush();

        RPC_Response response = rpcResponse();
        System.out.println("Database is of size: " + response);
    }

    private RPC_Response rpcResponse() throws IOException {
        return RPC_Response.parseDelimitedFrom(in);
    }


}