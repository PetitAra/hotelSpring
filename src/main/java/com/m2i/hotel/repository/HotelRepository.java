package com.m2i.hotel.repository;

import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.HotelEntity;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<HotelEntity, Integer> {
    Iterable<HotelEntity> findAll();
    public Iterable<HotelEntity> findByNomContains(String nom);
}
