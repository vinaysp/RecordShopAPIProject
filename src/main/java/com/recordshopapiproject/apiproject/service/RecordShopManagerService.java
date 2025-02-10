package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import java.util.List;

public interface RecordShopManagerService {

    List<AlbumArtistGenreResponseDTO> getResponseDTO();
    AlbumArtistGenreResponseDTO getAlbumByIdReturnDTO(Long ID) throws Exception;
    AlbumArtistGenreResponseDTO updateAlbumUsingDTO(Long id, AlbumArtistGenreResponseDTO albumDTODetails) throws Exception;
    void deleteAlbum(Long ID);
    AlbumArtistGenreResponseDTO insertAlbumFromDTO(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO);
}
