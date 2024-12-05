package com.RecordShopAPIProject.APIproject.Model;


import jakarta.persistence.*;
import jakarta.persistence.Entity;


@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(name = "artist_name")
    String artistName;

    public Artist(Long id, String artistName){
        this.id = id;
        this.artistName = artistName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
