package com.m2i.hotel.repository;

import com.m2i.hotel.entities.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity,Integer> {
}
