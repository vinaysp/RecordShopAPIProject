package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.dto.mapper.Mapper;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.ArtistRepository;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import com.recordshopapiproject.apiproject.service.spotifyapi.SpotifyApiServiceImplementation;
import io.micrometer.common.util.StringUtils;
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

    @Autowired
    SpotifyApiServiceImplementation spotifyService;

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
    public AlbumArtistGenreResponseDTO insertAlbumFromDTO(AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO){
        Mapper mapper = new Mapper();

        Album album = mapper.convertDtoToAlbum(albumArtistGenreResponseDTO);

        if (StringUtils.isBlank(albumArtistGenreResponseDTO.getAlbumImageUrl())) {
            String coverUrl = spotifyService.fetchAlbumCoverUrl(
                    albumArtistGenreResponseDTO.getAlbumName(),
                    albumArtistGenreResponseDTO.getArtistName()
            );
            album.setImageUrl(coverUrl);
        }

        if (albumArtistGenreResponseDTO.getArtistName() != null) {
            Optional<Artist> existingArtist = artistRepository.findByArtistName(albumArtistGenreResponseDTO.getArtistName());
            if (existingArtist.isPresent()) {
                album.setArtist(existingArtist.get());
            } else {
                Artist artist = artistRepository.save(album.getArtist());
                album.setArtist(artist);
            }
        }
        return mapper.convertEntityToDto(recordShopManagerRepository.save(album));
    }

    @Override
    public AlbumArtistGenreResponseDTO getAlbumByIdReturnDTO(Long ID) throws Exception{
        Mapper mapper = new Mapper();
        Album returnedAlbum = recordShopManagerRepository.findById(ID).orElseThrow(() -> new Exception("Album not found with id: " + ID));
        return mapper.convertEntityToDto(returnedAlbum);
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
        album.setImageUrl(albumDTODetails.getAlbumImageUrl());

        if (StringUtils.isBlank(albumDTODetails.getAlbumImageUrl())) {
            String coverUrl = spotifyService.fetchAlbumCoverUrl(
                    albumDTODetails.getAlbumName(),
                    albumDTODetails.getArtistName()
            );
            album.setImageUrl(coverUrl);
        }

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

            if (album.getArtist() != null && !album.getArtist().equals(artist)) {
                album.getArtist().removeAlbum(album);
            }
            album.setArtist(artist);
            artist.addAlbum(album);
            album.setArtist(artist);
        }

        Album updatedAlbum = recordShopManagerRepository.save(album);
        return mapper.convertEntityToDto(updatedAlbum);
    }

    @Override
    public void deleteAlbum(Long ID) {
        recordShopManagerRepository.deleteById(ID);
    }



}
