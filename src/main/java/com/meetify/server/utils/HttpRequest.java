package com.meetify.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    
    private static List<String> request(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                List<String> response = new ArrayList<>();
                
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    response.add(inputLine.trim());
                in.close();
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public static List<String> request(String lat, String lng, String radius) {
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                    lat + "," + lng + "&" +
                    "radius=" + radius + "&" +
                    "key=" + GOOGLE_API_KEY);
            return request(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    public static String photoRefToUrl(String photoRef) {
        
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL("https://maps.googleapis.com/maps/api/place/photo?" +
                    "photoreference=" + photoRef + "&" +
                    "key=" + GOOGLE_API_KEY + "&" +
                    "maxwidth=600")).openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            return conn.getURL().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
