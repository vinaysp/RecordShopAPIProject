package com.recordshopapiproject.apiproject.dto.mapper;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AlbumArtistGenreResponseDTO convertEntityToDto(Album album){

        if (album == null) {
            throw new IllegalArgumentException("Cannot convert null album to DTO");
        }

        return new AlbumArtistGenreResponseDTO(album);
    }

    public Album convertDtoToAlbum(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO){

        if (albumArtistGenreResponseDTO == null) {
            throw new IllegalArgumentException("Cannot convert null DTO to Album");
        }

        return new Album(
                new Artist (albumArtistGenreResponseDTO.getArtistName()),
                albumArtistGenreResponseDTO.getAlbumName(),
                albumArtistGenreResponseDTO.getAlbumReleaseYear(),
                Genre.valueOf(albumArtistGenreResponseDTO.getAlbumGenre()),
                albumArtistGenreResponseDTO.getAlbumDescription(),
                albumArtistGenreResponseDTO.getStock(),
                albumArtistGenreResponseDTO.getPrice(),
                albumArtistGenreResponseDTO.getAlbumImageUrl()
        );
    }
}
