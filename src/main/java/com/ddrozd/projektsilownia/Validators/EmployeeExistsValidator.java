package com.ddrozd.projektsilownia.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ddrozd.projektsilownia.Services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;

public class EmployeeExistsValidator implements ConstraintValidator<ClientExists, Long> {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void initialize(ClientExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long employeeId, ConstraintValidatorContext context) {
        return employeeService.findById(employeeId).isPresent();
    }
    
}
