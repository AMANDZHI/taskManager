package com.company.endpoint;

import com.company.api.Service;
import com.company.api.SessionService;
import com.company.api.TaskWebServiceEndpoint;
import com.company.api.UserService;
import com.company.dto.TaskDTO;
import com.company.model.Project;
import com.company.model.Session;
import com.company.model.Task;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import com.company.model.UserRole;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@WebService(endpointInterface = "com.company.api.TaskWebServiceEndpoint")
public class TaskWebServiceEndpointImpl implements TaskWebServiceEndpoint {

    @Autowired
    private SessionService sessionService;

    @Autowired
    @Qualifier("taskService")
    private Service<String, Task> taskService;

    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    private UserService userService;

    @Override
    public TaskDTO saveTask(String nameTask, String description, String nameProject, Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        Task task;
        User userSession = userService.findById(session.getUserId()).get();

        if (userSession.getRole().equals(UserRole.ADMIN)) {
            Optional<Project> optionalProject = projectService.findByName(nameProject);
            if (!optionalProject.isPresent()) {return null;}
            Project project = optionalProject.get();
            task = new Task(nameTask, description, project, userSession);
        } else {
            Optional<Project> optionalProject = projectService.findByNameAndUserId(nameProject, session.getUserId());
            if (!optionalProject.isPresent()) {return null;}
            Project project = optionalProject.get();
            task = new Task(nameTask, description, project, userSession);
        }
        Task actual = taskService.save(task);
        return EntityServiceToDTO.getTaskDTO(actual);
    }

    @SneakyThrows
    @Override
    public TaskDTO findByNameTask(String name, Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        Task task;
         if (userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {
             Optional<Task> optionalTask = taskService.findByName(name);
             if (!optionalTask.isPresent()) {return null;}
             task = optionalTask.get();
         } else {
             Optional<Task> optionalTask  = taskService.findByNameAndUserId(name, session.getUserId());
             if (!optionalTask.isPresent()) {return null;}
             task = optionalTask.get();
         }
         return EntityServiceToDTO.getTaskDTO(task);
    }

    @SneakyThrows
    @Override
    public TaskDTO findByIdTask(String id, Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        Task task;
        if (userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {
            Optional<Task> optionalTask = taskService.findById(id);
            if (!optionalTask.isPresent()) {return null;}
            task = optionalTask.get();
        } else {
            Optional<Task> optionalTask = taskService.findByIdAndUserId(id, session.getUserId());
            if (!optionalTask.isPresent()) {return null;}
            task = optionalTask.get();
        }
        return EntityServiceToDTO.getTaskDTO(task);
    }

    @SneakyThrows
    @Override
    public TaskDTO updateTask(String nameTask, String newNameTask, String newDescription, String newNameProject, Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        User userSession = userService.findById(session.getUserId()).get();
        Task actual = null;
        if (userSession.getRole().equals(UserRole.ADMIN)) {
            Optional<Project> optionalProject = projectService.findByName(newNameProject);
            Optional<Task> optionalTask = taskService.findByName(nameTask);
            if (!optionalProject.isPresent()) {return null;}
            if (!optionalTask.isPresent()) {return null;}
            Project project = optionalProject.get();
            Task task = optionalTask.get();
            task.setName(newNameTask);
            task.setDescription(newDescription);
            task.setProject(project);
            actual = taskService.update(task);
        } else {
            Optional<Project> optionalProject = projectService.findByNameAndUserId(newNameProject, session.getUserId());
            Optional<Task> optionalTask = taskService.findByNameAndUserId(nameTask, session.getUserId());
            if (!optionalProject.isPresent()) {return null;}
            if (!optionalTask.isPresent()) {return null;}
            Project project = optionalProject.get();
            Task task = optionalTask.get();
            task.setName(newNameTask);
            task.setDescription(newDescription);
            task.setProject(project);
            actual = taskService.update(task);
        }
        return EntityServiceToDTO.getTaskDTO(actual);
    }

    @SneakyThrows
    @Override
    public boolean removeByNameTask(String name, Session session) {
        if (!sessionService.checkSession(session)) {return false;}
        if (userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {
            return taskService.removeByName(name);
        } else {
            return taskService.removeByNameAndUserId(name, session.getUserId());
        }
    }

    @SneakyThrows
    @Override
    public List<TaskDTO> getListTask(Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        List<TaskDTO> forClientList = new ArrayList<>();
        List<Task> list;
        User user = userService.findById(session.getUserId()).get();
        if (user.getRole().equals(UserRole.ADMIN)) {
            list = taskService.getList();
        } else {
            list = taskService.getListByUserId(session.getUserId());
        }

        for (Task task: list) {
            forClientList.add(EntityServiceToDTO.getTaskDTO(task));
        }

        return forClientList;
    }
}