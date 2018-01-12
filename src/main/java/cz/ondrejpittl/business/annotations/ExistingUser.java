package cz.ondrejpittl.business.annotations;

import cz.ondrejpittl.business.validation.ExistingPostValidator;
import cz.ondrejpittl.business.validation.ExistingUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingUserValidator.class)
@Documented
public @interface ExistingUser {
    String message() default "User does not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}