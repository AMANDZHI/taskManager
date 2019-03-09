package com.company.service;

import com.company.api.*;
import com.company.dto.ProjectDTO;
import com.company.dto.UserDTO;
import com.company.model.Project;
import com.company.model.Session;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.ws.Endpoint;
import java.util.List;
import java.util.Optional;

@Component
public class EndpointPublisher {

    @Autowired
    private ProjectWebServiceEndpoint projectServiceEndpoint;

    @Autowired
    private TaskWebServiceEndpoint taskServiceEndpoint;

    @Autowired
    private UserWebServiceEndpoint userWebServiceEndpoint;

    @Autowired
    private SessionWebServiceEndpoint sessionWebServiceEndpoint;

    @Autowired
    private SerializationWebServiceEndpoint serializationWebServiceEndpoint;

    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void runEndpoint() {
        Optional<User> admin = userService.findByLogin("admin");

        Project project = new Project("project", "pr", admin.get());
        Project project1 = new Project("project1", "pr", admin.get());
        Project project2 = new Project("project2", "pr", admin.get());
        Project project3 = new Project("project3", "pr", admin.get());
        projectService.save(project);
        projectService.save(project1);
        projectService.save(project2);

        Endpoint.publish("http://localhost:1986/wss/project", projectServiceEndpoint);
        Endpoint.publish("http://localhost:1987/wss/task", taskServiceEndpoint);
        Endpoint.publish("http://localhost:1988/wss/user", userWebServiceEndpoint);
        Endpoint.publish("http://localhost:1989/wss/session", sessionWebServiceEndpoint);
        Endpoint.publish("http://localhost:1990/wss/serialization", serializationWebServiceEndpoint);
    }
}
