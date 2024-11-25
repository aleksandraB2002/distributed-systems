import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            ObjectMapper mapper = new ObjectMapper();

            int[] keys = {4101, 4102, 4103, 4104, 4105, 4106};
            String[] values = {"Appen", "Ahrensburg", "Wedel", "Aumuhle", "Seevetal", "Quickborn"};

            for (int i = 0; i < keys.length; i++) {
                Map<String, Object> addRequest = new HashMap<>();
                addRequest.put("jsonrpc", "2.0");
                addRequest.put("method", "addRecord");
                addRequest.put("params", Map.of("key", keys[i], "value", values[i]));
                addRequest.put("id", i + 1);

                out.println(mapper.writeValueAsString(addRequest));
                System.out.println("Server response: " + in.readLine());
            }

            Map<String, Object> getRequest = new HashMap<>();
            getRequest.put("jsonrpc", "2.0");
            getRequest.put("method", "getRecord");
            getRequest.put("params", Map.of("key", 4103));
            getRequest.put("id", 10);

            out.println(mapper.writeValueAsString(getRequest));
            System.out.println("Server response: " + in.readLine());

            getRequest.put("params", Map.of("key", 4107));
            out.println(mapper.writeValueAsString(getRequest));
            System.out.println("Server response: " + in.readLine());

            Map<String, Object> sizeRequest = new HashMap<>();
            sizeRequest.put("jsonrpc", "2.0");
            sizeRequest.put("method", "getSize");
            sizeRequest.put("id", 20);

            out.println(mapper.writeValueAsString(sizeRequest));
            System.out.println("Server response: " + in.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
