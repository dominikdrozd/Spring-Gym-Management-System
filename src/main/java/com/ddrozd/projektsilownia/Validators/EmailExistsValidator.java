package com.ddrozd.projektsilownia.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ddrozd.projektsilownia.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public class EmailExistsValidator implements ConstraintValidator<EmailExists, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userService.isEmailUsed(email);
    }
    
}
