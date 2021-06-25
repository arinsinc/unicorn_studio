package com.unicorn.studio.utils;

import com.unicorn.studio.entity.User;
import com.unicorn.studio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Configuration
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty");
        if (user.getFullName().length() < 2 || user.getFullName().length() > 32) {
            errors.rejectValue("fullName", "Invalid name");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (userService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "This email address is already registered");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Invalid password");
        }
    }
}
