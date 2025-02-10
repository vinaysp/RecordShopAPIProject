package com.recordshopapiproject.apiproject.service;

import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.dto.mapper.Mapper;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.repository.ArtistRepository;
import com.recordshopapiproject.apiproject.repository.RecordShopManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Field;
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
    private ArtistRepository artistRepository;

    @Mock
    private RecordShopManagerRepository mockrecordShopManagerRepository;

    @InjectMocks
    private RecordShopManagerServiceImplementation recordShopManagerServiceImplementation;

    @Mock
    private Mapper mapper;

    @BeforeEach
    void setUp() {
        when(mapper.convertDtoToAlbum(any(AlbumArtistGenreResponseDTO.class)))
                .thenAnswer(invocation -> {
                    AlbumArtistGenreResponseDTO dto = invocation.getArgument(0);
                    return new Album(
                            new Artist(dto.getArtistName()),
                            dto.getAlbumName(),
                            dto.getAlbumReleaseYear(),
                            Genre.valueOf(dto.getAlbumGenre()),
                            dto.getAlbumDescription(),
                            dto.getStock(),
                            dto.getPrice(),
                            dto.getAlbumImageUrl()
                    );
                });
    }


    @Test
    @DisplayName("RecordShopManager returns all albums listed in database")
    void testGetAllAlbumsReturnsListOfAlbums() {
        // Arrange
        List<Album> albums = new ArrayList<>();
        Artist radiohead = new Artist("Radiohead");
        Artist wham = new Artist("Wham!");
        albums.add(new Album(radiohead,"Darkside of the moon",2008, Genre.Indie,"cool", 3,8.99, "test url 1"));
        albums.add(new Album(wham,"Darkside of the moon 2",2009, Genre.Lofi," so cool", 1,90.99, "test url 2"));
        albums.add(new Album(wham,"Wham",1980, Genre.Pop,"very cool", 1000,999.99,"test url 3"));


        when(mockrecordShopManagerRepository.findAll()).thenReturn(albums);


        List<AlbumArtistGenreResponseDTO> actualResult = recordShopManagerServiceImplementation.getResponseDTO();


        assertThat(actualResult).hasSize(3);
    }

    @Test
    @DisplayName("RecordShopManager should succesfully be able to add a new album to database")
    void testInsertAlbumIntoDatabase() {
        AlbumArtistGenreResponseDTO inputDto = new AlbumArtistGenreResponseDTO();
        inputDto.setArtistName("me");
        inputDto.setAlbumName("Do your best");
        inputDto.setAlbumReleaseYear(2024);
        inputDto.setAlbumGenre("Rock");
        inputDto.setAlbumDescription("motivational");
        inputDto.setStock(1);
        inputDto.setPrice(9999999.99);
        inputDto.setAlbumImageUrl("test url 4");

        Artist expectedArtist = new Artist("me");
        Album expectedAlbum = new Album(expectedArtist, "Do your best", 2024, Genre.Rock, "motivational", 1, 9999999.99, "test url 4");

        when(artistRepository.save(any(Artist.class))).thenReturn(expectedArtist);
        when(mockrecordShopManagerRepository.save(any(Album.class))).thenReturn(expectedAlbum);

        AlbumArtistGenreResponseDTO actualResult = recordShopManagerServiceImplementation.insertAlbumFromDTO(inputDto);

        assertThat(actualResult.getAlbumName()).isEqualTo(inputDto.getAlbumName());
        assertThat(actualResult.getArtistName()).isEqualTo(inputDto.getArtistName());
        assertThat(actualResult.getAlbumGenre()).isEqualTo(inputDto.getAlbumGenre());
    }

    @Test
    @DisplayName("RecordShopManager should succesfully return an album from a given album id")
    void getAlbumsByID() throws Exception {
        Long ID = 1L;
        Artist me = new Artist("me");
        var expectedAlbum = new Album(me,"Do your best",2024,Genre.Rock,"motivational",1,9999999.99, "test url");

        when(mockrecordShopManagerRepository.findById(ID)).thenReturn(Optional.of(expectedAlbum));

        Optional<AlbumArtistGenreResponseDTO> result = Optional.ofNullable(recordShopManagerServiceImplementation.getAlbumByIdReturnDTO(ID));

        assertTrue(result.isPresent());
        assertEquals(expectedAlbum.getId(),result.get().getAlbumId());
    }

    @Test
    void updateAlbum() throws Exception {

        Long albumId = 1L;
        Artist existingArtist = new Artist("me");
        Album existingAlbum = new Album(existingArtist, "Do your best", 2022, Genre.Rock, "motivational", 1, 9999999.99, "test url 5");
        existingArtist.addAlbum(existingAlbum);


        AlbumArtistGenreResponseDTO updateDto = new AlbumArtistGenreResponseDTO();
        updateDto.setArtistName("me");
        updateDto.setAlbumName("keep going");
        updateDto.setAlbumReleaseYear(2024);
        updateDto.setAlbumGenre("Pop");
        updateDto.setAlbumDescription("very motivational");
        updateDto.setStock(12);
        updateDto.setPrice(10.50);
        updateDto.setAlbumImageUrl("test url 6");


        when(mockrecordShopManagerRepository.findById(albumId))
                .thenReturn(Optional.of(existingAlbum));
        when(artistRepository.findByArtistName("me"))
                .thenReturn(Optional.of(existingArtist));
        when(artistRepository.save(any(Artist.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(mockrecordShopManagerRepository.save(any(Album.class)))
                .thenAnswer(invocation -> {
                    Album savedAlbum = invocation.getArgument(0);
                    Field idField = Album.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(savedAlbum, albumId);
                    return savedAlbum;
                });

        AlbumArtistGenreResponseDTO result = recordShopManagerServiceImplementation.updateAlbumUsingDTO(albumId, updateDto);


        assertEquals(albumId, result.getAlbumId());
        assertEquals("keep going", result.getAlbumName());
        assertEquals(2024, result.getAlbumReleaseYear());
        assertEquals("Pop", result.getAlbumGenre());
        assertEquals("very motivational", result.getAlbumDescription());
        assertEquals(12, result.getStock());
        assertEquals(10.50, result.getPrice());
        assertEquals("me", result.getArtistName());

    }

    @Test
    void deleteAlbum() {
        Long albumId = 1L;
        Artist me = new Artist("me");
        var existingAlbum = new Album(me,"Do your best",2022,Genre.Rock,"motivational",1,9999999.99, "test url 7");
        when(mockrecordShopManagerRepository.save(existingAlbum)).thenReturn(existingAlbum);

        doNothing().when(mockrecordShopManagerRepository).deleteById(albumId);

        recordShopManagerServiceImplementation.deleteAlbum(albumId);

        verify(mockrecordShopManagerRepository, times(1)).deleteById(albumId);

    }

}