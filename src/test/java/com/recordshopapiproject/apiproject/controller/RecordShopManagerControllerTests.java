package com.recordshopapiproject.apiproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RecordShopManagerControllerTests {

    @Mock
    private RecordShopManagerServiceImplementation mockRecordShopManagerImpl;

    @InjectMocks
    private RecordShopManagerController recordShopManagerController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mockMvcController = MockMvcBuilders.standaloneSetup(recordShopManagerController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllAlbumsReturnsAlbums() throws Exception {

        List<Album> albums = new ArrayList<>();
        Artist me = new Artist(1L,"me");
        Artist m3 = new Artist(2L,"m3");
        albums.add(new Album(1L,"Do your best", 2022, Genre.Rock, "motivational", 1, 9999999.99));
        albums.add(new Album(2L,"keep going", 2024, Genre.Pop,"very motivational", 12, 10.50));
        albums.add(new Album(3L,"Try it, fix it",2021, Genre.Lofi, "motivationalllll", 1, 999.99));
        albums.add(new Album(4L,"Do your best",2022, Genre.Rock,"motivational", 1, 9999999.99));

        when(mockRecordShopManagerImpl.getAllAlbums()).thenReturn(albums);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/recordShop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Do your best"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("keep going"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Try it, fix it"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].id").value(4L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value("Do your best"));

    }

    @Test
    public void testPostMappingAddAlbum() throws Exception{
        Artist me = new Artist(1L,"me");
        List<String> songs2 = List.of("test 1", "test 2", "test 3", "test 4");
        Album album = new Album(3L,"you got this",2090,Genre.Lofi,"slowly getting there", 1, 9.99);

        when(mockRecordShopManagerImpl.insertAlbum(any(Album.class))).thenReturn(album);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/recordShop")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(album)))
                                .andExpect(MockMvcResultMatchers.status().isCreated())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3L))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("you got this"))
                                        .andDo(MockMvcResultHandlers.print());


        verify(mockRecordShopManagerImpl, times(1)).insertAlbum(any(Album.class));

    }

    @Test
    public void addAlbum_WithValidInput_ShouldReturnCreated() throws Exception {
        Album album = new Album(1L, "Test Album", 2024, Genre.Rock, "Description", 10, 29.99);
        when(mockRecordShopManagerImpl.insertAlbum(any(Album.class))).thenReturn(album);

        mockMvcController.perform(post("/api/v1/recordShop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(album)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("album"));
    }

    @Test
    public void addAlbum_WithNullName_ShouldReturnBadRequest() throws Exception {
        mockMvcController.perform(post("/api/v1/recordShop")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":null,\"releaseYear\":2024}"))
                .andExpect(status().isBadRequest());
    }

}