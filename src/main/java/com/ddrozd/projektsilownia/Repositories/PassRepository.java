package com.ddrozd.projektsilownia.Repositories;

import java.util.List;

import com.ddrozd.projektsilownia.Entities.Pass;

import org.springframework.data.repository.CrudRepository;

public interface PassRepository extends CrudRepository<Pass, Long> {
    
    public List<Pass> findAllByOrderByIdAsc();

}
