package com.company.controller;

import com.company.api.Service;
import com.company.api.UserService;
import com.company.model.Project;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {
    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    private UserService userService;

    @PostMapping("createProject")
    public ModelAndView createProject(Model model, HttpServletRequest req) {

        String name = req.getParameter("name");
        String descr = req.getParameter("description");
        String login = req.getParameter("login");
        Optional<User> optionalUser = userService.findByLogin(login);
        if (!optionalUser.isPresent()){return new ModelAndView("redirect:/index");}
        User user = optionalUser.get();
        Project project = new Project(name, descr, user);
        projectService.save(project);
        return new ModelAndView("redirect:/projects");
    }

    @GetMapping("/editProject")
    public String getEdit(Model model, HttpServletRequest req) {
        String name = req.getParameter("name");
        Optional<Project> optionalProject = projectService.findByName(name);
        if (!optionalProject.isPresent()) {return "index";}
        Project project = optionalProject.get();
        model.addAttribute("project", project);
        return "updateProject";
    }

    @PostMapping("/editProject")
    public ModelAndView editProject(HttpServletRequest req) {
        String oldName = req.getParameter("oldName");
        String newName = req.getParameter("newName");
        String newDescr = req.getParameter("newDescr");
        String newLogin = req.getParameter("newLogin");
        Optional<User> optionalUser = userService.findByLogin(newLogin);
        if (!optionalUser.isPresent()) {return new ModelAndView("redirect:/projects");}
        User user = optionalUser.get();
        if (oldName.isEmpty()) {
            Project project = new Project(newName, newDescr, user);
            projectService.save(project);
            return new ModelAndView("redirect:/projects");
        } else {
            Optional<Project> optionalProject = projectService.findByName(oldName);
            Project project = optionalProject.get();
            project.setName(newName);
            project.setDescription(newDescr);
            project.setUser(user);
            projectService.update(project);
            return new ModelAndView("redirect:/projects");
        }
    }

    @GetMapping("/projects")
    public String getList(Model model) {
        System.out.println("зашел");
        List<Project> projects = projectService.getList();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/removeProject")
    public ModelAndView removeProject(HttpServletRequest req) {
        String name = req.getParameter("name");
        projectService.removeByName(name);
        return new ModelAndView("redirect:/projects");
    }
}
