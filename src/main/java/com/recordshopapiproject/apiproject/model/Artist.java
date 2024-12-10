package com.recordshopapiproject.apiproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Artist{

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id")
    Long id;

    @Column(nullable = false)
    String artistName;

//    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
//    final List<Album> albums = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "artist_album",
        joinColumns = @JoinColumn(name = "artist_id"),
        inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private Set<Album> albums = new HashSet<>();

    public Artist(Long id,String artistName) {
        this.id = id;
        this.artistName = artistName;
    }

//    public Artist(String artistName) {
//        this.artistName = artistName;
//    }

    Artist(){};

    public void addAlbum(Album album) {
        this.albums.add(album);
        album.getArtists().add(this);
    }

    public void removeAlbum(Album album) {
        this.albums.remove(album);
        album.getArtists().remove(this);
    }
}
