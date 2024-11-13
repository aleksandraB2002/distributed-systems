import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class ProxyClient {
    public static void main(String[] args) throws IOException, MalformedURLException {
        URL url = new URL("https://stud.fh-wedel.de");

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("36.73.128.108", 8080));
        HttpURLConnection proxyConnection = (HttpURLConnection) url.openConnection(proxy);

        proxyConnection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(proxyConnection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println(proxyConnection.getResponseCode());
        in.close();
        proxyConnection.disconnect();
    }
}
