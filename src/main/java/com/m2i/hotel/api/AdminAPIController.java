package com.m2i.hotel.api;


import com.m2i.hotel.entities.AdminEntity;
import com.m2i.hotel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminAPIController {

    @Autowired
    AdminService as;

    public AdminAPIController( AdminService as ){
        this.as = as;
    }

    @GetMapping(value="" , produces = "application/json")
    public Iterable<AdminEntity> getAll(){
        return as.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AdminEntity> get(@PathVariable int id) {
        try{
            AdminEntity a = as.findAdmin(id);
            return ResponseEntity.ok(a);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<AdminEntity> add(@RequestBody AdminEntity a) {
        System.out.println(a);
        as.addAdmin(a);
        //creation de url d'access au nouvel objet => http://localhost:80/api/admin/20
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
        return ResponseEntity.created(uri).body(a);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public void update(@PathVariable int id, @RequestBody AdminEntity a) {
        try {
            as.editAdmin(id, a);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin introuvable");
        }
    }

    @DeleteMapping  (value="/{id}", produces = "application/json")
    public ResponseEntity<Object> delete(@PathVariable int id){
        try {
            as.delete(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}