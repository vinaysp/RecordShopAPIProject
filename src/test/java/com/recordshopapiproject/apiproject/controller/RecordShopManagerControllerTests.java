package com.recordshopapiproject.apiproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recordshopapiproject.apiproject.dto.AlbumArtistGenreResponseDTO;
import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import com.recordshopapiproject.apiproject.service.RecordShopManagerServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class RecordShopManagerControllerTests {

    @Mock
    private RecordShopManagerServiceImplementation mockRecordShopManagerImpl;

    @InjectMocks
    private RecordShopManagerController recordShopManagerController;

    @Autowired
    private MockMvc mockMvcController;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(recordShopManagerController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllAlbumsReturnsAlbums() throws Exception {

        List<Album> albums = new ArrayList<>();
        Artist me = new Artist("me");
        Artist m3 = new Artist("m3");
        albums.add(new Album(me,"Do your best", 2022, Genre.Rock, "motivational", 1, 9999999.99));
        albums.add(new Album(me,"keep going", 2024, Genre.Pop,"very motivational", 12, 10.50));
        albums.add(new Album(m3,"Try it, fix it",2021, Genre.Lofi, "motivationalllll", 1, 999.99));
        albums.add(new Album(m3,"Do your best",2022, Genre.Rock,"motivational", 1, 9999999.99));

        when(mockRecordShopManagerImpl.getAllAlbums()).thenReturn(albums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/recordShop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].name").value("Do your best"))
                .andExpect(jsonPath("$[1].name").value("keep going"))
                .andExpect(jsonPath("$[2].name").value("Try it, fix it"))
                .andExpect(jsonPath("$[3].name").value("Do your best"));

    }

    @Test
    public void testGetResponseDTOReturnsAlbumArtistGenreResponseDTO() throws Exception {
        List<AlbumArtistGenreResponseDTO> albumArtistGenreResponseDTOS= new ArrayList<>();
        Artist me = new Artist("me");
        Artist m3 = new Artist("m3");
        Album one = new Album(me,"Do your best", 2022, Genre.Rock, "motivational", 1, 9999999.99);
        Album two = new Album(me,"keep going", 2024, Genre.Pop,"very motivational", 12, 10.50);
        Album three = new Album(m3,"Try it, fix it",2021, Genre.Lofi, "motivationalllll", 1, 999.99);
        Album four = new Album(m3,"Do your best",2022, Genre.Rock,"motivational", 1, 9999999.99);

        albumArtistGenreResponseDTOS.add(new AlbumArtistGenreResponseDTO(one));
        albumArtistGenreResponseDTOS.add(new AlbumArtistGenreResponseDTO(two));
        albumArtistGenreResponseDTOS.add(new AlbumArtistGenreResponseDTO(three));
        albumArtistGenreResponseDTOS.add(new AlbumArtistGenreResponseDTO(four));

        when(mockRecordShopManagerImpl.getResponseDTO()).thenReturn(albumArtistGenreResponseDTOS);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/recordShop/dtoGetAlbums"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("[0].albumName").value("Do your best"))
                .andExpect(jsonPath("[1].albumName").value("keep going"))
                .andExpect(jsonPath("[2].albumName").value("Try it, fix it"))
                .andExpect(jsonPath("[3].albumName").value("Do your best"))
                .andDo(print());
    }

    @Test
    public void testPostMappingAddAlbum() throws Exception{
        Artist me = new Artist("me");
        List<String> songs2 = List.of("test 1", "test 2", "test 3", "test 4");
        Album album = new Album(me,"you got this",2090,Genre.Lofi,"slowly getting there", 1, 9.99);

        when(mockRecordShopManagerImpl.insertAlbum(any(Album.class))).thenReturn(album);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/recordShop")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(album)))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(jsonPath("$.name").value("you got this"))
                                        .andDo(MockMvcResultHandlers.print());


        verify(mockRecordShopManagerImpl, times(1)).insertAlbum(any(Album.class));

    }

    @Test
    public void testPostMappingAddAlbumWhenSubmittingDTO() throws Exception{
        Artist me = new Artist("me");
        Album album = new Album(me,"you got this",2090,Genre.Lofi,"slowly getting there", 1, 9.99);
        AlbumArtistGenreResponseDTO albumArtistGenreResponseDTO = new AlbumArtistGenreResponseDTO(album);

        when(mockRecordShopManagerImpl.insertAlbumFromDTO(any(AlbumArtistGenreResponseDTO.class))).thenReturn(albumArtistGenreResponseDTO);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/recordShop/dtoPostAlbums")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(albumArtistGenreResponseDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.albumName").value("you got this"))
                .andDo(MockMvcResultHandlers.print());


        verify(mockRecordShopManagerImpl, times(1)).insertAlbumFromDTO(any(AlbumArtistGenreResponseDTO.class));

    }

    @Test
    public void addAlbum_WithValidInput_ShouldReturnCreated() throws Exception {
        Artist me = new Artist("me");
        Album album = new Album(me, "Test Album", 2024, Genre.Rock, "Description", 10, 29.99);
        when(mockRecordShopManagerImpl.insertAlbum(any(Album.class))).thenReturn(album);

        mockMvcController.perform(post("/api/v1/recordShop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(album)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Album"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    public void addAlbum_WithNullName_ShouldReturnBadRequest() throws Exception {
        mockMvcController.perform(post("/api/v1/recordShop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":null,\"releaseYear\":2024}"))
                .andExpect(status().isBadRequest());
    }

}