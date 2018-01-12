package cz.ondrejpittl.business.annotations;

import cz.ondrejpittl.business.validation.PasswordEqualityValidator;
import cz.ondrejpittl.business.validation.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "E-mail is already taken.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}