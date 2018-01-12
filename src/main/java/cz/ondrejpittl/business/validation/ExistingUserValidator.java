package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.ExistingPost;
import cz.ondrejpittl.business.annotations.ExistingUser;
import cz.ondrejpittl.business.services.PostService;
import cz.ondrejpittl.business.services.UserService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingUserValidator implements ConstraintValidator<ExistingUser, Long> {

    @Inject
    private UserService userService;

    public void initialize(ExistingUser annotation) {

    }

    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return this.userService.checkUserExists(id);
    }
}
