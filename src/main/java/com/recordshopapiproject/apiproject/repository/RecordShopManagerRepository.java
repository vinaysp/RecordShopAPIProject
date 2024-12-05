package com.recordshopapiproject.apiproject.repository;
import com.recordshopapiproject.apiproject.model.Album;

import com.recordshopapiproject.apiproject.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordShopManagerRepository extends CrudRepository<Album, Long> {

    Iterable<Album> findByGenre(Genre genre);
}
