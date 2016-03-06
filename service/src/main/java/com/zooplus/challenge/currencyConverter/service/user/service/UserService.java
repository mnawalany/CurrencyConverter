package com.zooplus.challenge.currencyConverter.service.user.service;

import com.zooplus.challenge.currencyConverter.service.IdGenerator;
import com.zooplus.challenge.currencyConverter.service.user.User;
import com.zooplus.challenge.currencyConverter.service.user.UserCreator;
import com.zooplus.challenge.currencyConverter.service.user.UserVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserService implements UserCreator, UserVerificator {

    @Autowired
    private UserSource userSource;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public Optional<User> getByUsernameAndPassword(String username, String password) {
        return Optional.ofNullable(userSource.get(username, password));
    }

    @Override
    public boolean userExists(String username) {
        return userSource.usernameExists(username);
    }

    @Override
    public void createNewUser(String username, String password) {
        userSource.save(idGenerator.generateId(), username, password);
    }

}
