package melonproject.melon.controller;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

@RestController
public class SpotifyController {
    @Value("${spotify.api.client-id}")
    private String clientId;

    @Value("${spotify.api.client-secret}")
    private String clientSecret;

    @GetMapping("/spotify")
    public ResponseEntity<Object> spotify() throws Exception{
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRedirectUri(URI.create("http://localhost:8080/callback"))
        .build();

        String code = "AQC6CsgQLAwMkkFbmKqrw7YxIgze9A-Ryf7SNGyX5xCHctrBsK6kABlZZbgWiWnfUH7tPmaKi14UEryD8gnPzpwaflilR7zYC9Uy0X8EFH19g6AYx--tfgbP-9h2GKdvcbmSSqxLC6VU4pjZpg74ibL4aUCPuWi69CscMlbMotwUMCD_pgcTHiqSD-hiVWloB-5YTzGoxnMJxM7f7SuWmwVCpuUkCQ";
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
        .build();
        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

        // 액세스 토큰
        String accessToken = authorizationCodeCredentials.getAccessToken();

        // 리프레시 토큰
        String refreshToken = authorizationCodeCredentials.getRefreshToken();

        Map<String, Object> map = new LinkedHashMap<>();

        map.put("spotifyAccess", accessToken);
        map.put("spotifyRefresh", refreshToken);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
