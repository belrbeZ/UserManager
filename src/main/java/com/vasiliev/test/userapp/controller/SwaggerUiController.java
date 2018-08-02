package com.vasiliev.test.userapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to swagger api documentation
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@Controller
public class SwaggerUiController {

    /**
     * Api string.
     *
     * @return the string
     */
    @RequestMapping(value = {"/api"})
    public String api() {
        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }
}
