package com.ddrozd.projektsilownia.Services;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Repositories.AddressRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private GymService gymService;

    public Address getAddressOrRegister(String addr, String city, String postcode) {
        
        Address currentAddress = addressRepository.findByAddressAndCityAndPostcode(
            addr,
            city,
            postcode
        );

        if(currentAddress != null) {
            return currentAddress;
        }

        return registerAddress(addr, city, postcode);
    }

    public Address registerAddress(String addr, String city, String postcode) {

        Address address = new Address();
        address.setAddress(addr);
        address.setCity(city);
        address.setPostcode(postcode);
        addressRepository.save(address);
        
        return address;
    }

    public boolean deleteIfNotUsed(Address address) {            
        int employeesWithAddress = employeeService.countWhereAddressId(address.getId());
        int clientsWithAddress = clientService.countWhereAddressId(address.getId());
        int gymWithAddress = gymService.countWhereAddressId(address.getId());
        int sum = employeesWithAddress + clientsWithAddress + gymWithAddress;

        if(sum > 0){
            return false;
        }

        addressRepository.delete(address);

        return true;
    }
    
    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
