package com.RecordShopAPIProject.APIproject.repository;
import com.RecordShopAPIProject.APIproject.model.Album;

import com.RecordShopAPIProject.APIproject.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordShopManagerRepository extends CrudRepository<Album, Long> {

    Iterable<Album> findByGenre(Genre genre);
}
