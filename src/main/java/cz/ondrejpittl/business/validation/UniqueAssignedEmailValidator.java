package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.UniqueAssignedEmail;
import cz.ondrejpittl.business.annotations.UniqueEmail;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.persistence.domain.User;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueAssignedEmailValidator implements ConstraintValidator<UniqueAssignedEmail, String> {

    @Inject
    private UserService userService;

    public void initialize(UniqueAssignedEmail annotation) {

    }

    public boolean isValid(String email, ConstraintValidatorContext context) {
        User user = this.userService.getAuthenticatedUser();
        return this.userService.checkEmailAvailability(email) || user.getEmail().equals(email);
    }
}
