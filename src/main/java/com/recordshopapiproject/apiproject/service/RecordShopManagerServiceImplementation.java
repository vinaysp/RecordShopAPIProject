package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RecordShopManagerServiceImplementation implements RecordShopManagerService{

    @Autowired
    RecordShopManagerRepository recordShopManagerRepository;

    @Override
    public List<Album> getAllAlbums() {
        return List.of();
    }

    @Override
    public Album insertAlbum(Album album) {
        return null;
    }

    @Override
    public Optional<Album> getAlbumsByID(Long ID) {
        return Optional.empty();
    }

    @Override
    public Album updateAlbum(Long id, Album albumDetails) throws Exception {
        return null;
    }

    @Override
    public void deleteAlbum(Long ID) {

    }

    @Override
    public Iterable<Album> getAlbumsByGenre(Genre genre) {
        return null;
    }
}
