package com.m2i.hotel.repository;

import com.m2i.hotel.api.AdminAPIController;
import com.m2i.hotel.entities.AdminEntity;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {
    Iterable<AdminEntity> findAll();

    public AdminEntity findByUsername(String username);
}
