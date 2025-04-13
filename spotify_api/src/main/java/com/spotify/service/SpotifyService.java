package com.spotify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.model.CurrentlyPlaying;
import com.spotify.model.Track;
import com.spotify.util.SpotifyApiClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    private final SpotifyApiClient apiClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public SpotifyService(SpotifyApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<Track> getTopTracks() throws Exception {
        String response = apiClient.get("/me/top/tracks?limit=10");
        JsonNode root = mapper.readTree(response);
        List<Track> tracks = new ArrayList<>();

        for (JsonNode item : root.get("items")) {
            String name = item.get("name").asText();
            String artist = item.get("artists").get(0).get("name").asText();
            String uri = item.get("uri").asText();
            tracks.add(new Track(name, artist, uri));
        }

        return tracks;
    }

    public CurrentlyPlaying getNowPlaying() throws Exception {
        String response = apiClient.get("/me/player/currently-playing");
        JsonNode root = mapper.readTree(response);

        if (root == null || root.isEmpty() || !root.has("item")) {
            return null;
        }

        String name = root.get("item").get("name").asText();
        String artist = root.get("item").get("artists").get(0).get("name").asText();
        boolean isPlaying = root.get("is_playing").asBoolean();

        CurrentlyPlaying cp = new CurrentlyPlaying();
        cp.setTrackName(name);
        cp.setArtistName(artist);
        cp.setPlaying(isPlaying);
        return cp;
    }

    public void playTrack(String uri) throws Exception {
        String json = "{\"uris\": [\"" + uri + "\"]}";
        apiClient.put("/me/player/play", json);
    }

    public void stopPlayback() throws Exception {
        apiClient.put("/me/player/pause", "");
    }
}