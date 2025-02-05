package com.recordshopapiproject.apiproject.service.spotifyapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;

@Service
public class SpotifyApiServiceImplementation {
    private final SpotifyApi spotifyApi;
    private final String defaultImageUrl;

    public SpotifyApiServiceImplementation(
            SpotifyApi spotifyApi,
            @Value("${album.image.fallback}") String defaultImageUrl) {

        this.spotifyApi = spotifyApi;
        this.defaultImageUrl = defaultImageUrl;
    }

    public String fetchAlbumCoverUrl(String albumName, String artistName) {
        try {
            String query = String.format("album:%s artist:%s", albumName, artistName);
            SearchAlbumsRequest searchRequest = spotifyApi.searchAlbums(query)
                    .limit(1)
                    .build();

            Paging<AlbumSimplified> results = searchRequest.execute();

            if (results.getItems().length > 0) {
                AlbumSimplified album = results.getItems()[0];
                if (album.getImages().length > 0) {
                    return album.getImages()[0].getUrl();
                }
            }
            return defaultImageUrl;
        } catch (Exception e) {
            return defaultImageUrl;
        }
    }


}
