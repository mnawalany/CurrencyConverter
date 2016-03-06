package com.zooplus.challenge.currencyConverter.service.config;

import com.zooplus.challenge.currencyConverter.service.user.User;
import com.zooplus.challenge.currencyConverter.service.user.UserVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserVerificator userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Optional<User> user = userService.getByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            return new UsernamePasswordAuthenticationToken(
                    user.get(), null, createAuthorityList("ROLE_USER"));
        }

        return null;

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
