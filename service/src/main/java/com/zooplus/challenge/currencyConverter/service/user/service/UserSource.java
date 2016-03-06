package com.zooplus.challenge.currencyConverter.service.user.service;

import com.zooplus.challenge.currencyConverter.service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class UserSource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPasswordService userPasswordService;

    public User get(String username, String password) {
        RepositoryUser repositoryUser = userRepository.findByUsername(username);
        if (repositoryUser == null) {
            return null;
        }
        if (!userPasswordService.authenticate(password, repositoryUser.getPassword(), repositoryUser.getSalt())) {
            return null;
        }
        return new User(repositoryUser.getId(), repositoryUser.getUsername());
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public void save(String id, String username, String password) {
        final byte[] salt = userPasswordService.generateSalt();
        final byte[] encryptedPassword = userPasswordService.getEncryptedPassword(password, salt);
        userRepository.save(new RepositoryUser(id, username, encryptedPassword, salt));
    }

}
