package com.recordshopapiproject.apiproject.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class Album {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    Long id;

    @Column (nullable = false)
    String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Column
    int releaseYear;

    @Column
    @Enumerated(EnumType.STRING)
    Genre genre;

    @Column
    String description;

    @Column
    int stock;

    @Column
    double price;


//    public Album(long id,String name, Artist artist, int releaseYear, Genre genre, String description, int stock, double price) {
//        this.id = id;
//        this.name = name;
//        this.artist = artist;
//        this.releaseYear = releaseYear;
//        this.genre = genre;
//        this.description = description;
//        this.stock = stock;
//        this.price = price;
//    }

    public Album(long id, String name, int releaseYear, Genre genre,
                 String description, int stock, double price) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

//    public Album(String name, Artist artist, int releaseYear, Genre genre,String description, int stock, double price) {
//        this.name = name;
//        this.artist = artist;
//        this.releaseYear = releaseYear;
//        this.genre = genre;
//        this.description = description;
//        this.stock = stock;
//        this.price = price;
//    }

    public Album(){};

//    // Helper method to manage the relationship
//    public void addArtist(Artist artist) {
//        this.artist.add(artist);
//        artist.getAlbums().add(this);
//    }
//
//    public void removeArtist(Artist artist) {
//        this.artists.remove(artist);
//        artist.getAlbums().remove(this);
//    }

}
