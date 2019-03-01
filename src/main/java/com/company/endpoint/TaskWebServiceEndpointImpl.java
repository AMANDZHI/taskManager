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
import com.company.util.UserRole;
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

        Optional<Project> optionalProject = projectService.findByName(nameProject);

        if (!optionalProject.isPresent()) {return null;}

        User userSession = userService.findById(session.getUserId()).get();
        Project project = optionalProject.get();
        User userProject = project.getUser();

        if (!userProject.getId().equals(session.getUserId()) && !userSession.getRole().equals(UserRole.ADMIN)) {return null;}

        Task task = new Task(nameTask, description, project, userSession);
        Task actual = taskService.save(task);

        return EntityServiceToDTO.getTaskDTO(actual);
    }

    @SneakyThrows
    @Override
    public TaskDTO findByNameTask(String name, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<Task> optionalTask = taskService.findByName(name);

        if (!optionalTask.isPresent()) {return null;}
        if (!optionalTask.get().getUser().getId().equals(session.getUserId()) || !userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return null;}

        Task task = optionalTask.get();
        return EntityServiceToDTO.getTaskDTO(task);
    }

    @SneakyThrows
    @Override
    public TaskDTO findByIdTask(String id, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<Task> optionalTask = taskService.findById(id);

        if (!optionalTask.isPresent()) {return null;}
        if (!optionalTask.get().getUser().getId().equals(session.getUserId()) && !userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return null;}

        Task task = optionalTask.get();
        return EntityServiceToDTO.getTaskDTO(task);
    }

    @SneakyThrows
    @Override
    public TaskDTO updateTask(String nameTask, String newNameTask, String newDescription, String newNameProject, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<Project> optionalProject = projectService.findByName(newNameProject);

        if (!optionalProject.isPresent()) {return null;}

        Project project = optionalProject.get();
        User userProject = project.getUser();
        User userSession = userService.findById(session.getUserId()).get();

        if (!userProject.getId().equals(session.getUserId()) && !userSession.getRole().equals(UserRole.ADMIN)) {return null;}

        Optional<Task> optionalTask = taskService.findByName(nameTask);

        if (!optionalTask.isPresent()) {return null;}

        Task task = optionalTask.get();
        User userTask = task.getUser();

        if (!userTask.getId().equals(session.getUserId()) && !userSession.getRole().equals(UserRole.ADMIN)) {return null;}

        task.setName(newNameTask);
        task.setDescription(newDescription);
        task.setProject(project);
        Task actual = taskService.update(task);

        return EntityServiceToDTO.getTaskDTO(actual);
    }

    @SneakyThrows
    @Override
    public boolean removeByNameTask(String name, Session session) {
        if (!sessionService.checkSession(session)) {return false;}

        Optional<Task> optionalTask = taskService.findByName(name);

        if (!optionalTask.isPresent()) {return false;}
        if (!optionalTask.get().getUser().getId().equals(session.getUserId()) && !userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return false;}

        return taskService.removeByName(name);
    }

    @SneakyThrows
    @Override
    public List<TaskDTO> getListTask(Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        List<TaskDTO> forClientList = new ArrayList<>();
        List<Task> list = taskService.getList();

        for (Task task: list) {
            User userTask = task.getUser();
            User user = userService.findById(session.getUserId()).get();
            if (userTask.getId().equals(session.getUserId()) || user.getRole().equals(UserRole.ADMIN)) {
                forClientList.add(EntityServiceToDTO.getTaskDTO(task));
            }
        }

        return forClientList;
    }
}