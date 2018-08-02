package com.vasiliev.test.userapp.controller;


import com.vasiliev.test.userapp.model.User;
import com.vasiliev.test.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user-change/{id}", method = RequestMethod.GET)
    public ModelAndView profileUpdate(@PathVariable(value = "id") String id, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getUserByID(id);
        modelAndView.addObject("user", user);

        modelAndView.setViewName("change");

        return modelAndView;
    }

    @RequestMapping(value = "user-list", method = RequestMethod.GET)
    public ModelAndView profileGet(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        List<User> users = userService.getAllUsers();
        modelAndView.addObject("users", users);

        List<String> activeUsers = userService.getUsersFromSessionRegistry();
        modelAndView.addObject("activeUsers", activeUsers);

        modelAndView.setViewName("users");

        return modelAndView;
    }

    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public ModelAndView registration(Model model) {
        ModelAndView modelAndView = new ModelAndView();

        User newUser = new User();
        modelAndView.addObject(newUser);
        modelAndView.setViewName("registration");

        return modelAndView;
    }
}
