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
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            byte[] buf = new byte[1024];
            int bytesRead = 0;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            while ((bytesRead = in.read(buf)) != -1) {
                byteStream.write(buf, 0, bytesRead);
                if (in.available() <= 0) break; // End if no more data is available
            }

            // Convert byte array output stream to byte array
            byte[] messageBytes = byteStream.toByteArray();

            // Parse the RPC_Request from the message bytes
            RPC_Request request = RPC_Request.parseFrom(messageBytes);
            RPC_Response response = handleRequest(request);
            response.writeTo(out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                if(ind >= 0 && ind < database.size() && database.get(ind) != null) {
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
