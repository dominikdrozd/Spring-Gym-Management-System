package com.ddrozd.projektsilownia.Repositories;

import com.ddrozd.projektsilownia.Entities.Address;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
 
    public Address findByAddressAndCityAndPostcode(String address, String city, String postcode);

}
