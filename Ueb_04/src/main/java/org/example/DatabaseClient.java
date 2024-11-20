package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.dbserver.*;

import java.util.Scanner;

public class DatabaseClient {
    private final DbServer.DatabaseServiceBlockingStub stub;

    public DatabaseClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        stub = DatabaseService.newBlockingStub(channel);
    }

    public static void main(String[] args) {
        DatabaseClient client = new Client("localhost", 8080);
        client.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1 = GET_RECORD, 2 = ADD_RECORD, 3 = GET_SIZE, 9 = END PROCESS");
            int selection = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (selection) {
                    case 1:
                        getRecord(scanner);
                        break;
                    case 2:
                        addRecord(scanner);
                        break;
                    case 3:
                        getSize();
                        break;
                    case 9:
                        return;
                    default:
                        System.out.println("Invalid selection");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void getRecord(Scanner scanner) {
        System.out.println("Enter record number: ");
        int number = scanner.nextInt();

        RPC_Request request = RPC_Request.newBuilder()
                .setOperation(RPC_Request.Operation.GET_RECORD)
                .setIndex(number)
                .build();

        RPC_Response response = stub.performOperation(request);
        System.out.println("Record: " + response.getRecord());
    }

    private void addRecord(Scanner scanner) {
        System.out.println("Enter record number: ");
        int number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter a String: ");
        String string = scanner.nextLine();

        RPC_Request request = RPC_Request.newBuilder()
                .setOperation(RPC_Request.Operation.ADD_RECORD)
                .setIndex(number)
                .setRecord(string)
                .build();

        RPC_Response response = stub.performOperation(request);
        if (response.getSuccess()) {
            System.out.println("Record added successfully");
        } else {
            System.out.println("Failed to add record: " + response.getRecord());
        }
    }

    private void getSize() {
        RPC_Request request = RPC_Request.newBuilder()
                .setOperation(RPC_Request.Operation.GET_SIZE)
                .build();

        RPC_Response response = stub.performOperation(request);
        System.out.println("Database size: " + response.getSize());
    }
}
