package cz.ondrejpittl.business.annotations;

import cz.ondrejpittl.business.validation.ExistingPostValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingPostValidator.class)
@Documented
public @interface ExistingPost {
    String message () default "Post does not exist.";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};
}