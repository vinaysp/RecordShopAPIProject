package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Genre;

import java.util.List;
import java.util.Optional;

public interface RecordShopManagerService {

    List<Album> getAllAlbums();
    Album insertAlbum(Album album);

    Optional<Album> getAlbumsByID(Long ID);
    public Album updateAlbum(Long id, Album albumDetails) throws Exception;
    public void deleteAlbum(Long ID);
    Album getAlbumById(Long ID) throws Exception;

}
