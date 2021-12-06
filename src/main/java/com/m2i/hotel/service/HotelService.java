package com.m2i.hotel.service;

import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HotelService {

    private HotelRepository hr;


    public Iterable<HotelEntity> findAll() {
        return hr.findAll();
    }

    public HotelEntity findHotel(int id) {
        return hr.findById(id).get();
    }

    public void addHotel(HotelEntity h) {
        hr.save(h);
    }


    public void editHotel(int id, HotelEntity h) {

        try{HotelEntity hExistant=hr.findById(id).get();
        hExistant.setNom(h.getNom());
        hExistant.setAdresse(h.getAdresse());
        hExistant.setEmail(h.getEmail());
        hExistant.setEtoiles(h.getEtoiles());
        hExistant.setTelephone(h.getTelephone());
        hExistant.setVille(h.getVille());

        hr.save(hExistant);}
        catch (NoSuchElementException e){
            throw e;
        }
    }

    public void delete(int id) {
        hr.deleteById(id);
    }
}
