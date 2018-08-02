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

/**
 * The type Page controller.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@Controller
public class PageController {

    @Autowired
    private UserService userService;

    /**
     * Profile update model and view.
     *
     * @param id    the id
     * @param model the model
     * @return the model and view
     */
    @RequestMapping(value = "user-change/{id}", method = RequestMethod.GET)
    public ModelAndView profileUpdate(@PathVariable(value = "id") String id, Model model) {
        ModelAndView modelAndView = new ModelAndView();

        User user = userService.getUserByID(id);
        modelAndView.addObject("user", user);

        modelAndView.setViewName("change");

        return modelAndView;
    }

    /**
     * Profile get model and view.
     *
     * @param model the model
     * @return the model and view
     */
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
}
