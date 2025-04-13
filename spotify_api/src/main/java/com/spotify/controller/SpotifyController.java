package com.spotify.controller;

import com.spotify.model.CurrentlyPlaying;
import com.spotify.model.Track;
import com.spotify.service.SpotifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/top-tracks")
    public ResponseEntity<List<Track>> getTopTracks() throws Exception {
        return ResponseEntity.ok(spotifyService.getTopTracks());
    }

    @GetMapping("/now-playing")
    public ResponseEntity<CurrentlyPlaying> getNowPlaying() throws Exception {
        return ResponseEntity.ok(spotifyService.getNowPlaying());
    }

    @PutMapping("/play")
    public ResponseEntity<String> playTrack(@RequestParam String uri) throws Exception {
        spotifyService.playTrack(uri);
        return ResponseEntity.ok("Playback started for URI: " + uri);
    }

    @PutMapping("/pause")
    public ResponseEntity<String> pause() throws Exception {
        spotifyService.stopPlayback();
        return ResponseEntity.ok("Playback paused.");
    }
}