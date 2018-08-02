package com.vasiliev.test.userapp.service;


import com.vasiliev.test.userapp.model.User;
import com.vasiliev.test.userapp.model.UserList;
import com.vasiliev.test.userapp.model.UserRegisterInvoice;
import com.vasiliev.test.userapp.persistance.entity.UserEntity;

import java.util.List;

/**
 * The interface User service.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
public interface UserService {

    /**
     * Register new user account user.
     *
     * @param userRegisterInvoice the user register invoice
     * @return the user
     */
    User registerNewUserAccount(UserRegisterInvoice userRegisterInvoice);

    /**
     * Gets all users.
     *
     * @return the all users
     */
    UserList getAllUsers();

    /**
     * Delete user.
     *
     * @param id the id
     */
    void deleteUser(String id);

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    User getUserByID(String id);

    /**
     * Update user user.
     *
     * @param id                the id
     * @param userUpdateInvoice the user update invoice
     * @return the user
     */
    User updateUser(String id, User userUpdateInvoice);

    /**
     * Find user from security context holder user entity.
     *
     * @return the user entity
     */
    UserEntity findUserFromSecurityContextHolder();

    /**
     * Gets users from session registry.
     *
     * @return the users from session registry
     */
    List<String> getUsersFromSessionRegistry();
}
