package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.PasswordEquality;
import cz.ondrejpittl.rest.dtos.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordEqualityValidator implements ConstraintValidator<PasswordEquality, UserDTO> {

    public void initialize(PasswordEquality annotation) {

    }

    public boolean isValid(UserDTO user, ConstraintValidatorContext context) {
        return user.getPassword() == null || user.getPassword().equals(user.getConfirmPassword());
    }
}
