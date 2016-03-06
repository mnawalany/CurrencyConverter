package com.zooplus.challenge.currencyConverter.client.user;

import com.zooplus.challenge.currencyConverter.service.user.UserCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;

import static io.codearte.catchexception.shade.mockito.Mockito.mock;
import static io.codearte.catchexception.shade.mockito.Mockito.verify;
import static io.codearte.catchexception.shade.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationFormValidatorTest {

    @Mock
    private UserCreator userCreator;

    @InjectMocks
    private RegistrationFormValidator validator;

    private Errors errors = mock(Errors.class);

    @Test
    public void shouldRejectEmptyEmail() {
        // Given
        RegistrationForm registrationForm = vaidForm();
        registrationForm.setEmail("");

        // When
        validator.validate(registrationForm, errors);

        // Then
        verify(errors).rejectValue("email", "registration.fields.email.empty", "Email cannot be empty");
    }

    @Test
    public void shouldRejectInvalidEmail() {
        verifyEmailRejected("not valid");
        verifyEmailRejected("not@valid");
        verifyEmailRejected("not@valid.");
        verifyEmailRejected("not@valid@a");
        verifyEmailRejected("@not.valid");
    }

    private void verifyEmailRejected(String email) {
        // Given
        RegistrationForm registrationForm = vaidForm();
        registrationForm.setEmail(email);

        Errors errors = mock(Errors.class);

        // When
        validator.validate(registrationForm, errors);

        // Then
        verify(errors).rejectValue("email", "registration.fields.email.invalid", "Email address is invalid");
    }

    @Test
    public void shouldRejectWhenEmailExisis() {
        // Given
        RegistrationForm registrationForm = vaidForm();
        given(userCreator.userExists(registrationForm.getEmail())).willReturn(true);

        // When
        validator.validate(registrationForm, errors);

        // Then
        verify(errors).rejectValue("email", "registration.fields.email.exists", "Email address already exists");
    }

    @Test
    public void shouldRejectWhenPasswordIsEmpty() {
        // Given
        RegistrationForm registrationForm = vaidForm();
        registrationForm.setPassword1("");

        // When
        validator.validate(registrationForm, errors);

        // Then
        verify(errors).rejectValue("password1", "registration.fields.password1.empty", "Password cannot be empty");
    }

    @Test
    public void shouldRejectWhenPasswordsDontMatch() {
        // Given
        RegistrationForm registrationForm = vaidForm();
        registrationForm.setPassword1("some1");
        registrationForm.setPassword2("some2");

        // When
        validator.validate(registrationForm, errors);

        // Then
        verify(errors).rejectValue("password2", "registration.fields.password2.notMatch", "Passwords don't match");
    }

    @Test
    public void shouldAcceptValidForm() {
        // Given
        RegistrationForm registrationForm = vaidForm();

        // When
        validator.validate(registrationForm, errors);

        // Then
        verifyNoMoreInteractions(errors);
    }

    private RegistrationForm vaidForm() {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setEmail("some.guy@provider.com");
        registrationForm.setPassword1("secret");
        registrationForm.setPassword2("secret");
        given(userCreator.userExists(registrationForm.getEmail())).willReturn(false);
        return registrationForm;
    }

}