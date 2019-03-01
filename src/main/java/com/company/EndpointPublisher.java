package com.company;

import com.company.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.ws.Endpoint;

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

    @PostConstruct
    public void runEndpoint() {
        Endpoint.publish("http://localhost:1986/wss/project", projectServiceEndpoint);
        Endpoint.publish("http://localhost:1987/wss/task", taskServiceEndpoint);
        Endpoint.publish("http://localhost:1988/wss/user", userWebServiceEndpoint);
        Endpoint.publish("http://localhost:1989/wss/session", sessionWebServiceEndpoint);
        Endpoint.publish("http://localhost:1990/wss/serialization", serializationWebServiceEndpoint);
    }
}
