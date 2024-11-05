import java.io.*;
import java.net.*;



public class withProxy {
    public static void main(String[] args) {

        String pH = "proxy.fh-wedel.com"; /*Was soll hier hin*/
        int pP = 8080;
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(pH, pP));
            URL url = new URL("http://stud.fh-wedel.de");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            in.close();
            connection.disconnect();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
