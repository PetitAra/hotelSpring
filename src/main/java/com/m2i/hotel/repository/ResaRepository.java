package com.m2i.hotel.repository;

import com.m2i.hotel.entities.ClientEntity;
import com.m2i.hotel.entities.ResaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface ResaRepository extends CrudRepository<ResaEntity, Integer> {
    Iterable<ResaEntity> findAll();

   public Iterable<ResaEntity> findByClient_Id(int client);

    Iterable<ResaEntity> findByDatedebBetween(Date before, Date after);
    Iterable<ResaEntity> findByDatefinBetween(Date before, Date after);

    public Iterable<ResaEntity> findByIdNotAndNumChambreAndDatedebBetween(int id,int numChambre, Date before, Date after );
    public Iterable<ResaEntity> findByIdNotAndNumChambreAndDatefinBetween(int id,int numChambre, Date before, Date after );

    //AndDatedebBetween(int id, Date before, Date after)
}
