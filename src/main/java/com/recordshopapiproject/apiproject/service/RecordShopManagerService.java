package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.model.Album;


import java.util.List;
import java.util.Optional;

public interface RecordShopManagerService {

    List<Album> getAllAlbums();
    List<AlbumArtistGenreResponseDTO> getResponseDTO();
    Album insertAlbum(Album album);
    AlbumArtistGenreResponseDTO getAlbumByIdReturnDTO(Long ID) throws Exception;
//    Optional<Album> getAlbumsByID(Long ID);
    public Album updateAlbum(Long id, Album albumDetails) throws Exception;
    AlbumArtistGenreResponseDTO updateAlbumUsingDTO(Long id, AlbumArtistGenreResponseDTO albumDTODetails) throws Exception;
    public void deleteAlbum(Long ID);
    Album getAlbumById(Long ID) throws Exception;
    AlbumArtistGenreResponseDTO insertAlbumFromDTO(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO);
}
