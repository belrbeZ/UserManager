package com.vasiliev.test.userapp.persistance.dao;

import com.vasiliev.test.userapp.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * The interface User repository.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
//    @Query(value = "SELECT * FROM user_ccount WHERE user_ccount.email = :email", nativeQuery = true)
    UserEntity findByEmail(String email);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
//    @Query(value = "SELECT * FROM user_ccount WHERE user_ccount.email = :email", nativeQuery = true)
    boolean existsByEmail(String email);

    /**
     * Exists by email and id is not boolean.
     *
     * @param email the email
     * @param uuid  the uuid
     * @return the boolean
     */
    //    @Query(value = "SELECT * FROM user_ccount WHERE user_ccount.email = :email and user_ccount.id <> :uuid", nativeQuery = true)
    boolean existsByEmailAndIdIsNot(String email, UUID uuid);
}
