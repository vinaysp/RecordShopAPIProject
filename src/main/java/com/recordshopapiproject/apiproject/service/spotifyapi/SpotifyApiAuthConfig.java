package com.recordshopapiproject.apiproject.service.spotifyapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Configuration
public class SpotifyApiAuthConfig {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Bean
    public SpotifyApi spotifyApi() throws ParseException, SpotifyWebApiException, IOException, org.apache.hc.core5.http.ParseException {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();

        ClientCredentialsRequest request = spotifyApi.clientCredentials().build();
        ClientCredentials credentials = request.execute();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        return spotifyApi;
    }
}
