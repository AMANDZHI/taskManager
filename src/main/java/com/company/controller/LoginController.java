package com.company.controller;

import com.company.api.UserService;
import com.company.model.User;
import com.company.service.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ModelAndView login(Model model, HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<User> optionalUser = userService.findByLogin(login);
        if (!optionalUser.isPresent()) { return new ModelAndView("redirect:/"); }
        User user = optionalUser.get();
        if (user.getPassword().equals(Encryption.md5Custom(password))) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            return new ModelAndView("redirect:/projects");
        } else {
            return new ModelAndView("redirect:/index");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
        session.invalidate();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/registration")
    public ModelAndView registr(HttpServletRequest req) {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {return new ModelAndView("redirect:/");}
        User newUser = new User(name, login, password);
        userService.save(newUser);

        HttpSession session = req.getSession();
        session.setAttribute("login", login);
        session.setAttribute("password", password);
        return new ModelAndView("redirect:/projects");
    }
}
