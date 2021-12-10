package com.m2i.hotel.api;


import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.service.ClientService;
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
@RequestMapping("/api/client")
public class ClientAPIController {

    @Autowired
    ClientService cs;

    @GetMapping(value="" , produces = "application/json")
    public Iterable<ClientEntity> getAll(HttpServletRequest request){
        String search = request.getParameter("search");
        System.out.println("Recherche de client avec param " +search);
        return cs.findAll(search);}

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClientEntity> get(@PathVariable int id) {
        try{
            ClientEntity c = cs.findClient(id);
            return ResponseEntity.ok(c);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<ClientEntity> add(@RequestBody ClientEntity c) {
        System.out.println(c);
        try{ cs.addClient(c);
        //creation de url d'access au nouvel objet => http://localhost:80/api/hotel/20
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(c.getId()).toUri();
        return ResponseEntity.created(uri).body(c);
        }catch ( Exception e ){
            System.out.println(e.getMessage());
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable int id, @RequestBody ClientEntity c) {
        try {
            cs.editClient(id, c);
        }catch ( Exception e ){
        System.out.println(e.getMessage());
        throw new ResponseStatusException( HttpStatus.BAD_REQUEST , e.getMessage() );
    }
    }

    @DeleteMapping  (value="/{id}", produces = "application/json")
    public ResponseEntity<Object> delete(@PathVariable int id){
        try {
            cs.delete(id);
            return ResponseEntity.ok(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());        }
    }

}