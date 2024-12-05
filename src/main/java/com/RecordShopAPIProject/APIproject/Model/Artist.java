package com.RecordShopAPIProject.APIproject.Model;


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
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String artistName;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    List<Album> albums = new ArrayList<>();

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public Artist(Long id, String artistName, List<Album> albums) {
        this.id = id;
        this.artistName = artistName;
        this.albums = albums;
    }

    Artist(){};

}
