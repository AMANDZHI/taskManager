package com.company.service;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.model.Project;
import com.company.model.Task;
import com.company.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityServiceToDTO {

    public static ProjectDTO getProjectDTO(Project project) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setUserId(project.getUser().getId());
        projectDTO.setDate(simpleDateFormat.format(project.getDate()));
        projectDTO.setUserLogin(project.getUser().getLogin());
        return projectDTO;
    }

    public static TaskDTO getTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setProjectId(task.getProject().getId());
        taskDTO.setUserId(task.getUser().getId());
        taskDTO.setDate(task.getDate().toString());
        return taskDTO;
    }

    public static UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole().toString());
        userDTO.setDate(user.getDate().toString());
        return userDTO;
    }

    public static List<ProjectDTO> getProjects(List<Project> list) {
        List<ProjectDTO> forClientList = new ArrayList<>();

        for (Project project: list) {
            forClientList.add(EntityServiceToDTO.getProjectDTO(project));
        }

        return  forClientList;
    }

    public static List<TaskDTO> getTasks(List<Task> list) {
        List<TaskDTO> forClientList = new ArrayList<>();

        for (Task task: list) {
            forClientList.add(EntityServiceToDTO.getTaskDTO(task));
        }

        return  forClientList;
    }

    public static List<UserDTO> getUsers(List<User> list) {
        List<UserDTO> forClientList = new ArrayList<>();

        for (User user: list) {
            forClientList.add(EntityServiceToDTO.getUserDTO(user));
        }

        return  forClientList;
    }
}