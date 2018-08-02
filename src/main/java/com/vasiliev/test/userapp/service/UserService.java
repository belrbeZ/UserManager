package com.vasiliev.test.userapp.service;


import com.vasiliev.test.userapp.model.User;
import com.vasiliev.test.userapp.model.UserRegisterInvoice;
import com.vasiliev.test.userapp.persistance.entity.UserEntity;

import java.util.List;

public interface UserService {

    User registerNewUserAccount(UserRegisterInvoice userRegisterInvoice);

    List<User> getAllUsers();

    void deleteUser(String id);

    User getUserByID(String id);

    User updateUser(String id, User userUpdateInvoice);

    UserEntity findUserFromSecurityContextHolder();

    List<String> getUsersFromSessionRegistry();
}
