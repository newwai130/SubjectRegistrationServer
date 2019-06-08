package com.bot.polysubject.controller;

import com.bot.polysubject.Service.BotService;
import com.bot.polysubject.entity.User;
import com.bot.polysubject.model.api.resq.UserResq;
import com.bot.polysubject.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api")
public class SubjectRegistrationController {

    @Autowired
    BotService botService;

    @RequestMapping(value = "/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    //get all users
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getAllUser() {
        return botService.getAllUser();
    }

    //insert a new user
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public UserDTO activateUser(UserResq userResq) {
        return botService.activateUser(userResq.getUserId());
    }

    //
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public User test() {
        User user = new User();
        user.setStatus("active");
        return user;
    }

}
