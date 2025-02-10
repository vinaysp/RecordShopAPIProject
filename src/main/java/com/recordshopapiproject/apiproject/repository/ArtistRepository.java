package com.recordshopapiproject.apiproject.repository;

import com.recordshopapiproject.apiproject.model.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Optional<Artist> findByArtistName(String artistName);
}
