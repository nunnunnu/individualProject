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

        String code = "AQA2cuOb7VlQ4K8LvDAEKElM0QaWti81CZ-GpnLNbALf73A6q_TzmrHrnrxId3pMRwE1oiINBD7zTAIuo2PY7bgOvNxuCMHIUSoXRSIme8Q7X6EW_1WsqQe_Yw65owOv6TF_8lJx83Imt3-7G-v2znSMqrTlo2kH3gv-1mMzx2uFmz_FWyUPWM7R8ZyqKF6RBSc26CE1eX1SUcVit9q2Rf-3WrsvtA";
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
