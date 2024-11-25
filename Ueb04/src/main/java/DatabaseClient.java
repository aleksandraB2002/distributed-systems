package io.grpc.database;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DatabaseClient {

    private final ManagedChannel channel;
    private final DatabaseServiceGrpc.DatabaseServiceBlockingStub blockingStub;

    public DatabaseClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = DatabaseServiceGrpc.newBlockingStub(channel);
    }

    public void addRecord(int index, String record) {
        AddRecordRequest request = AddRecordRequest.newBuilder()
                .setIndex(index)
                .setRecord(record)
                .build();
        AddRecordResponse response = blockingStub.addRecord(request);
        System.out.println("Add record result: " + response.getSuccess());
    }

    public void getRecord(int index) {
        GetRecordRequest request = GetRecordRequest.newBuilder()
                .setIndex(index)
                .build();
        GetRecordResponse response = blockingStub.getRecord(request);
        System.out.println("Record: " + response.getRecord() + ", Success: " + response.getSuccess());
    }

    public void getSize() {
        GetSizeRequest request = GetSizeRequest.newBuilder().build();
        GetSizeResponse response = blockingStub.getSize(request);
        System.out.println("Database size: " + response.getSize());
    }

    public static void main(String[] args) {
        DatabaseClient client = new DatabaseClient("localhost", 50051);
        client.getSize();

        client.addRecord(4101, "Appen");
        client.addRecord(4102, "Ahrensburg");
        client.addRecord(4103, "Wedel");
        client.addRecord(4104, "Aumühle");
        client.addRecord(4105, "Seevetal");
        client.addRecord(4106, "Quickborn");

        client.getRecord(4103);
        client.getRecord(4107);

        client.getSize();
    }
}
