package com.company.controller;

import com.company.api.Service;
import com.company.api.UserService;
import com.company.dto.TaskDTO;
import com.company.model.Project;
import com.company.model.Task;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    @Qualifier("taskService")
    private Service<String, Task> taskService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/tasks", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<TaskDTO> getList() {
        List<Task> tasks = taskService.getList();
        List<TaskDTO> dtoTasks = EntityServiceToDTO.getTasks(tasks);
        return dtoTasks;
    }

    @PostMapping(value = "/task/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskDTO> updateProject(@PathVariable("id") String id, @ModelAttribute("task") Task updateTask) {
        Optional<Task> findTask = taskService.findById(id);
        Task task = findTask.get();
        task.setName(updateTask.getName());
        task.setDescription(updateTask.getDescription());
        taskService.update(task);
        return new ResponseEntity<TaskDTO>(HttpStatus.OK);
    }

    @PostMapping(value = "task/add", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskDTO> addProject(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("userId") String userId, @RequestParam("date") String date, @RequestParam("project") String nameProject) throws ParseException {
        Optional<User> findUser = userService.findById(userId);
        Project project = projectService.findById(nameProject).get();
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setUser(findUser.get());
        task.setProject(project);
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        task.setDate(localDateTime);
        taskService.save(task);
        return new ResponseEntity<TaskDTO>(HttpStatus.OK);
    }

    @PostMapping(value = "/task/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> deleteProject(@RequestParam("id") String id) {
        taskService.removeById(id);
        return new ResponseEntity<Task>(HttpStatus.OK);
    }

//    @PostMapping("/createTask")
//    public ModelAndView createTask(HttpServletRequest req) {
//        String name = req.getParameter("name");
//        String descr = req.getParameter("description");
//        String nameProject = req.getParameter("nameProject");
//        String login = req.getParameter("login");
//        Optional<Project> optionalProject = projectService.findByName(nameProject);
//        Optional<User> optionalUser = userService.findByLogin(login);
//        if (!optionalProject.isPresent()) {return new ModelAndView("redirect:/tasks");}
//        if (!optionalUser.isPresent()){return new ModelAndView("redirect:/tasks");}
//        Project project = optionalProject.get();
//        User user = optionalUser.get();
//        Task task = new Task(name, descr, project, user);
//        taskService.save(task);
//        return new ModelAndView("redirect:/tasks");
//    }
//
//    @GetMapping("/editTask")
//    public ModelAndView getEdit(HttpServletRequest req) {
//        String name = req.getParameter("name");
//        Optional<Task> optionalTask = taskService.findByName(name);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("updateTask");
//        if (!optionalTask.isPresent()) {
//            return modelAndView;
//        }
//        Task task = optionalTask.get();
//        modelAndView.addObject("task", task);
//        return modelAndView;
//    }
//
//    @PostMapping("/editTask")
//    public ModelAndView editTask(HttpServletRequest req) {
//        String oldName = req.getParameter("oldName");
//        String newName = req.getParameter("newName");
//        String newDescr = req.getParameter("newDescr");
//        String newProject = req.getParameter("newProject");
//        String newLogin = req.getParameter("newLogin");
//        Optional<Project> optionalProject = projectService.findByName(newProject);
//        Optional<User> optionalUser = userService.findByLogin(newLogin);
//        if (!optionalProject.isPresent()) {return new ModelAndView("redirect:/index");}
//        if (!optionalUser.isPresent()) {return new ModelAndView("redirect:/index");}
//        Project project = optionalProject.get();
//        User user = optionalUser.get();
//        if (oldName.isEmpty()) {
//            Task task = new Task(newName, newDescr, project, user);
//            taskService.save(task);
//            return new ModelAndView("redirect:/tasks");
//        } else {
//            Optional<Task> optionalTask = taskService.findByName(oldName);
//            Task task = optionalTask.get();
//            task.setName(newName);
//            task.setDescription(newDescr);
//            task.setProject(project);
//            taskService.update(task);
//            return new ModelAndView("redirect:/tasks");
//        }
//    }
//
//    @GetMapping("/tasks")
//    public String getList(Model model) {
//        List<Task> tasks = taskService.getList();
//        System.out.println(tasks.size());
//        model.addAttribute("tasks", tasks);
//        return "tasks";
//    }
//
//    @GetMapping("/removeTask")
//    public ModelAndView removeTask(HttpServletRequest req) {
//        String name = req.getParameter("name");
//        taskService.removeById(name);
//        return new ModelAndView("redirect:/tasks");
//    }
}
