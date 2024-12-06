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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

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
        List<String> songs2 = List.of("test 1", "test 2", "test 3", "test 4");
        List<String> songs1 = List.of("test 1", "test 2", "test 3");
        List<String> songs3 = List.of("test 1", "test 2");
        List<String> songs4 = List.of("test 1");
        albums.add(new Album(1L,"Do your best", me, 2022, Genre.Rock, songs1, "motivational", 1, 9999999.99));
        albums.add(new Album(2L,"keep going", me, 2024, Genre.Pop, songs2, "very motivational", 12, 10.50));
        albums.add(new Album(3L,"Try it, fix it", me, 2021, Genre.Lofi, songs3, "motivationalllll", 1, 999.99));
        albums.add(new Album(4L,"Do your best", m3, 2022, Genre.Rock, songs1, "motivational", 1, 9999999.99));

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


}