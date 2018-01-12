package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.PasswordEquality;
import cz.ondrejpittl.business.annotations.UniqueEmail;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Inject
    private UserService userService;

    public void initialize(UniqueEmail annotation) {

    }

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return this.userService.checkEmailAvailability(email);
    }
}
