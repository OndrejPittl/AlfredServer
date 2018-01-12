package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.ExistingPost;
import cz.ondrejpittl.business.annotations.UniqueEmail;
import cz.ondrejpittl.business.services.PostService;
import cz.ondrejpittl.business.services.UserService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingPostValidator implements ConstraintValidator<ExistingPost, Long> {

    @Inject
    private PostService postService;

    public void initialize(ExistingPost annotation) {

    }

    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return this.postService.checkPostExists(id);
    }
}
