package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RecordShopManagerServiceImplementation implements RecordShopManagerService{

    @Autowired
    RecordShopManagerRepository recordShopManagerRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        recordShopManagerRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public Album insertAlbum(Album album) {
        return recordShopManagerRepository.save(album);
    }

    @Override
    public Optional<Album> getAlbumsByID(Long ID) {
        return recordShopManagerRepository.findById(ID);
    }

    @Override
    public Album updateAlbum(Long id, Album albumDetails) throws Exception {
        Album album = recordShopManagerRepository.findById(id)
                .orElseThrow(() -> new Exception("Album not found with id: " + id));

        album.setId(albumDetails.getId());
        album.setName(albumDetails.getName());
        album.setArtist(albumDetails.getArtist());
        album.setReleaseYear(albumDetails.getReleaseYear());
        album.setGenre(albumDetails.getGenre());
        album.setDescription(albumDetails.getDescription());
        album.setStock(albumDetails.getStock());
        album.setPrice(albumDetails.getPrice());

        return recordShopManagerRepository.save(album);
    }

    @Override
    public void deleteAlbum(Long ID) {
        recordShopManagerRepository.deleteById(ID);
    }

}
