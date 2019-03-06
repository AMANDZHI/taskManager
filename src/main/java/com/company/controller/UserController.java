package com.company.controller;

import com.company.api.UserService;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping("/createUser")
    public ModelAndView createUser(HttpServletRequest req) {
       String name = req.getParameter("name");
       String login = req.getParameter("login");
       String password = req.getParameter("password");
       User user = new User(name, login, password);
       userService.save(user);
       return new ModelAndView("redirect:/users");
   }

   @GetMapping("/editUser")
    public String getEdit(Model model, HttpServletRequest req) {
       String login = req.getParameter("name");
       Optional<User> optionalUser = userService.findByLogin(login);
       model.addAttribute("user", optionalUser.get());
       return "updateUser";
   }

    @PostMapping("/editUser")
    public ModelAndView editUser(HttpServletRequest req) {
        String oldLogin = req.getParameter("oldLogin");
        String newName = req.getParameter("newName");
        String newLogin = req.getParameter("newLogin");
        String newPassword = req.getParameter("newPassword");

        if (oldLogin.isEmpty()) {
            User user = new User(newName, newLogin, newPassword);
            userService.save(user);
            return new ModelAndView("redirect:/users");
        } else {
            Optional<User> optionalUser = userService.findByLogin(oldLogin);
            User user = optionalUser.get();
            user.setName(newName);
            user.setLogin(newLogin);
            user.setPassword(newPassword);
            userService.update(user);
            return new ModelAndView("redirect:/users");
        }
    }

    @GetMapping("/users")
    public String getList(Model model) {
        List<User> users = userService.getList();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/removeUser")
    public ModelAndView removeUser(HttpServletRequest req) {
        String login = req.getParameter("name");
        userService.removeByLogin(login);
        return new ModelAndView("redirect:/users");
    }
}
