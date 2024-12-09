package com.recordshopapiproject.apiproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
public class Album {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
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

    @Column
    int stock;

    @Column
    double price;


    public Album(long id,String name, Artist artist, int releaseYear, Genre genre, String description, int stock, double price) {
        this.id = id;
        this.name = name;
        this.artist = artist;
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


}
