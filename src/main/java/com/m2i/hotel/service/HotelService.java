package com.m2i.hotel.service;

import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hr;


    public Iterable<HotelEntity> findAll() {
        return hr.findAll();
    }

    public Iterable<HotelEntity> findAll(String search) {
        if(search!=null){
            return hr.findByNomContains(search);
        }
        return hr.findAll();
    }

    public HotelEntity findHotel(int id) {
        return hr.findById(id).get();
    }

    public static boolean validate(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private void checkHotel(HotelEntity h) throws InvalidObjectException {
        if(h.getNom().length()<=2){
            throw new InvalidObjectException("Nom d'hotel invalide");
        }
        if(h.getAdresse().length()<10){
            throw new InvalidObjectException("Adresse invalide");
        }
        if (!validate(h.getEmail()) || h.getEmail().length()<5) {
            throw new InvalidObjectException("Email invalide");
        }
        if(Integer.toString(h.getTelephone()).length()<8){
            throw new InvalidObjectException("Telephone invalide");
        }
        if(h.getVille().length()<2){
            throw new InvalidObjectException("Ville invalide");
        }
        if(h.getEtoiles()<1 || h.getEtoiles()>5){
            throw new InvalidObjectException("Nombre des etoiles entre 0 et 5");
        }

    }

    public void addHotel(HotelEntity h) throws InvalidObjectException {
        checkHotel(h);
        hr.save(h);
    }


    public void editHotel(int id, HotelEntity h) throws InvalidObjectException {

        checkHotel(h);
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
