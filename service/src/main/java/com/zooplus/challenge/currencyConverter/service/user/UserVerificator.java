package com.zooplus.challenge.currencyConverter.service.user;

import java.util.Optional;

public interface UserVerificator {

    Optional<User> getByUsernameAndPassword(String username, String password);

}
