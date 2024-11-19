package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import io.grpc.dbserver.*;

import static org.example.Server.database;

public class ClientHandlingInstance implements Runnable {
    private final Socket socket;

    public ClientHandlingInstance(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()) {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                // Parse the RPC_Request from the message bytes
                RPC_Request request = RPC_Request.parseDelimitedFrom(in);
                System.out.println(request);
                RPC_Response response = handleRequest(request);
                response.writeDelimitedTo(out);
                out.flush();
            }
        } catch (IOException e) {
            if (e instanceof java.net.SocketException) {
                System.out.println("Client connection closed: " + e.getMessage());
            } else {
                System.out.println(e);
            }
        }
    }

    public synchronized RPC_Response handleRequest(RPC_Request request) {
        RPC_Response.Builder responseBuilder = RPC_Response.newBuilder();
        switch(request.getOperation()){
            case DEFAULT:
                responseBuilder.setRecord("");
                responseBuilder.setSize(0);
                responseBuilder.setSuccess(false);
                break;
            case GET_RECORD:
                System.out.println("received request: " + request);
                int ind = request.getIndex();
                System.out.println("index: " + ind);
                if(ind >= 0 && database.get(ind) != null) {
                    responseBuilder.setRecord(database.get(ind));
                } else {
                    responseBuilder.setRecord("This record does not exist");
                }
                break;
            case GET_SIZE:
                String s = String.valueOf(database.size());
                responseBuilder.setRecord(s);
                break;
            case ADD_RECORD:
                String rec = request.getRecord();
                if(rec != null && !database.containsKey(request.getIndex())) {
                    database.put(request.getIndex(), rec);
                } else {
                    responseBuilder.setRecord("This record already exists or your send Data is null");
                }
                break;
            default:
                responseBuilder.setRecord("");
                responseBuilder.setSize(0);
                responseBuilder.setSuccess(false);
                break;
        }
        System.out.println(responseBuilder.toString());

        return responseBuilder.build();
    }
}
