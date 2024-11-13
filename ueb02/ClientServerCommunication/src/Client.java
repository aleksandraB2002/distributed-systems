import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Client c;
    String msg="";
    int port;
    String host;

    public Client(String pMsg, int pPort, String pHost) {
        this.msg = pMsg + "\r\n";
        this.port = pPort;
        this.host = pHost;
    }

    public void connect() throws IOException {
        //GET / HTTP/1
        String msg = this.msg;
        String host = this.host;
        int port = this.port;

        Socket s = new Socket(host, port);
        System.out.println(s);
    }

    public String httpGet() throws IOException {
        StringBuilder ret = new StringBuilder("");
        Socket s = new Socket(host, port);

        PrintWriter out = new PrintWriter(s.getOutputStream());
        out.print(msg);
        out.print("Host: " + host + "\r\n");
        out.print("\r\n");
        out.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String line;
        while((line = in.readLine()) != null) {
            ret.append(line).append("\n");
        }
        in.close();
        out.close();
        s.close();
        assert ret != null;
        return ret.toString();
    }
}
