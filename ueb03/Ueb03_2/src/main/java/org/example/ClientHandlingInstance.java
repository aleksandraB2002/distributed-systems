package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import io.grpc.dbserver.RPC_Request;
import io.grpc.dbserver.RPC_Response;

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

            int len = in.readAllBytes().length;
            byte[] buf = new byte[len];

            RPC_Request request = RPC_Request.parseFrom(buf);
            RPC_Response response = handleRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public RPC_Response handleRequest(RPC_Request request) {
        RPC_Response.Builder responseBuilder = RPC_Response.newBuilder();
        switch(request.getOperation()){
            case DEFAULT:
                responseBuilder.setRecord("");
                responseBuilder.setSize(0);
                responseBuilder.setSuccess(false);
                break;
            case GET_RECORD:
                int ind = request.getIndex();
                if(ind > 0 && ind < database.size()) {
                    responseBuilder.setRecord(database.get(ind));
                } else {
                    responseBuilder.setRecord("This record does not exist");
                }
                break;
            case GET_SIZE:
                responseBuilder.setSize(database.size());
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
        return responseBuilder.build();
    }
}
