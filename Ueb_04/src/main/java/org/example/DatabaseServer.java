package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.dbserver.*;

import java.io.IOException;
import java.util.HashMap;

public class DatabaseServer {
    public static HashMap<Integer, String> database = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        // Populate the database with some records
        database.put(4101, "Appen");
        database.put(4102, "Ahrensburg");
        database.put(4103, "Wedel");
        database.put(4104, "Aumuhle");
        database.put(4105, "Seevetal");
        database.put(4106, "Quickborn");

        DatabaseServer server = ServerBuilder.forPort(8080)
                .addService(new DatabaseServiceImpl())
                .build();

        System.out.println("Server is starting...");
        server.start();
        System.out.println("Server started on port 8080");
        server.awaitTermination();
    }

    static class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {
        @Override
        public void performOperation(RPC_Request request, StreamObserver<RPC_Response> responseObserver) {
            RPC_Response.Builder responseBuilder = RPC_Response.newBuilder();
            switch (request.getOperation()) {
                case GET_RECORD:
                    String record = database.getOrDefault(request.getIndex(), "This record does not exist");
                    responseBuilder.setRecord(record);
                    break;
                case ADD_RECORD:
                    if (!database.containsKey(request.getIndex())) {
                        database.put(request.getIndex(), request.getRecord());
                        responseBuilder.setSuccess(true);
                    } else {
                        responseBuilder.setRecord("This record already exists");
                        responseBuilder.setSuccess(false);
                    }
                    break;
                case GET_SIZE:
                    responseBuilder.setSize(database.size());
                    break;
                default:
                    responseBuilder.setSuccess(false);
                    responseBuilder.setRecord("Unsupported operation");
                    break;
            }
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }
}
