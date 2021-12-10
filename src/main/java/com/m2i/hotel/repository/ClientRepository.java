package com.m2i.hotel.repository;

import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.HotelEntity;
import com.m2i.hotel.entities.ResaEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity,Integer> {
    Iterable<ClientEntity> findAll();
    public Iterable<ClientEntity> findByNomCompletContains(String nom);
}
