package cz.ondrejpittl.business.validation;

import cz.ondrejpittl.business.annotations.CaptchaVerification;
import cz.ondrejpittl.dev.Dev;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CaptchaVerificationValidator implements ConstraintValidator<CaptchaVerification, String> {

    public void initialize(CaptchaVerification annotation) { }

    public boolean isValid(String captcha, ConstraintValidatorContext context) {
        Dev.print(captcha);
        return captcha.equals("14");
    }
}