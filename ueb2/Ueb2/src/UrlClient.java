import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlClient {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://stud.fh-wedel.de");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;

        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println(connection.getResponseCode());
        in.close();
        connection.disconnect();

    }
}
