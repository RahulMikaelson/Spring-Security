package org.varunmatangi.springsecurity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.varunmatangi.springsecurity.documents.UserEntity;

@Repository
public interface UserRepo extends MongoRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
}
