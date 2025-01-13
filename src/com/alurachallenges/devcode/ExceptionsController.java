package com.alurachallenges.devcode;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;

public class ExceptionsController {
    public static String HTTPMethods(String uriResource) {
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriResource))
                    .build();
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (NullPointerException e) {
            System.err.println("Error de NullPointerException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de IOException: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error grave InterruptedException: " + e.getMessage());
        }

        return "http_response";
    }
}
