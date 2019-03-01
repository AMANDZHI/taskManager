package com.company.api;

import com.company.model.Session;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SessionWebServiceEndpoint {

    @WebMethod
    Session openSession(@WebParam(name="login") String login, @WebParam(name="password") String password);

    @WebMethod
    boolean checkSession(@WebParam(name="session") Session session);
}
