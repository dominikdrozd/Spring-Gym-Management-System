package com.ddrozd.projektsilownia.Repositories;

import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Client;

import org.springframework.data.repository.CrudRepository;


public interface ClientRepository extends CrudRepository<Client, Long> {

    public List<Client> findAllByOrderByIdAsc();
    public int countByAddressId(long addressId);
    public Optional<Client> findByUserId(Long id);
    
}
