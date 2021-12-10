package com.m2i.hotel.service;


import com.m2i.hotel.entities.AdminEntity;
import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.repository.AdminRepository;
import com.m2i.hotel.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AdminService {

    @Autowired
    private AdminRepository ar;


    public Iterable<AdminEntity> findAll() {
        return ar.findAll();
    }

    public AdminEntity findAdmin(int id) {
        return ar.findById(id).get();
    }

    public void addAdmin(AdminEntity a) {

        ar.save(a);
        if( a.getPhotouser().length() == 0)
            a.setPhotouser( "default.jpg" );

        ar.save(a);
    }


    public void editAdmin(int id, AdminEntity a) {

        try{AdminEntity aExistant=ar.findById(id).get();
            aExistant.setUsername(a.getUsername());
            aExistant.setPassword(a.getPassword());
            aExistant.setRole(a.getRole());

            ar.save(aExistant);}
        catch (NoSuchElementException e){
            throw e;
        }
    }

    public void editProfil( int id , AdminEntity a) throws NoSuchElementException {
        try{
            AdminEntity aExistant = ar.findById(id).get();

            aExistant.setUsername( a.getUsername() );

            System.out.println( "------------------" );
            System.out.println( a.getPhotouser() );
            System.out.println( "*************" );

            if( a.getPhotouser() == null )
                aExistant.setPhotouser( "default.jpg" );
            else
                aExistant.setPhotouser( a.getPhotouser() );

            ar.save( aExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }

    public void delete(int id) {
        ar.deleteById(id);
    }
}
