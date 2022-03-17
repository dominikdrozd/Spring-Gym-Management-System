package com.ddrozd.projektsilownia.Repositories;

import com.ddrozd.projektsilownia.Entities.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail(String email);

    public int countByEmail(String email);

}
