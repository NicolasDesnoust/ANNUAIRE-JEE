package dev.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.UrlValidator;

public class isWebsiteConstraintValidator implements ConstraintValidator<isWebsite, String> {

    @Override
    public void initialize(isWebsite arg0) {
    }

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
    	UrlValidator validator = UrlValidator.getInstance();
    	if (validator.isValid(arg0))
    	   return true;

        return false;
    }
    
}