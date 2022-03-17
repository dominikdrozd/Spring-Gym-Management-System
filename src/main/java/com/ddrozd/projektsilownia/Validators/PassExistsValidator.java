package com.ddrozd.projektsilownia.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ddrozd.projektsilownia.Services.PassService;

import org.springframework.beans.factory.annotation.Autowired;

public class PassExistsValidator implements ConstraintValidator<PassExists, Long> {

    @Autowired
    private PassService passService;

    @Override
    public void initialize(PassExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long passId, ConstraintValidatorContext context) {
        return passService.findById(passId).isPresent();
    }
    
}
