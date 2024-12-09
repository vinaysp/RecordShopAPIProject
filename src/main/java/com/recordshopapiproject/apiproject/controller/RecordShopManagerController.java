package com.recordshopapiproject.apiproject.controller;

import com.recordshopapiproject.apiproject.model.Album;
import com.recordshopapiproject.apiproject.service.RecordShopManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/recordShop")
public class RecordShopManagerController {

    @Autowired
    RecordShopManagerService recordShopManagerService;

    @GetMapping
    public ResponseEntity<Iterable<Album>>getAlbums(){
        Iterable<Album> albums;
        albums = recordShopManagerService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @PostMapping
    public ResponseEntity<Album> addAlbum(@RequestBody Album album){
        Album newAlbum = recordShopManagerService.insertAlbum(album);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("album","/api/v1/recordShop/"+newAlbum.getId().toString());
        return new ResponseEntity<>(newAlbum, httpHeaders, HttpStatus.CREATED);
    }

}
