package com.vasiliev.test.userapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasiliev.test.userapp.model.OperationResultWithUser;
import com.vasiliev.test.userapp.model.OperationResultWithUserList;
import com.vasiliev.test.userapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class UserApiController implements UserApi {

    private final ObjectMapper objectMapper;

    @Autowired
    public UserApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<OperationResultWithUser> createUser(User userInvoice) {
        return null;
    }

    @Override
    public ResponseEntity<OperationResultWithUser> getUser(String id) {
        return null;
    }

    @Override
    public ResponseEntity<OperationResultWithUserList> getUserList() {
        return null;
    }

    @Override
    public ResponseEntity<OperationResultWithUser> updateUser(String id, User userUpdateInvoice) {
        return null;
    }
}
