package com.zooplus.challenge.currencyConverter.service.user.service;

import com.zooplus.challenge.currencyConverter.service.SpringProfile;
import com.zooplus.challenge.currencyConverter.service.config.ServiceConfiguration;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.StrictAssertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles(SpringProfile.TEST)
public class UserSourceIntegrationTest {

    @Autowired
    private UserSource userSource;

    @Test
    public void shouldCreateAndReadUser() {
        // Given
        String id = "id";
        String username = "username";
        String password = "password";

        userSource.save(id, username, password);

        // When
        User resultUser = userSource.get(username, password);

        // Then
        assertThat(resultUser.getId()).isEqualTo(id);
        assertThat(resultUser.getUsername()).isEqualTo(username);
    }

    @Test
    public void shouldReturnNullWhenInvalidUsername() {
        // Given
        String username = "username";
        String password = "password";

        userSource.save("id", username, password);

        // When
        User resultUser = userSource.get("invalid username", password);

        // Then
        assertThat(resultUser).isNull();
    }

    @Test
    public void shouldReturnNullWhenInvalidPassword() {
        // Given
        String username = "username";
        String password = "password";

        userSource.save("id", username, password);

        // When
        User resultUser = userSource.get(username, "invalid password");

        // Then
        assertThat(resultUser).isNull();
    }

    @Test
    public void shouldReturnTrueIfUserWithUsernameExists() {
        // Given
        String username = "username";
        userSource.save("id", username, "password");

        // When
        boolean exists = userSource.usernameExists(username);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void shouldReturnFalseIfUserWithUsernameDeosntExist() {
        // Given
        userSource.save("id", "username", "password");

        // When
        boolean exists = userSource.usernameExists("different username");

        // Then
        assertThat(exists).isFalse();
    }

}