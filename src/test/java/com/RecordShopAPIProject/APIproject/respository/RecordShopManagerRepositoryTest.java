package com.RecordShopAPIProject.APIproject.respository;

import com.RecordShopAPIProject.APIproject.Model.Album;
import com.RecordShopAPIProject.APIproject.Model.Artist;
import com.RecordShopAPIProject.APIproject.Model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class RecordShopManagerRepositoryTest {

    @Autowired
    private RecordShopManagerRepository recordShopManagerRepository;

    @Test
    public void testFindAllAlbumsReturnsAlbums(){
        //Arrange
        Artist coldplay = new Artist("Coldplay");
        Album album = new Album("X and Y", coldplay, 2008, Genre.Pop,"great album");
        recordShopManagerRepository.save(album);

        //Act
        Iterable<Album> albums = recordShopManagerRepository.findAll();

        //Assert
        assertThat(albums).hasSize(1);
    }







}