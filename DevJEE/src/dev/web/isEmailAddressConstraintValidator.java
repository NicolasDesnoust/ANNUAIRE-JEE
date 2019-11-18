package dev.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.EmailValidator;

public class isEmailAddressConstraintValidator implements ConstraintValidator<isEmailAddress, String> {

    @Override
    public void initialize(isEmailAddress arg0) {
    }

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
    	EmailValidator validator = EmailValidator.getInstance();
    	if (validator.isValid(arg0))
    	   return true;

        return false;
    }

}