import java.io.*;
import java.net.Socket;

public class ClientHandlingInstance implements Runnable{
    Socket socket;

    public ClientHandlingInstance(Socket pSocket) throws IOException {
        this.socket = pSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String req = in.readLine();
            System.out.println("Server got the following request: " + req);

            if(req != null && req.contains("GET")) {
                get(in, out, req);
            } else if (req != null && req.contains("POST")) {
                post(in, out, req);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void get(BufferedReader in, BufferedWriter out, String req) throws IOException {
        //get file path
        String[] tokens = req.split(" ");
        String filePath = tokens[1].equals("/") ? "index.html" : tokens[1];

        File file = new File(filePath);
        //check if file exists and respone
        if (file.exists() && !file.isDirectory()) {
            System.out.println("File exists");
            sendResponse(out, 200, "OK", file);
        } else {
            sendResponse(out, 404, "Not Found", null);
            System.out.println(System.getProperty("user.dir"));
        }
    }

    private void post(BufferedReader in, BufferedWriter out, String req) throws IOException {
        String[] tokens = req.split(" ");
        String filePath = tokens[1].equals("/") ? "/index.html" : tokens[1];
        File file = new File(filePath);

        String line;
        int contentLength = 0;

        // Read headers until empty line (HTTP headers are separated from body by empty line)
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            if(line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(" ")[1]);
            }
            System.out.println("Header: " + line);
        }

        // Only read body if we have content length
        if (contentLength > 0) {
            char[] bodyContent = new char[contentLength];
            in.read(bodyContent, 0, contentLength);
            String postDat = new String(bodyContent);
            System.out.println("Daten: " + postDat);
            File data = new File("data.txt");
            writeFile("data.txt", postDat);
            sendResponse(out, 200, "OK", file);
            System.out.println("File has been created at " + filePath);
        } else {
            sendResponse(out, 400, "Bad Request - No Content Length", null);
        }
    }

    private void writeFile(String fileName, String content) {
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(content);
            out.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(BufferedWriter out, int code, String msg, File file) throws IOException {
        out.write("HTTP/1.1" + " " + code + " " + msg + "\r\n");
        out.write("Content-Type: text/html\r\n");
        out.write("\r\n");

        if(file != null) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                String line;
                while((line = in.readLine()) != null) {
                    out.write(line + "\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            out.write("Error" + " " + code + " " + msg + "\r\n");
        }

        out.flush();
    }
}
