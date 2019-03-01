package com.company.endpoint;

import com.company.api.ProjectWebServiceEndpoint;
import com.company.api.Service;
import com.company.api.SessionService;
import com.company.api.UserService;
import com.company.dto.ProjectDTO;
import com.company.model.Project;
import com.company.model.Session;
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
@WebService(endpointInterface = "com.company.api.ProjectWebServiceEndpoint")
public class ProjectWebServiceEndpointImpl implements ProjectWebServiceEndpoint {

    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @SneakyThrows
    @Override
    public ProjectDTO saveProject(String name, String description, Session session) {
        if (sessionService.checkSession(session)) {
            Optional<User> findUser = userService.findById(session.getUserId());
            User userSession = findUser.get();
            Project project = new Project(name, description, userSession);
            Project actual = projectService.save(project);

            return EntityServiceToDTO.getProjectDTO(actual);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public ProjectDTO findByNameProject(String name, Session session ) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<Project> optionalProject = projectService.findByName(name);

        if (!optionalProject.isPresent()) {return null;}
        if (!optionalProject.get().getUser().getId().equals(session.getUserId()) && !userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return null;}

        Project project = optionalProject.get();

        return EntityServiceToDTO.getProjectDTO(project);
    }

    @SneakyThrows
    @Override
    public ProjectDTO findByIdProject(String id, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<Project> optionalProject = projectService.findById(id);

        if (!optionalProject.isPresent()) {return null;}
        if (!optionalProject.get().getUser().getId().equals(session.getUserId()) && !userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return null;}

        Project project = optionalProject.get();

        return EntityServiceToDTO.getProjectDTO(project);
    }

    @SneakyThrows
    @Override
    public ProjectDTO updateProject(String name, String newName, String newDescription, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<Project> optionalProject = projectService.findByName(name);

        if (!optionalProject.isPresent()) {return null;}

        Project project = optionalProject.get();
        User user = project.getUser();
        Optional<User> optionalSessionUser = userService.findById(session.getUserId());
        User userSession = optionalSessionUser.get();

        if (!user.getId().equals(session.getUserId()) && !userSession.getRole().equals(UserRole.ADMIN)){return null;}

        project.setName(newName);
        project.setDescription(newDescription);
        Project updateProject = projectService.update(project);

        return EntityServiceToDTO.getProjectDTO(updateProject);
    }

    @SneakyThrows
    @Override
    public boolean removeByNameProject(String name, Session session) {
        if (!sessionService.checkSession(session)) {return false;}

        Optional<Project> optionalProject = projectService.findByName(name);

        if (!optionalProject.isPresent()) {return false;}
        if (!optionalProject.get().getUser().getId().equals(session.getUserId()) || !userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return false;}

        return projectService.removeByName(name);
    }

    @SneakyThrows
    @Override
    public List<ProjectDTO> getListProject(Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        List<ProjectDTO> forClientList = new ArrayList<>();
        User userSession = userService.findById(session.getUserId()).get();
        List<Project> list = projectService.getList();

        for (Project project: list) {
            User userProject = project.getUser();
            if (userProject.getId().equals(session.getUserId()) || userSession.getRole().equals(UserRole.ADMIN)) {
                forClientList.add(EntityServiceToDTO.getProjectDTO(project));
            }
        }

        return forClientList;
    }
}
