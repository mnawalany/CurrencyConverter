package com.zooplus.challenge.currencyConverter.service.user.service;

import org.springframework.data.mongodb.repository.MongoRepository;

interface UserRepository extends MongoRepository<RepositoryUser, String> {

    RepositoryUser findByUsername(String username);

}
