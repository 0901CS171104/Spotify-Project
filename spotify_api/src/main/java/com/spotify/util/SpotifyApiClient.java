package com.spotify.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpotifyApiClient {
    private final OkHttpClient client = new OkHttpClient();
    private final String accessToken = "YOUR_SPOTIFY_ACCESS_TOKEN";

    private final String baseUrl = "https://api.spotify.com/v1";

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        return client.newCall(request).execute().body().string();
    }

    public String put(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .put(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        return client.newCall(request).execute().body().string();
    }

    public String post(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .post(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        return client.newCall(request).execute().body().string();
    }
}
