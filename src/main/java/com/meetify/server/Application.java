package com.meetify.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.meetify.server
 * Created by kr3v on 02.10.2016.
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        getNearby();
    }
//
//    private static void getNearby() {//throws IOException {
//        String lat = "48.468488";
//        String lon = "35.049684";
//        String radius = "20";
//        try {
//            Optional.ofNullable(new ObjectMapper().readValue(
//                    StringUtils.makeString(HttpRequest.request(lat, lon, radius)),
//                    GooglePlace.class).getResults()).ifPresent(results ->
//                    results.forEach(result -> Optional.ofNullable(result.getPhotos())
//                            .ifPresent(photos -> photos.forEach(photo ->
//                                    photo.setPhotoReference(HttpRequest.photoRefToUrl(photo.getPhotoReference()))))));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}