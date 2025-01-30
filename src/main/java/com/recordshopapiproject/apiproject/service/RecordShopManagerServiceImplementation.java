package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.dto.mapper.Mapper;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.ArtistRepository;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RecordShopManagerServiceImplementation implements RecordShopManagerService{

    @Autowired
    RecordShopManagerRepository recordShopManagerRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        recordShopManagerRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public List<AlbumArtistGenreResponseDTO> getResponseDTO(){
        List<AlbumArtistGenreResponseDTO> resultList = new ArrayList<>();
        Mapper mapper = new Mapper();
        recordShopManagerRepository.findAll()
                .forEach(entity -> {
                    resultList.add(mapper.convertEntityToDto(entity));
                });
        return resultList;
    }


    @Override
    public Album insertAlbum(Album album) {
        if (album.getArtist() != null) {
            Artist artist = artistRepository.save(album.getArtist());
            album.setArtist(artist);
        }
        return recordShopManagerRepository.save(album);
    }

    @Override
    public Album insertAlbumFromDTO(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO){
        Mapper mapper = new Mapper();
//        Album album = null;
//        album = mapper.convertDtoToAlbum(albumArtistGenreResponseDTO);
//        if(albumArtistGenreResponseDTO.getArtistName() != null){
//            Artist artist = artistRepository.save(album.getArtist());
//            album.setArtist(artist);
//        }
//        return recordShopManagerRepository.save(album);
        Album album = mapper.convertDtoToAlbum(albumArtistGenreResponseDTO);

        if (albumArtistGenreResponseDTO.getArtistName() != null) {
            Optional<Artist> existingArtist = artistRepository.findByArtistName(albumArtistGenreResponseDTO.getArtistName());
            if (existingArtist.isPresent()) {
                album.setArtist(existingArtist.get());
            } else {
                Artist artist = artistRepository.save(album.getArtist());
                album.setArtist(artist);
            }
        }
        return recordShopManagerRepository.save(album);
    }

//    @Override
//    public Optional<Album> getAlbumsByID(Long ID) {
//        return recordShopManagerRepository.findById(ID);
//    }

    @Override
    public Album getAlbumById(Long ID) throws Exception {
        return recordShopManagerRepository.findById(ID)
                .orElseThrow(() -> new Exception("Album not found with id: " + ID));
    }

    @Override
    public AlbumArtistGenreResponseDTO getAlbumByIdReturnDTO(Long ID) throws Exception{
        Mapper mapper = new Mapper();
        Album returnedAlbum = recordShopManagerRepository.findById(ID).orElseThrow(() -> new Exception("Album not found with id: " + ID));
        return mapper.convertEntityToDto(returnedAlbum);
    }

    @Override
    public Album updateAlbum(Long id, Album albumDetails) throws Exception {
        Album album = recordShopManagerRepository.findById(id)
                .orElseThrow(() -> new Exception("Album not found with id: " + id));

        album.setName(albumDetails.getName());
        album.setReleaseYear(albumDetails.getReleaseYear());
        album.setGenre(albumDetails.getGenre());
        album.setDescription(albumDetails.getDescription());
        album.setStock(albumDetails.getStock());
        album.setPrice(albumDetails.getPrice());

        if (albumDetails.getArtist() != null) {
            Artist existingArtist = album.getArtist();
            if (existingArtist != null) {
                existingArtist.setArtistName(albumDetails.getArtist().getArtistName());
            }

            album.setArtist(existingArtist);
        } else {
            album.setArtist(albumDetails.getArtist());
        }

        return recordShopManagerRepository.save(album);
    }

    @Override
    @Transactional
    public AlbumArtistGenreResponseDTO updateAlbumUsingDTO(Long id, AlbumArtistGenreResponseDTO albumDTODetails) throws Exception {
        Mapper mapper = new Mapper();
        Album album;

        album = recordShopManagerRepository.findById(id)
                .orElseThrow(() -> new Exception("Album not found with id: " + id));

        album.setName(albumDTODetails.getAlbumName());
        album.setReleaseYear(albumDTODetails.getAlbumReleaseYear());
        album.setGenre(Genre.valueOf(albumDTODetails.getAlbumGenre()));
        album.setDescription(albumDTODetails.getAlbumDescription());
        album.setStock(albumDTODetails.getStock());
        album.setPrice(albumDTODetails.getPrice());

//        if (album.getArtist() != null) {
//            Artist existingArtist = album.getArtist();
//            existingArtist.setArtistName(albumDTODetails.getArtistName());
//            album.setArtist(existingArtist);
//        } else {
//            album.setArtist(null);
//        }
        if (albumDTODetails.getArtistName() != null) {
            Artist artist;
            if (albumDTODetails.getArtistId() != null) {
                artist = artistRepository.findById(albumDTODetails.getArtistId())
                        .orElse(new Artist());

            } else {
                artist = new Artist();
            }

            artist.setArtistName(albumDTODetails.getArtistName());
            artist = artistRepository.save(artist);

            // Handle the bidirectional relationship
            if (album.getArtist() != null && !album.getArtist().equals(artist)) {
                album.getArtist().removeAlbum(album);
            }
            artist.addAlbum(album);
            album.setArtist(artist);
        }

//        return mapper.convertEntityToDto(recordShopManagerRepository.save(album));
        Album updatedAlbum = recordShopManagerRepository.save(album);
        return mapper.convertEntityToDto(updatedAlbum);
    }

    @Override
    public void deleteAlbum(Long ID) {
        recordShopManagerRepository.deleteById(ID);
    }



}
