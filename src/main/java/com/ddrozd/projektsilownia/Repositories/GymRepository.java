package com.ddrozd.projektsilownia.Repositories;

import java.util.List;

import com.ddrozd.projektsilownia.Entities.Gym;

import org.springframework.data.repository.CrudRepository;


public interface GymRepository extends CrudRepository<Gym, Long> {
    
    public List<Gym> findAllByOrderByIdAsc();
    public int countById(Long id);
    public int countByAddressId(long addressId);

}
