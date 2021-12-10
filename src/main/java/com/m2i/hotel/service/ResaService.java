package com.m2i.hotel.service;


import com.m2i.hotel.entities.AdminEntity;
import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.ResaEntity;
import com.m2i.hotel.repository.AdminRepository;
import com.m2i.hotel.repository.ClientRepository;
import com.m2i.hotel.repository.ResaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
public class ResaService {

    @Autowired
    private ResaRepository rr;


    @Autowired
    private ClientRepository cr;

    public Iterable<ResaEntity> findAll(int client) {
        if( client > 0){
            return rr.findByClient_Id(client);
        }
        return rr.findAll();
    }

    public ResaEntity findResa(int id) {
        return rr.findById(id).get();
    }

    public void addResa(ResaEntity r) throws Exception {
        int numChambre=r.getNumChambre();

        checkResa(0, numChambre, r);
        rr.save(r);
    }

    private void checkResa( int id, int numChambre,ResaEntity r ) throws Exception {

        Date after = (Date) r.getDatefin().clone();
        after.setTime(after.getTime()+ TimeUnit.DAYS.toDays(1));
        Date before = (Date) r.getDatedeb().clone();
        before.setTime(before.getTime() - TimeUnit.DAYS.toDays(1));

        System.out.println( "Je check les resas entre " + before + " et " + after );

        Iterable<ResaEntity> retourCheckDeb = rr.findByIdNotAndNumChambreAndDatedebBetween( id,numChambre,before , after );
        Iterable<ResaEntity> retourCheckFin = rr.findByIdNotAndNumChambreAndDatefinBetween(id,numChambre, before , after );

        if( retourCheckDeb.iterator().hasNext() || retourCheckFin.iterator().hasNext()
                || retourCheckDeb.iterator().hasNext() && retourCheckFin.iterator().hasNext()){
            throw new Exception("Resa en conflit avec d'autres resas");
        }

    }

    public void editResa(int id, ResaEntity r) throws Exception {

        checkResa(id, r.getNumChambre(), r);
        try{ResaEntity rExistant=rr.findById(id).get();
            rExistant.setHotel(r.getHotel());
            rExistant.setClient(r.getClient());
            rExistant.setDatedeb(r.getDatedeb());
            rExistant.setDatefin(r.getDatefin());
            rExistant.setNumChambre(r.getNumChambre());

            rr.save(rExistant);}
        catch (NoSuchElementException e){
            throw e;
        }
    }

    public void delete(int id) {
        rr.deleteById(id);
    }
}
