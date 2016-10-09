package com.meetify.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * com.meetify.server.model
 * Created by kr3v on 08.10.2016.
 */

public class HttpRequest {
    private static final String GOOGLE_API_KEY = "AIzaSyBgnGyxIek6PtMuVARZmVfaEtlH0Wiazms";

    private HttpRequest() {

    }

    public static List<String> request(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()))) {
            List<String> response = new ArrayList<>();

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                response.add(inputLine.trim());
            in.close();
            return response;
        }
    }

    public static List<String> request(String lat, String lng, String radius) throws IOException {
        URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                lat + "," + lng + "&" +
                "radius=" + radius + "&" +
                "key=" + GOOGLE_API_KEY);
        return request(url);
    }
}
