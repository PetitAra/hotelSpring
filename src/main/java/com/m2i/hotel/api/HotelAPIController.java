package com.m2i.hotel.api;


import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hotel")
public class HotelAPIController {

    HotelService hs;

    @GetMapping(value="" , produces = "application/json")
    public Iterable<HotelEntity> getAll(HttpServletRequest request){
        return hs.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<HotelEntity> get(@PathVariable int id) {
        try{
            HotelEntity h = hs.findHotel(id);
            return ResponseEntity.ok(h);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<HotelEntity> add(@RequestBody HotelEntity h) {
        System.out.println(h);
        hs.addHotel(h);
        //creation de url d'access au nouvel objet => http://localhost:80/api/hotel/20
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(h.getId()).toUri();
        return ResponseEntity.created(uri).body(h);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable int id, @RequestBody HotelEntity h) {
        try {
            hs.editHotel(id, h);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel introuvable");
        }
    }

    @DeleteMapping  (value="/{id}", produces = "application/json")
    public ResponseEntity<Object> delete(@PathVariable int id){
        try {
            hs.delete(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}


