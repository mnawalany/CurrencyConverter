package com.zooplus.challenge.currencyConverter.client.user;

import com.zooplus.challenge.currencyConverter.service.user.UserCreator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class RegistrationFormValidator implements Validator {

    @Autowired
    private UserCreator userCreator;

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) target;

        if (isEmpty(registrationForm.getEmail())) {
            errors.rejectValue("email", "registration.fields.email.empty", "Email cannot be empty");
        } else if (!emailValidator.isValid(registrationForm.getEmail())) {
            errors.rejectValue("email", "registration.fields.email.invalid", "Email address is invalid");
        } else if (userCreator.userExists(registrationForm.getEmail())) {
            errors.rejectValue("email", "registration.fields.email.exists", "Email address already exists");
        }

        if (isEmpty(registrationForm.getPassword1())) {
            errors.rejectValue("password1", "registration.fields.password1.empty", "Password cannot be empty");
        }

        if (! registrationForm.getPassword1().equals(registrationForm.getPassword2())) {
            errors.rejectValue("password2", "registration.fields.password2.notMatch", "Passwords don't match");
        }

    }
}
