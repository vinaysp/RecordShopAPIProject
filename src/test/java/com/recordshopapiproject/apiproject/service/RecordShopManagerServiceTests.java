package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
class RecordShopManagerServiceTests {

    @Mock
    private RecordShopManagerRepository mockrecordShopManagerRepository;

    @InjectMocks
    private RecordShopManagerServiceImplementation recordShopManagerServiceImplementation;


    @Test
    @DisplayName("RecordShopManager returns all albums listed in database")
    void testGetAllAlbumsReturnsListOfAlbums() {
        // Arrange
        List<Album> albums = new ArrayList<>();
        Artist radiohead = new Artist(1L,"Radiohead");
        Artist wham = new Artist(2L,"Wham!");
        albums.add(new Album(1L,"Darkside of the moon",2008, Genre.Indie,"cool", 3,8.99));
        albums.add(new Album(2L,"Darkside of the moon 2",2009, Genre.Lofi," so cool", 1,90.99));
        albums.add(new Album(3L,"Wham",1980, Genre.Pop,"very cool", 1000,999.99));

        // Ensure that when .findAll() is invoked, it will always return the albums above
        when(mockrecordShopManagerRepository.findAll()).thenReturn(albums);

        // Act
        List<Album> actualResult = recordShopManagerServiceImplementation.getAllAlbums();

        // Assert
        assertThat(actualResult).hasSize(3);
        assertThat(actualResult).isEqualTo(albums);
    }

    @Test
    @DisplayName("RecordShopManager should succesfully be able to add a new album to database")
    void testInsertAlbumIntoDatabase() {
        Artist me = new Artist(1L,"me");
        List<String> songs2 = List.of("test 1", "test 2", "test 3", "test 4");
        var expectedAlbum = new Album(1L,"Do your best",2024,Genre.Rock,"motivational",1,9999999.99);

        when(mockrecordShopManagerRepository.save(expectedAlbum)).thenReturn(expectedAlbum);

        Album actualResult = recordShopManagerServiceImplementation.insertAlbum(expectedAlbum);

        assertThat(actualResult).isEqualTo(expectedAlbum);

    }

    @Test
    @DisplayName("RecordShopManager should succesfully return an album from a given album id")
    void getAlbumsByID() {
        Long id = 1L;
        Artist me = new Artist(1L,"me");
        var expectedAlbum = new Album(1L,"Do your best",2024,Genre.Rock,"motivational",1,9999999.99);

        when(mockrecordShopManagerRepository.findById(id)).thenReturn(Optional.of(expectedAlbum));

        Optional<Album> result = recordShopManagerServiceImplementation.getAlbumsByID(id);

        assertTrue(result.isPresent());
        assertEquals(expectedAlbum.getId(),result.get().getId());
        assertEquals(Optional.of(expectedAlbum),result);
    }

    @Test
    void updateAlbum() throws Exception {
        Long albumId = 1L;
        Artist me = new Artist(1L,"me");
        var existingAlbum = new Album(1L,"Do your best",2022,Genre.Rock, "motivational",1,9999999.99);
        var updatedAlbum = new Album(1L,"keep going",2024,Genre.Pop,"very motivational",12,10.50);

        when(mockrecordShopManagerRepository.findById(albumId)).thenReturn(Optional.of(existingAlbum));

        when(mockrecordShopManagerRepository.save(any(Album.class))).thenReturn(updatedAlbum);

        Album result = recordShopManagerServiceImplementation.updateAlbum(albumId, updatedAlbum);

//        assertNotNull(result);
        assertEquals(existingAlbum.getId(), result.getId());
        assertEquals("keep going", result.getName());
//        assertEquals(me,result.getArtist());
        assertEquals(2024,result.getReleaseYear());
        assertEquals(Genre.Pop,result.getGenre());
        assertEquals("very motivational",result.getDescription());
        assertEquals(12,result.getStock());
        assertEquals(10.50,result.getPrice());

    }

    @Test
    void deleteAlbum() {
        Long albumId = 1L;
        Artist me = new Artist(1L,"me");
        var existingAlbum = new Album(1L,"Do your best",2022,Genre.Rock,"motivational",1,9999999.99);
        when(mockrecordShopManagerRepository.save(existingAlbum)).thenReturn(existingAlbum);

        doNothing().when(mockrecordShopManagerRepository).deleteById(albumId);

        recordShopManagerServiceImplementation.deleteAlbum(albumId);

        verify(mockrecordShopManagerRepository, times(1)).deleteById(albumId);

    }

}