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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    Long id;

    @Column
    String artistName;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    final List<Album> albums = new ArrayList<>();

    public Artist(Long id,String artistName) {
        this.id = id;
        this.artistName = artistName;
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    Artist(){};

}
