package net.iegreen.web.validator;

import org.springframework.validation.Errors;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractPasswordValidator {

    protected static final int MIN_PASSWORD_LENGTH = 6;


    protected void validatePassword(String password, String rePassword, Errors errors) {

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            errors.rejectValue(passwordField(), null, "密码长度 >= 6");
            return;
        }

        if (!password.equals(rePassword)) {
            errors.rejectValue(rePasswordField(), null, "两数输入密码不同");
        }
    }


    protected String rePasswordField() {
        return "rePassword";
    }

    protected String passwordField() {
        return "password";
    }

}