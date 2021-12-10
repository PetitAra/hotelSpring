package com.m2i.hotel.api;


import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.ResaEntity;
import com.m2i.hotel.service.ClientService;
import com.m2i.hotel.service.ResaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/resa")
public class ResaAPIController {


    @Autowired
    ResaService resaService;

    @GetMapping(value="" , produces = "application/json")
    public ResponseEntity<Iterable<ResaEntity>> getAll(@RequestParam(name = "client", required = false, defaultValue = "0") int client){

        System.out.println( "\nVal recherchée = client =  "+ client);
        return ResponseEntity.ok().body(resaService.findAll(client));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ResaEntity> get(@PathVariable int id) {
        try{
            ResaEntity r = resaService.findResa(id);
            return ResponseEntity.ok(r);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<ResaEntity> add(@RequestBody ResaEntity r) throws Exception {
        System.out.println(r);
        resaService.addResa(r);
        //creation de url d'access au nouvel objet => http://localhost:80/api/hotel/20
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(r.getId()).toUri();
        return ResponseEntity.created(uri).body(r);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable int id, @RequestBody ResaEntity r) {
        try {
            resaService.editResa(id, r);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resa introuvable");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());        }
    }

    @DeleteMapping  (value="/{id}", produces = "application/json")
    public ResponseEntity<Object> delete(@PathVariable int id){
        try {
           resaService.delete(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
