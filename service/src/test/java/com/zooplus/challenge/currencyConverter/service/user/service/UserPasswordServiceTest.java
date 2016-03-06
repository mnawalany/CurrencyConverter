package com.zooplus.challenge.currencyConverter.service.user.service;

import org.junit.Test;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class UserPasswordServiceTest {

    private UserPasswordService userPasswordService = new UserPasswordService();

    @Test
    public void shouldEncryptPassword() {
        // Given
        String password = "test";

        // When
        byte[] salt = userPasswordService.generateSalt();
        byte[] encryptedPassword = userPasswordService.getEncryptedPassword(password, salt);
        boolean authenticated1 = userPasswordService.authenticate(password, encryptedPassword, salt);
        boolean authenticated2 = userPasswordService.authenticate("incorrect password", encryptedPassword, salt);

        // Then
        assertThat(authenticated1).isTrue();
        assertThat(authenticated2).isFalse();
    }

    @Test
    public void shouldGenerateDifferentPasswordsForDifferentSalts() {
        // Given
        String password = "test";

        // When
        byte[] salt1 = userPasswordService.generateSalt();
        byte[] salt2 = userPasswordService.generateSalt();
        assertThat(salt1).isNotEqualTo(salt2);

        byte[] encryptedPassword1 = userPasswordService.getEncryptedPassword(password, salt1);
        byte[] encryptedPassword2 = userPasswordService.getEncryptedPassword(password, salt2);
        assertThat(encryptedPassword1).isNotEqualTo(encryptedPassword2);

        boolean authenticated1 = userPasswordService.authenticate(password, encryptedPassword1, salt1);
        boolean authenticated2 = userPasswordService.authenticate(password, encryptedPassword1, salt2);
        boolean authenticated3 = userPasswordService.authenticate(password, encryptedPassword2, salt1);
        boolean authenticated4 = userPasswordService.authenticate(password, encryptedPassword2, salt2);

        // Then
        assertThat(authenticated1).isTrue();
        assertThat(authenticated2).isFalse();
        assertThat(authenticated3).isFalse();
        assertThat(authenticated4).isTrue();
    }

}