package com.recordshopapiproject.apiproject.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column
    String imageUrl;

    public Album(Artist artist, String name, int releaseYear, Genre genre,
                 String description, int stock, double price, String imageUrl) {
        this.artist = artist;
        this.name = name;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Album(){};


}
