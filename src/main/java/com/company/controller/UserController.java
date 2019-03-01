package com.company.controller;

import com.company.api.UserService;
import com.company.dto.UserDTO;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user")
    public ModelAndView getUsers() {
        ModelAndView modelAndView = new ModelAndView();
        List<User> list = userService.getList();

        List<UserDTO> forClientList = EntityServiceToDTO.getUsers(list);

        modelAndView.addObject("list", forClientList);
        modelAndView.setViewName("user");
        return modelAndView;
    }
}
