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

        if (album.getName() == null || album.getName().trim().isEmpty()){
        return ResponseEntity.badRequest().build();
        }

        if (album.getPrice()<0){
            return ResponseEntity.badRequest().build();
        }

        Album newAlbum = recordShopManagerService.insertAlbum(album);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (newAlbum.getId() != null) {
            httpHeaders.add("album", String.format("/api/v1/recordShop/%d", newAlbum.getId()));}
        return new ResponseEntity<>(newAlbum, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long albumId, @RequestBody Album albumDetails) throws Exception {
        Album updatedAlbum = recordShopManagerService.updateAlbum(albumId, albumDetails);
        return ResponseEntity.ok(updatedAlbum);
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<Album> deleteAlbumByID(@PathVariable("ID") Long ID){
        recordShopManagerService.deleteAlbum(ID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) throws Exception {
        Album album = recordShopManagerService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

}
