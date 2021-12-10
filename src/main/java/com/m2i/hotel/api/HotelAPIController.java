package com.m2i.hotel.api;


import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    HotelService hs;

    @GetMapping(value="" , produces = "application/json")
    public Iterable<HotelEntity> getAll(HttpServletRequest request){
        String search = request.getParameter("search");
        System.out.println("Recherche d'hotel avec param " +search);
        return hs.findAll(search);
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
    public ResponseEntity<HotelEntity> add(@RequestBody HotelEntity h) throws InvalidObjectException {
        System.out.println(h);
        try {
            hs.addHotel(h);
            //creation de url d'access au nouvel objet => http://localhost:80/api/hotel/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(h.getId()).toUri();
            return ResponseEntity.created(uri).body(h);
        }catch ( Exception e ){
            System.out.println(e.getMessage());
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable int id, @RequestBody HotelEntity h) {
        try {
            hs.editHotel(id, h);
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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


