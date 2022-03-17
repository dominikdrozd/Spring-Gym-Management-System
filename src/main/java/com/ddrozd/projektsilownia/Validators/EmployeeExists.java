package com.ddrozd.projektsilownia.Validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeExistsValidator.class)
@Documented
public @interface EmployeeExists {
    String message() default "Employee doesn't exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
