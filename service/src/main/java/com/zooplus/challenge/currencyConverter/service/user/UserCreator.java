package com.zooplus.challenge.currencyConverter.service.user;

public interface UserCreator {

    boolean userExists(String username);

    void createNewUser(String username, String password);

}
