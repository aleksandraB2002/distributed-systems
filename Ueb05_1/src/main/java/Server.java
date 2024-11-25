import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 5000;
    private static final Map<Integer, String> database = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Server is running on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            ObjectMapper mapper = new ObjectMapper();
            String requestJson;
            while ((requestJson = in.readLine()) != null) {
                Map<String, Object> request = mapper.readValue(requestJson, Map.class);
                String method = (String) request.get("method");
                Map<String, Object> response = new HashMap<>();
                response.put("jsonrpc", "2.0");
                response.put("id", request.get("id"));

                if ("addRecord".equals(method)) {
                    Map<String, Object> params = (Map<String, Object>) request.get("params");
                    int key = (Integer) params.get("key");
                    String value = (String) params.get("value");
                    database.put(key, value);
                    response.put("result", "Record added successfully");
                } else if ("getRecord".equals(method)) {
                    Map<String, Object> params = (Map<String, Object>) request.get("params");
                    int key = (Integer) params.get("key");
                    String value = database.getOrDefault(key, "Record not found");
                    response.put("result", value);
                } else if ("getSize".equals(method)) {
                    response.put("result", database.size());
                } else {
                    response.put("error", "Unknown method");
                }

                String responseJson = mapper.writeValueAsString(response);
                out.println(responseJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
