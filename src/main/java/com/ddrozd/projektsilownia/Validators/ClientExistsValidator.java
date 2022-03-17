package com.ddrozd.projektsilownia.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ddrozd.projektsilownia.Services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;

public class ClientExistsValidator implements ConstraintValidator<ClientExists, Long> {

    @Autowired
    private ClientService clientService;

    @Override
    public void initialize(ClientExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long clientId, ConstraintValidatorContext context) {
        return clientService.findById(clientId).isPresent();
    }
    
}
