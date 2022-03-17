package com.ddrozd.projektsilownia.Services;

import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Exceptions.UserExistsException;
import com.ddrozd.projektsilownia.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(String email, String password, String role) throws UserExistsException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        User existingUser = userRepository.findByEmail(email);

        if(existingUser != null) {
            throw new UserExistsException("User already exists.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(role);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }

    public boolean isEmailUsed(String email) {
        int emailCount = userRepository.countByEmail(email);
        boolean isUsed = emailCount > 0;
        return isUsed;
    }
    
}
