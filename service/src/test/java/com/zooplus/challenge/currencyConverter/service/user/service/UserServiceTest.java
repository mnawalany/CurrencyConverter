package com.zooplus.challenge.currencyConverter.service.user.service;

import com.zooplus.challenge.currencyConverter.service.IdGenerator;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private UserSource userSource;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldReturnAbsentWhenNoUserWithUsernameAndPassword() {
        // Given
        String username = "username";
        String password = "pass";
        given(userSource.get(username, password)).willReturn(null);

        // When
        Optional<User> user = userService.getByUsernameAndPassword(username, password);

        // Then
        assertThat(user).isEmpty();
    }

    @Test
    public void shouldReturnUserByUsernameAndPassword() {
        // Given
        String username = "username";
        String password = "pass";
        User existingUser = new User("id", username);
        given(userSource.get(username, password)).willReturn(existingUser);

        // When
        Optional<User> user = userService.getByUsernameAndPassword(username, password);

        // Then
        assertThat(user).isPresent();
        assertThat(user.get()).isEqualTo(existingUser);
    }

    @Test
    public void shouldReturnFalseWhenUserDoesntExists() {
        // Given
        String username = "username";
        given(userSource.usernameExists(username)).willReturn(false);

        // When
        boolean exists = userService.userExists(username);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenUserExists() {
        // Given
        String username = "username";
        given(userSource.usernameExists(username)).willReturn(true);

        // When
        boolean exists = userService.userExists(username);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void shouldCreateNewUser() {
        // Given
        String username = "username";
        String password = "pass";
        String expectedId = "id";
        given(idGenerator.generateId()).willReturn(expectedId);

        // When
        userService.createNewUser(username, password);

        // Then
        verify(userSource).save(expectedId, username, password);
    }

}