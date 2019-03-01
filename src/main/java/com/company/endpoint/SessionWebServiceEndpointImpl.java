package com.company.endpoint;

import com.company.api.SessionService;
import com.company.api.SessionWebServiceEndpoint;
import com.company.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@Component
@WebService(endpointInterface = "com.company.api.SessionWebServiceEndpoint")
public class SessionWebServiceEndpointImpl implements SessionWebServiceEndpoint {

    @Autowired
    private SessionService sessionService;

    @Override
    public Session openSession(String login, String password) {
        return sessionService.openSession(login, password);
    }

    @Override
    public boolean checkSession(Session session) {
        return sessionService.checkSession(session);
    }
}
