package com.recordshopapiproject.apiproject.dto.mapper;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AlbumArtistGenreResponseDTO convertEntityToDto(Album album){
        return new AlbumArtistGenreResponseDTO(album);
    }

    public Album convertDtoToAlbum(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO){
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
