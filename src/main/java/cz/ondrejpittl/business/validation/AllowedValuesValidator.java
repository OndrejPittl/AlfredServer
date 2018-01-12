package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.AllowedValues;
import cz.ondrejpittl.business.annotations.UniqueEmail;
import cz.ondrejpittl.business.services.UserService;
import cz.ondrejpittl.dev.Dev;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, String> {

    private String[] val;

    public void initialize(AllowedValues annotation) {
        this.val = annotation.val();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(this.val).contains(value);
    }
}
