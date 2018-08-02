package com.vasiliev.test.userapp.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vasiliev.test.userapp.model.User;
import com.vasiliev.test.userapp.model.UserRegisterInvoice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceBeanTest {

    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private final String UserFilePath = "/User.json";
    private User testUser;

    @Before
    public void registerNewUserAccount() {
        UserRegisterInvoice userRegisterInvoice = new UserRegisterInvoice();
        userRegisterInvoice.email("test@email.com").password("pass");
        testUser = userService.registerNewUserAccount(userRegisterInvoice);
    }

    @After
    public void deleteUser() {
        userService.deleteUser(testUser.getId());
    }

    @Test
    public void getAllUsers() {
        assertEquals(userService.getAllUsers(), Collections.singletonList(testUser));
    }

    @Test
    public void getUserByID() {
        assertEquals(userService.getUserByID(testUser.getId()), testUser);
    }

//    @Ignore
    @Test
    public void updateUser() throws IOException {
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(new JavaTimeModule())
                .build();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = objectMapper.readValue(getClass().getResourceAsStream(UserFilePath), User.class);
        user.setId(testUser.getId());
        User userUpdated = userService.updateUser(user.getId(), user);
        assertNotNull(user.getId());
        assertEquals(user.getEmail(), userUpdated.getEmail());
    }
}
