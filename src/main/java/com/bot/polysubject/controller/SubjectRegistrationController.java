package com.bot.polysubject.controller;

import com.bot.polysubject.Service.UserService;
import com.bot.polysubject.entity.User;
import com.bot.polysubject.model.api.resq.UserResq;
import com.bot.polysubject.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BaseController.API_URL)
public class SubjectRegistrationController {

    @Autowired
    UserService botService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Greetings from Spring Boot!";
    }

    //get all users
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<UserDTO> getAllUser() {
        return botService.getAllUser();
    }

    //insert a new user
    @RequestMapping(value = "/user/activate", method = RequestMethod.POST)
    public UserDTO activateUser(@RequestBody UserResq userResq) {
        return botService.activateUser(userResq.getUserId());
    }

    //insert a new user
    @RequestMapping(value = "/user/deactivate", method = RequestMethod.POST)
    public UserDTO deactivateUser(@RequestBody UserResq userResq) throws Exception{
        return botService.deactivateUser(userResq.getUserId());
    }

    //
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public User test() {
        User user = new User();
        user.setStatus("active");
        return user;
    }

}
