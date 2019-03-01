package com.company.api;

import com.company.dto.UserDTO;
import com.company.model.Session;
import com.company.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface UserWebServiceEndpoint {

    @WebMethod
    UserDTO saveUser(@WebParam(name="name") String name, @WebParam(name="login") String login, @WebParam(name="password") String password, @WebParam(name="role") String role, @WebParam(name="session") Session session);

    @WebMethod
    UserDTO updateUser(@WebParam(name="login") String login, @WebParam(name="newName") String newName, @WebParam(name="newLogin") String newLogin, @WebParam(name="newPassword") String newPassword,@WebParam(name="newRole") String newRole, @WebParam(name="session") Session session);

    @WebMethod
    UserDTO findByLoginUser(@WebParam(name="user_login") String login, @WebParam(name="session") Session session);

    @WebMethod
    UserDTO findByIdUser(@WebParam(name="user_id") String id,@WebParam(name="session") Session session);

    @WebMethod
    boolean removeByLoginUser(@WebParam(name="user_login") String login, @WebParam(name="session")Session session);

    @WebMethod
    List<UserDTO> getListUser(@WebParam(name="session") Session session);
}