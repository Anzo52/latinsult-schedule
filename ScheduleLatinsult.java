// Java program to make API calls to https://api.zollnersolutions.com/latinsults/ at 04:20 UTC and 16:20 UTC monday to friday
// using ScheduleExecutorService to schedule the API calls
// API calls are made using the HttpClient library
// The API calls are made using the GET method
// This program will run indefinitely until the program is terminated


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.*;
import java.util.*;


public class ScheduleLatinsult {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {

        // Create a ScheduledExecutorService with a thread pool size of 1
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        List<ScheduledFuture<?>> futures = new ArrayList<ScheduledFuture<?>>();

        // Create a Runnable object to make the API call
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    // Create a URI object to make the API call
                    URI uri = new URI("https://api.zollnersolutions.com/latinsults/"); // TODO: make this dynamic or allow user to input the URI

                    // TODO: object for API key and other authentication

                    // Create a HttpClient and HttpRequest object to make the API call
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(uri)
                            .GET()
                            .build();

                    // Execute the API call
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .thenApply(HttpResponse::body)
                            .thenAccept(System.out::println);

                    System.out.println(client.send(request, HttpResponse.BodyHandlers.ofString()).body());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        // Schedule the API call at 04:20 UTC and 16:20 UTC monday to friday
        // TODO get current day and time after response is received and schedule accordingly (ideally just 12 hours after last call)
        // TODO change to Mountain Time for the heck of it (testing purposes)
        for (int i = 0; i < 5; i++) {
            futures.add(scheduler.schedule(runnable, i * 7200, TimeUnit.SECONDS));
        }

        // Keep the program running until the program is terminated
        while (true) {
            Thread.sleep(1000);
        }
    }
}