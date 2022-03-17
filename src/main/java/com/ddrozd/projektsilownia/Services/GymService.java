package com.ddrozd.projektsilownia.Services;

import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Employee;
import com.ddrozd.projektsilownia.Entities.Gym;
import com.ddrozd.projektsilownia.Repositories.GymRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymService {

    @Autowired
    private GymRepository gymRepository;

    public List<Gym> findAll() {
        return gymRepository.findAllByOrderByIdAsc();
    }

    public List<Gym> findAvailableGyms(Employee employee) {
        List<Gym> all = findAll();
        all.removeAll(employee.getGyms());
        return all;
    }

    public boolean doesGymExists(Long gymId) {
        int gymCount = gymRepository.countById(gymId);
        boolean exists = gymCount > 0;
        return exists;
    }

    public Optional<Gym> findById(Long id) {
        return gymRepository.findById(id);
    }

    public void save(Gym gym) {
        gymRepository.save(gym);
    }

    public int countWhereAddressId(long addressId) {
        return gymRepository.countByAddressId(addressId);
    }

    public boolean addEmployeeIfNotAlreadyIn(Gym gym, Employee employee) {
        if (gym.getEmployees().contains(employee)) {
            return false;
        }
        gym.getEmployees().add(employee);
        save(gym);
        return true;
    }

    public void removeEmployee(Gym gym, Employee employee) {
        gym.getEmployees().remove(employee);
        save(gym);
    }

    public Gym registerGym(Address address, String name, String phone) {
        Gym gym = new Gym();

        gym.setAddress(address);
        gym.setName(name);
        gym.setPhone(phone);
        save(gym);

        return gym;
    }

    public Gym updateGym(Gym gym, Address address, String name, String phone) {
        gym.setName(name);
        gym.setAddress(address);
        gym.setPhone(phone);
        save(gym);
        
        return gym;
    }

    public void delete(Gym gym) {
        gym.getEmployees().clear();
        gymRepository.save(gym);
        gymRepository.delete(gym);
    }

    public void deleteEmployeeFromGyms(Employee employee) {
        List<Gym> gyms = employee.getGyms();

        for(Gym gym : gyms) {
            gym.getEmployees().remove(employee);
        }
    }
    
}
