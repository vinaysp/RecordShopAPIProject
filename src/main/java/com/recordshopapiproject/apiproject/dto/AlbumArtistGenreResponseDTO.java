package com.recordshopapiproject.apiproject.dto;

import com.recordshopapiproject.apiproject.model.Album;
import lombok.Data;

@Data
public class AlbumArtistGenreResponseDTO {
    public Long albumId;
    private Long artistId;
    private String artistName;
    private String albumName;
    private String albumGenre;
    private String albumDescription;
    private int albumReleaseYear;
    private int stock;
    private double price;
    private String albumImageUrl;

    public AlbumArtistGenreResponseDTO(Album album){
        this.albumId = album.getId();
        this.artistId = album.getArtist() != null ? album.getArtist().getId() : null;
        this.artistName = album.getArtist() != null ? album.getArtist().getArtistName() : null;
        this.albumName = album.getName();
        this.albumGenre = album.getGenre().name();
        this.albumDescription = album.getDescription();
        this.albumReleaseYear = album.getReleaseYear();
        this.stock = album.getStock();
        this.price = album.getPrice();
        this.albumImageUrl = album.getImageUrl();
    }

    public AlbumArtistGenreResponseDTO() {}

}
