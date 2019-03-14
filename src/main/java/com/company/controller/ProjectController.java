package com.company.controller;

import com.company.api.Service;
import com.company.api.UserService;
import com.company.dto.ProjectDTO;
import com.company.model.Project;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
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
//
//    @GetMapping("/editProject")
//    @ResponseBody
//    public ProjectDTO getEdit(Model model, HttpServletRequest req) {
//        String name = req.getParameter("value");
//        Optional<Project> optionalProject = projectService.findById(name);
//        if (!optionalProject.isPresent()) {return null;}
//        Project project = optionalProject.get();
//        model.addAttribute("project", project);
//        System.out.println(project.getName());
//        ProjectDTO projectDTO = EntityServiceToDTO.getProjectDTO(project);
//        return projectDTO;
//    }
//
//    @PostMapping("/editProject")
//    @ResponseBody
//    public ProjectDTO editProject(HttpServletRequest req) {
//        String id = req.getParameter("id");
//        String newName = req.getParameter("name");
//        String newDescr = req.getParameter("descr");
//        String newLogin = req.getParameter("login");
//        Optional<User> optionalUser = userService.findByLogin(newLogin);
//        if (!optionalUser.isPresent()) {return null;}
//        User user = optionalUser.get();
//        if (id.isEmpty()) {
//            Project project = new Project(newName, newDescr, user);
//            projectService.save(project);
//            return null;
//        } else {
//            Optional<Project> optionalProject = projectService.findById(id);
//            Project project = optionalProject.get();
//            project.setName(newName);
//            project.setDescription(newDescr);
//            project.setUser(user);
//            Project updateProject = projectService.update(project);
//            return EntityServiceToDTO.getProjectDTO(updateProject);
//        }
//    }
//
//    @PostMapping("/createProject")
//    @ResponseBody
//    public ProjectDTO createProject(HttpServletRequest req) {
//        String newName = req.getParameter("name");
//        String newDescr = req.getParameter("descr");
//        String newLogin = req.getParameter("login");
//        Optional<User> optionalUser = userService.findByLogin(newLogin);
//        if (!optionalUser.isPresent()) {return null;}
//        User user = optionalUser.get();
//
//        Project project = new Project(newName, newDescr, user);
//        Project saveProject = projectService.save(project);
//        ProjectDTO projectDTO = EntityServiceToDTO.getProjectDTO(saveProject);
//        return projectDTO;
//    }

    @GetMapping(value = "/projects", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProjectDTO> getList() {
        List<Project> projects = projectService.getList();
        List<ProjectDTO> dtoProjects = EntityServiceToDTO.getProjects(projects);
        return dtoProjects;
    }

    @PostMapping(value = "/project/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable("id") String id, @ModelAttribute("project") Project updateProject) {
        Optional<Project> findProject = projectService.findById(id);
        Project project = findProject.get();
        System.out.println(project.getDescription() + " : " + project.getName());
        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        projectService.update(project);
        return new ResponseEntity<ProjectDTO>(HttpStatus.OK);
    }

    @PostMapping(value = "project/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProjectDTO> addProject(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("userLogin") String userLogin, @RequestParam("date") String date) throws ParseException {
        Optional<User> findUser = userService.findByLogin(userLogin);
        System.out.println(date);
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setUser(findUser.get());
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        project.setDate(localDateTime);
        projectService.save(project);
        return new ResponseEntity<ProjectDTO>(HttpStatus.OK);
    }

    @PostMapping(value = "/project/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Project> deleteProject(@RequestParam("id") String id) {
        projectService.removeById(id);
        return new ResponseEntity<Project>(HttpStatus.OK);
    }

//    @RequestMapping(value = "/project/update/{id}", produces = { MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
//    public Project updateProject(@PathVariable("id") String id, @) {
//        Optional<Project> findProject = projectService.findById(id);
//        System.out.println("update : " + id);
//        Project project = findProject.get();
//        System.out.println(name + " : " + "client");
//        System.out.println(id + " : " + "client");
//        System.out.println(description + " : " + "client");
//        System.out.println(project.getDescription() +" : "  + project.getName());
//        project.setName(name);
//        project.setDescription(description);
//        projectService.update(project);
//        return project;
//    }
//
//    @DeleteMapping(value = "/project/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<Project> deleteProject(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description) {
//
//    }

//    @GetMapping("/removeProject")
//    public ModelAndView removeProject(HttpServletRequest req) {
//        String name = req.getParameter("name");
//        projectService.removeByName(name);
//        return new ModelAndView("redirect:/projects");
//    }
//
//    @GetMapping("/sort")
//    public String getListByDate(HttpServletRequest req, Model model) throws ParseException {
//        String start = req.getParameter("start");
//        String end = req.getParameter("end");
//        SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
//        Date startDate = formatter.parse(start);
//        Date endDate = formatter.parse(end);
//
//        List<Project> projects = projectService.getListByDate(startDate, endDate);
//        System.out.println(projects.size());
//        model.addAttribute("projects", projects);
//        return "projects";
//    }
}
