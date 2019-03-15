package com.company.controller;

import com.company.api.UserService;
import com.company.dto.UserDTO;
import com.company.model.User;
import com.company.model.UserRole;
import com.company.service.Encryption;
import com.company.service.EntityServiceToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserDTO> getList() {
        List<User> users = userService.getList();

        List<UserDTO> dtoUsers = EntityServiceToDTO.getUsers(users);
        return dtoUsers;
    }

    @PostMapping(value = "/user/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") String id, @ModelAttribute("user") User updateUser) {
        Optional<User> findUser = userService.findById(id);
        User user = findUser.get();

        user.setName(updateUser.getName());
        user.setLogin(updateUser.getLogin());
        user.setDate(updateUser.getDate());
        user.setRole(updateUser.getRole());
        userService.update(user);
        return new ResponseEntity<UserDTO>(HttpStatus.OK);
    }

    @PostMapping(value = "user/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDTO> addProject(@RequestParam("name") String name, @RequestParam("login") String login, @RequestParam("password") String password, @RequestParam("date") String date, @RequestParam("role") String role) throws ParseException {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(Encryption.md5Custom(password));
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        user.setDate(localDateTime);
        user.setRole(UserRole.valueOf(role));
        userService.save(user);
        return new ResponseEntity<UserDTO>(HttpStatus.OK);
    }

    @PostMapping(value = "/user/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> deleteProject(@RequestParam("id") String id) {
        userService.removeById(id);
        return new ResponseEntity<User>(HttpStatus.OK);
    }
//
//    @PostMapping(value = "/project/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") String id, @ModelAttribute("project") Project updateProject) {
//        Optional<Project> findProject = projectService.findById(id);
//        Project project = findProject.get();
//        System.out.println(project.getDescription() + " : " + project.getName());
//        project.setName(updateProject.getName());
//        project.setDescription(updateProject.getDescription());
//        projectService.update(project);
//        return new ResponseEntity<ProjectDTO>(HttpStatus.OK);
//    }
//
//    @PostMapping(value = "project/add", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ProjectDTO> addProject(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("userLogin") String userLogin, @RequestParam("date") String date) throws ParseException {
//        Optional<User> findUser = userService.findByLogin(userLogin);
//        System.out.println(date);
//        Project project = new Project();
//        project.setName(name);
//        project.setDescription(description);
//        project.setUser(findUser.get());
//        LocalDateTime localDateTime = LocalDateTime.parse(date);
//        project.setDate(localDateTime);
//        projectService.save(project);
//        return new ResponseEntity<ProjectDTO>(HttpStatus.OK);
//    }
//
//    @PostMapping(value = "/project/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<Project> deleteProject(@RequestParam("id") String id) {
//        projectService.removeById(id);
//        return new ResponseEntity<Project>(HttpStatus.OK);
//    }

//   @PostMapping("/createUser")
//    public ModelAndView createUser(HttpServletRequest req) {
//       String name = req.getParameter("name");
//       String login = req.getParameter("login");
//       String password = req.getParameter("password");
//       User user = new User(name, login, password);
//       userService.save(user);
//       return new ModelAndView("redirect:/users");
//   }
//
//   @GetMapping("/editUser")
//    public String getEdit(Model model, HttpServletRequest req) {
//       String login = req.getParameter("name");
//       Optional<User> optionalUser = userService.findByLogin(login);
//       model.addAttribute("user", optionalUser.get());
//       return "updateUser";
//   }
//
//    @PostMapping("/editUser")
//    public ModelAndView editUser(HttpServletRequest req) {
//        String oldLogin = req.getParameter("oldLogin");
//        String newName = req.getParameter("newName");
//        String newLogin = req.getParameter("newLogin");
//        String newPassword = req.getParameter("newPassword");
//
//        if (oldLogin.isEmpty()) {
//            User user = new User(newName, newLogin, newPassword);
//            userService.save(user);
//            return new ModelAndView("redirect:/users");
//        } else {
//            Optional<User> optionalUser = userService.findByLogin(oldLogin);
//            User user = optionalUser.get();
//            user.setName(newName);
//            user.setLogin(newLogin);
//            user.setPassword(newPassword);
//            userService.update(user);
//            return new ModelAndView("redirect:/users");
//        }
//    }
//
//    @GetMapping("/users")
//    public String getList(Model model) {
//        List<User> users = userService.getList();
//        model.addAttribute("users", users);
//        return "users";
//    }
//
//    @GetMapping("/removeUser")
//    public ModelAndView removeUser(HttpServletRequest req) {
//        String login = req.getParameter("name");
//        userService.removeByLogin(login);
//        return new ModelAndView("redirect:/users");
//    }
}
