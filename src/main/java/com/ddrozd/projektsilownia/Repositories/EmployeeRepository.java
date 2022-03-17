package com.ddrozd.projektsilownia.Repositories;

import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Employee;

import org.springframework.data.repository.CrudRepository;


public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public int countByAddressId(long addressId);
    public List<Employee> findAll();
    public Optional<Employee> findByUserId(Long id);
    
}
