import io.grpc.stub.StreamObserver;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import io.grpc.database.*;
import io.grpc.database.GetRecordRequest;
import io.grpc.database.GetRecordResponse;
import io.grpc.database.AddRecordRequest;
import io.grpc.database.AddRecordResponse;
import io.grpc.database.GetSizeRequest;
import io.grpc.database.GetSizeResponse;
import io.grpc.database.DatabaseServiceGrpc;

public class DatabaseServer {

    private final int port = 50051;
    private final Server server;
    private static Map<Integer, String> database = new HashMap<>();

    public DatabaseServer() {
        // Erstelle den Server und registriere den Service
        server = ServerBuilder.forPort(port)
                .addService(new DatabaseServiceImpl())
                .build();
    }

    // Startet den Server
    public void start() throws IOException {
        server.start();
        System.out.println("Server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            stop();
        }));
    }

    // Stoppt den Server
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final DatabaseServer server = new DatabaseServer();
        server.start();
        server.server.awaitTermination();
    }

    // Implementierung des Services
    static class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {

        @Override
        public void getRecord(GetRecordRequest request, StreamObserver<GetRecordResponse> responseObserver) {
            int index = request.getIndex();
            String record = database.get(index);
            GetRecordResponse response = GetRecordResponse.newBuilder()
                    .setRecord(record != null ? record : "")
                    .setSuccess(record != null)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void addRecord(AddRecordRequest request, StreamObserver<AddRecordResponse> responseObserver) {
            database.put(request.getIndex(), request.getRecord());
            AddRecordResponse response = AddRecordResponse.newBuilder().setSuccess(true).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getSize(GetSizeRequest request, StreamObserver<GetSizeResponse> responseObserver) {
            GetSizeResponse response = GetSizeResponse.newBuilder()
                    .setSize(database.size())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}

