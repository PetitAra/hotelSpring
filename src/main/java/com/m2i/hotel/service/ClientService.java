package com.m2i.hotel.service;


import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.entities.ResaEntity;
import com.m2i.hotel.repository.ClientRepository;
import com.m2i.hotel.repository.HotelRepository;
import com.m2i.hotel.repository.ResaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class ClientService {

    @Autowired
    private ClientRepository cr;

    @Autowired
    private ResaRepository rr;

    public Iterable<ClientEntity> findAll() {
        return cr.findAll();
    };

    public Iterable<ClientEntity> findAll(String search) {
        if(search!=null){
            return cr.findByNomCompletContains(search);
        }
        return cr.findAll();
    }

    public ClientEntity findClient(int id) {
        return cr.findById(id).get();
    }

    public static boolean validate(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    private void checkClient(ClientEntity c) throws InvalidObjectException {
        if(c.getNomComplet().length()<=2){
            throw new InvalidObjectException("Nom de Patient invalide");
        }
        if(c.getAdresse().length()<10){
            throw new InvalidObjectException("Adresse invalide");
        }
        if (!validate(c.getEmail()) || c.getEmail().length()<5) {
            throw new InvalidObjectException("Email invalide");
        }
        if(c.getTelephone().length()<8){
            throw new InvalidObjectException("Telephone invalide");
        }

    }


    public void addClient(ClientEntity c) throws InvalidObjectException {

        checkClient(c);
        cr.save(c);
    }


    public void editClient(int id, ClientEntity c) throws InvalidObjectException {
        checkClient(c);
        try{ClientEntity cExistant=cr.findById(id).get();
            cExistant.setNomComplet(c.getNomComplet());
            cExistant.setAdresse(c.getAdresse());
            cExistant.setEmail(c.getEmail());
            cExistant.setTelephone(c.getTelephone());

            cr.save(cExistant);}
        catch (NoSuchElementException e){
            throw e;
        }
    }

    public void checkResas(int id) throws Exception {
        System.out.println(id);
        Iterable<ResaEntity> resas = rr.findByClient_Id(id);
        if (resas. iterator(). hasNext() ==true ){
            System.out.println(resas);
            throw new Exception("Impossible de supprimer: le client a des reservations en cours.");
        }

    }

    public void delete(int id) throws Exception {
        checkResas(id);
        cr.deleteById(id);
    }
}
