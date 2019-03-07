package com.company.util;

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
    Service<String, Project> projectService;

    @Autowired
    UserService userService;

    @PostConstruct
    public void runEndpoint() {


        projectService.removeByName("project");

        List<Project> listByUserId = projectService.getList();
        System.out.println(listByUserId.size());

        Endpoint.publish("http://localhost:1986/wss/project", projectServiceEndpoint);
        Endpoint.publish("http://localhost:1987/wss/task", taskServiceEndpoint);
        Endpoint.publish("http://localhost:1988/wss/user", userWebServiceEndpoint);
        Endpoint.publish("http://localhost:1989/wss/session", sessionWebServiceEndpoint);
        Endpoint.publish("http://localhost:1990/wss/serialization", serializationWebServiceEndpoint);
    }
}
