package com.recordshopapiproject.apiproject.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Artist{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String artistName;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    List<Album> albums = new ArrayList<>();

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    Artist(){};

}
