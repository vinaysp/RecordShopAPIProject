package com.recordshopapiproject.apiproject.dto.mapper;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;

public class Mapper {

    public AlbumArtistGenreResponseDTO convertEntityToDto(Album album){
        return new AlbumArtistGenreResponseDTO(album);
    }

    public Album convertDtoToAlbum(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO){
        return new Album(
                new Artist (albumArtistGenreResponseDTO.getAlbumName()),
                albumArtistGenreResponseDTO.getArtistName(),
                albumArtistGenreResponseDTO.getAlbumReleaseYear(),
                Genre.valueOf(albumArtistGenreResponseDTO.getAlbumGenre()),
                albumArtistGenreResponseDTO.getAlbumDescription(),
                albumArtistGenreResponseDTO.getStock(),
                albumArtistGenreResponseDTO.getPrice()
        );
    }
}
