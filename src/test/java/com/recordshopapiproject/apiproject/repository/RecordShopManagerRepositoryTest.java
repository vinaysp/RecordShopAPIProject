package com.recordshopapiproject.apiproject.repository;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.model.Artist;
import com.recordshopapiproject.apiproject.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class RecordShopManagerRepositoryTest {

    @Autowired
    private RecordShopManagerRepository recordShopManagerRepository;

    @Test
    public void testFindAllAlbumsReturnsAlbums(){
        //Arrange
        Artist coldplay = new Artist("Coldplay");
        Album album = new Album("X and Y", coldplay, 2008, Genre.Pop,"great album",5,9.99);
        recordShopManagerRepository.save(album);

        //Act
        Iterable<Album> albums = recordShopManagerRepository.findAll();

        //Assert
        assertThat(albums).hasSize(1);
    }







}