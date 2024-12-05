package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class RecordShopManagerServiceTests {

    @Mock
    private RecordShopManagerRepository recordShopManagerRepository;

    @InjectMocks
    private RecordShopManagerServiceImplementation recordShopManagerServiceImplementation;

    @Test
    void getAllAlbums() {
        // Arrange
        List<Album> albums = new ArrayList<>();
        Artist radiohead = new Artist("Radiohead");
        albums.add(new Album("Darkside of the moon",radiohead,2008, Genre.Indie,"cool", 3,8.99));

        // Ensure that when .findAll() is invoked, it will always return the albums above
        when(recordShopManagerRepository.findAll()).thenReturn(albums);

        // Act
        List<Album> actualResult = recordShopManagerServiceImplementation.getAllAlbums();

        // Assert
        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(albums);
    }

    @Test
    void insertAlbum() {
    }

    @Test
    void getAlbumsByID() {
    }

    @Test
    void updateAlbum() {
    }

    @Test
    void deleteAlbum() {
    }

    @Test
    void getAlbumsByGenre() {
    }
}