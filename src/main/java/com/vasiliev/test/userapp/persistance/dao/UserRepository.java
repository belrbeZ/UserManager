package com.vasiliev.test.userapp.persistance.dao;

import com.vasiliev.test.userapp.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdIsNot(String email, UUID uuid);
}
