package com.recordshopapiproject.apiproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Artist{

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    Long id;

    @Column(nullable = false)
    String artistName;

//    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
//    final List<Album> albums = new ArrayList<>();
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//        name = "artist_album",
//        joinColumns = @JoinColumn(name = "artist_id"),
//        inverseJoinColumns = @JoinColumn(name = "album_id")
//    )
    @JsonBackReference
    @OneToMany(mappedBy = "artist")
    private List<Album> albums = new ArrayList<>();

    public Artist(Long id,String artistName) {
        this.id = id;
        this.artistName = artistName;
    }

//    public Artist(String artistName) {
//        this.artistName = artistName;
//    }

    Artist(){};

//    public void addAlbum(Album album) {
//        this.albums.add(album);
//        album.getArtist().add(this);
//    }
//
//    public void removeAlbum(Album album) {
//        this.albums.remove(album);
//        album.getArtist().remove(this);
//    }
}
