package com.RecordShopAPIProject.APIproject.respository;
import com.RecordShopAPIProject.APIproject.Model.Album;

import com.RecordShopAPIProject.APIproject.Model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordShopManagerRepository extends CrudRepository<Album, Long> {

    Iterable<Album> findByGenre(Genre genre);
}
