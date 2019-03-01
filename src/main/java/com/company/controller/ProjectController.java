package com.company.controller;

import com.company.api.Service;
import com.company.api.UserService;
import com.company.dto.ProjectDTO;
import com.company.model.Project;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/")
    public ModelAndView getProjects() {
        ModelAndView modelAndView = new ModelAndView();
        List<Project> list = projectService.getList();

        List<ProjectDTO> forClientList = EntityServiceToDTO.getProjects(list);

        modelAndView.addObject("list", forClientList);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    @PostMapping("/createProject")
    public ModelAndView createProject(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();

        String name = req.getParameter("name");
        String descr = req.getParameter("descr");
        String login = req.getParameter("login");

        Optional<User> findLogin = userService.findByLogin(login);
        Project project = new Project(name, descr, findLogin.get());
        projectService.save(project);

        List<Project> list = projectService.getList();

        List<ProjectDTO> forClientList = EntityServiceToDTO.getProjects(list);

        modelAndView.addObject("list", forClientList);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    @PostMapping("/updateProject")
    public ModelAndView updateProject(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();

        String name = req.getParameter("name");
        String newName = req.getParameter("newName");
        String descr = req.getParameter("descr");
        String login = req.getParameter("login");

        Optional<User> findLogin = userService.findByLogin(login);
        Project findProject = projectService.findByName(name).get();
        User user = userService.findByLogin(login).get();
        findProject.setName(newName);
        findProject.setDescription(descr);
        findProject.setUser(user);
        projectService.update(findProject);

        List<Project> list = projectService.getList();

        List<ProjectDTO> forClientList = EntityServiceToDTO.getProjects(list);

        modelAndView.addObject("list", forClientList);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    @PostMapping("/findProject")
    public ModelAndView findProject(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();

        String name = req.getParameter("name");

        Project findProject = projectService.findByName(name).get();

        ProjectDTO projectDTO = EntityServiceToDTO.getProjectDTO(findProject);

        modelAndView.addObject("project", projectDTO);
        modelAndView.setViewName("findProject");
        return modelAndView;
    }

    @PostMapping("/removeProject")
    public ModelAndView removeProject(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();

        String name = req.getParameter("name");

        projectService.removeByName(name);

        List<Project> list = projectService.getList();

        List<ProjectDTO> forClientList = EntityServiceToDTO.getProjects(list);

        modelAndView.addObject("list", forClientList);
        modelAndView.setViewName("project");
        return modelAndView;
    }
}
