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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {

    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    private UserService userService;

//    @PostMapping("createProject")
//    public ModelAndView createProject(Model model, HttpServletRequest req) {
//
//        String name = req.getParameter("name");
//        String descr = req.getParameter("description");
//        String login = req.getParameter("login");
//        Optional<User> optionalUser = userService.findByLogin(login);
//        if (!optionalUser.isPresent()){return new ModelAndView("redirect:/index");}
//        User user = optionalUser.get();
//        Project project = new Project(name, descr, user);
//        projectService.save(project);
//        return new ModelAndView("redirect:/projects");
//    }

    @GetMapping("/editProject")
    @ResponseBody
    public ProjectDTO getEdit(Model model, HttpServletRequest req) {
        String name = req.getParameter("value");
        Optional<Project> optionalProject = projectService.findById(name);
        if (!optionalProject.isPresent()) {return null;}
        Project project = optionalProject.get();
        model.addAttribute("project", project);
        System.out.println(project.getName());
        ProjectDTO projectDTO = EntityServiceToDTO.getProjectDTO(project);
        return projectDTO;
    }

    @PostMapping("/editProject")
    @ResponseBody
    public ProjectDTO editProject(HttpServletRequest req) {
        String id = req.getParameter("id");
        String newName = req.getParameter("name");
        String newDescr = req.getParameter("descr");
        String newLogin = req.getParameter("login");
        Optional<User> optionalUser = userService.findByLogin(newLogin);
        if (!optionalUser.isPresent()) {return null;}
        User user = optionalUser.get();
        if (id.isEmpty()) {
            Project project = new Project(newName, newDescr, user);
            projectService.save(project);
            return null;
        } else {
            Optional<Project> optionalProject = projectService.findById(id);
            Project project = optionalProject.get();
            project.setName(newName);
            project.setDescription(newDescr);
            project.setUser(user);
            Project updateProject = projectService.update(project);
            return EntityServiceToDTO.getProjectDTO(updateProject);
        }
    }

    @PostMapping("/createProject")
    @ResponseBody
    public ProjectDTO createProject(HttpServletRequest req) {
        String newName = req.getParameter("name");
        String newDescr = req.getParameter("descr");
        String newLogin = req.getParameter("login");
        Optional<User> optionalUser = userService.findByLogin(newLogin);
        if (!optionalUser.isPresent()) {return null;}
        User user = optionalUser.get();

        Project project = new Project(newName, newDescr, user);
        Project saveProject = projectService.save(project);
        ProjectDTO projectDTO = EntityServiceToDTO.getProjectDTO(saveProject);
        return projectDTO;
    }

//    @GetMapping(value = "/projects", produces = { MediaType.APPLICATION_JSON_VALUE})
//    public List getList(Model model) {
//        List<Project> projects = projectService.getList();
//        return projects;
//    }

    @GetMapping("/removeProject")
    public ModelAndView removeProject(HttpServletRequest req) {
        String name = req.getParameter("name");
        projectService.removeByName(name);
        return new ModelAndView("redirect:/projects");
    }

    @GetMapping("/sort")
    public String getListByDate(HttpServletRequest req, Model model) throws ParseException {
        String start = req.getParameter("start");
        String end = req.getParameter("end");
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);

        List<Project> projects = projectService.getListByDate(startDate, endDate);
        System.out.println(projects.size());
        model.addAttribute("projects", projects);
        return "projects";
    }
}
