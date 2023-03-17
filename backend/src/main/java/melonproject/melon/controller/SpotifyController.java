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

        String code = "AQDtFw16Oe5CbJVCLb6FGGn9yrsAPAdrpBiZ-e_RoQaiZVwiWlkX3ZGW9MMoRmrKvehZ9wjRqc8uano6C737fgHCdmeMeIKoeFCE4NU3-2b7JshyfaJf76S62Tjmxw9kBTDGHor_remtcl07dKc6g9LP3uB4AQznIw4z9VEe0ileZajDCDuin36Rj38QxoTaP-3Z1XmY4kcz5eQ9am6ikw9EJitD4w";
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
