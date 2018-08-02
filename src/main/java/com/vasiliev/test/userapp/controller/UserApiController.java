package com.vasiliev.test.userapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasiliev.test.userapp.model.OperationResultWithUser;
import com.vasiliev.test.userapp.model.OperationResultWithUserList;
import com.vasiliev.test.userapp.model.User;
import com.vasiliev.test.userapp.model.UserRegisterInvoice;
import com.vasiliev.test.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserApiController extends GenericApiController implements UserApi {

    private final ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    public UserApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ResponseEntity<OperationResultWithUser> createUser(@Valid @RequestBody UserRegisterInvoice userInvoice) {
        return created().content(userService.registerNewUserAccount(userInvoice))
                .type(OperationResultWithUser.class)
                .response();    }

    @Override
    public ResponseEntity<OperationResultWithUser> getUser(@PathVariable("id") String id) {
        return retrieved().content(userService.getUserByID(id))
                .type(OperationResultWithUser.class)
                .response();
    }

    @Override
    public ResponseEntity<OperationResultWithUserList> getUserList() {
        return retrieved().content(userService.getAllUsers())
                .type(OperationResultWithUserList.class)
                .response();
    }

    @Override
    public ResponseEntity<OperationResultWithUser> updateUser(@PathVariable("id") String id, @Valid @RequestBody User userUpdateInvoice) {
        return updated().content(userService.updateUser(id, userUpdateInvoice))
                .type(OperationResultWithUser.class)
                .response();
    }
}
