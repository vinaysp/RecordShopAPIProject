package com.recordshopapiproject.apiproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Artist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column
    Long id;

    @Column(nullable = false)
    String artistName;

    @JsonBackReference
    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public Artist(){};

    public void addAlbum(Album album) {
        albums.add(album);
        album.setArtist(this);
    }

    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setArtist(null);
    }
}
