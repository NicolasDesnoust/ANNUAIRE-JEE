package dev.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = isEmailAddressConstraintValidator.class)
@Documented
public @interface isEmailAddress {

    String message() default "{person.warn.email.address}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}