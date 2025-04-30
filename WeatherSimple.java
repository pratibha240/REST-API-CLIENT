package java_task2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;


public class WeatherSimple {
    public static void main(String[] args) throws Exception {
        String urlStr = "https://api.weatherapi.com/v1/current.json?key=YOUR_API_KEY&q=London";

        URI uri = URI.create(urlStr);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) response.append(line);
        in.close();

        // Quick and dirty manual parsing
        String json = response.toString();

        String city = getValue(json, "\"name\":\"", "\"");
        String tempC = getValue(json, "\"temp_c\":", ",");
        String condition = getValue(json, "\"text\":\"", "\"");

        System.out.println("City: " + city);
        System.out.println("Temperature (\u00B0C): " + tempC);
        System.out.println("Condition: " + condition);
    }

    // Helper method to extract values between markers
    private static String getValue(String json, String keyStart, String keyEnd) {
        int start = json.indexOf(keyStart) + keyStart.length();
        int end = json.indexOf(keyEnd, start);
        return json.substring(start, end);
    }
}
