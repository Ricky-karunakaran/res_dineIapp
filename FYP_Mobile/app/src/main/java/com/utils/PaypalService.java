package com.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class PaypalService {


        public String getAccessToken(String clientId, String clientSecret) {
            String tokenEndpoint = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
            String authString = clientId + ":" + clientSecret;
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
            String requestBody = "grant_type=client_credentials";

            try {
                URL url = new URL(tokenEndpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Authorization", basicAuth);
                connection.setDoOutput(true);

                connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    System.out.println("API request failed. Response Code: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static void paym() throws IOException {
            URL url = new URL("https://api-m.sandbox.paypal.com/v2/checkout/orders/5O190127TN364715T/authorize");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");

            httpConn.setRequestProperty("PayPal-Request-Id", "7b92603e-77ed-4896-8e78-5dea2050476a");
            httpConn.setRequestProperty("Authorization", "Bearer access_token6V7rbVwmlM1gFZKW_8QtzWXqpcwQ6T5vhEGYNJDAAdn3paCgRpdeMdVYmWzgbKSsECednupJ3Zx5Xd-g");

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            System.out.println(response);
        }

}
