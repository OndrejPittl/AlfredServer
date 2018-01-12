package cz.ondrejpittl.business.annotations;

import cz.ondrejpittl.business.validation.PasswordEqualityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)   // class
//@Target({ FIELD, ANNOTATION_TYPE })   // class attribute
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordEqualityValidator.class)
public @interface PasswordEquality {
    String message() default "Passwords must match!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}