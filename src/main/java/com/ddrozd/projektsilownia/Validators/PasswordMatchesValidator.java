package com.ddrozd.projektsilownia.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ddrozd.projektsilownia.Forms.EmployeeRegisterForm;
import com.ddrozd.projektsilownia.Forms.ClientRegisterForm;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context){

        boolean isValid = true;

        if(object.getClass().getSimpleName().equals("ClientRegisterForm")) {
            ClientRegisterForm urf = ClientRegisterForm.class.cast(object);
            isValid = urf.getPassword().equals(urf.getPasswordRepeat());
        } else if (object.getClass().getSimpleName().equals("EmployeeRegisterForm")) {
            EmployeeRegisterForm erf = EmployeeRegisterForm.class.cast(object);
            isValid = erf.getPassword().equals(erf.getPasswordRepeat());
        } else {
            isValid = true;
        }

        if(!isValid){
            context.disableDefaultConstraintViolation();;
            context
                .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("password")
                .addConstraintViolation();
        }
        return isValid;
    }

}