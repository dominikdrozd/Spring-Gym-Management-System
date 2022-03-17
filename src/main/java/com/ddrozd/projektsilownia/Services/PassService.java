package com.ddrozd.projektsilownia.Services;

import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Pass;
import com.ddrozd.projektsilownia.Repositories.PassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassService {

    @Autowired
    private PassRepository passRepository;

    public List<Pass> findAll() {
        return passRepository.findAllByOrderByIdAsc();
    }

    public Optional<Pass> findById(Long id) {
        return passRepository.findById(id);
    }

    public Pass registerPass(String name, double price, int expiryTime) {
        Pass pass = new Pass();

        pass.setName(name);
        pass.setPrice(price);
        pass.setExpiryTime(expiryTime);
        save(pass);

        return pass;
    }

    public void save(Pass pass) {
        passRepository.save(pass);
    }
    
    public void update(Pass pass, String name, Double price, int expiryTime) {
        pass.setName(name);
        pass.setPrice(price);
        pass.setExpiryTime(expiryTime);
        save(pass);
    }

    public void delete(Pass pass) {
        passRepository.delete(pass);
    }
}
