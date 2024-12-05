package com.RecordShopAPIProject.APIproject.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
    Artist artist;

    @Column
    int releaseYear;

    @Column
    Genre genre;

    @Column
    String description;

    public Album(String name, Artist artist, int releaseYear, Genre genre, String description) {
        this.name = name;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
    }

    public Album(Long id, String name, Artist artist, int releaseYear, Genre genre, String description) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
    }

    Album(){};

}
