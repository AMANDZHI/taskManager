package com.company.endpoint;

import com.company.api.SessionService;
import com.company.api.UserService;
import com.company.api.UserWebServiceEndpoint;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.model.Session;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import com.company.util.Encryption;
import com.company.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@WebService(endpointInterface = "com.company.api.UserWebServiceEndpoint")
public class UserWebServiceEndpointImpl implements UserWebServiceEndpoint {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Override
    public UserDTO saveUser(String name, String login, String password, String role, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(Encryption.md5Custom(password));
        user.setRole(UserRole.valueOf(role.toUpperCase()));

        User userSession = userService.findById(session.getUserId()).get();

        if (!userSession.getRole().equals(UserRole.ADMIN)) {return null;}

        User actual = userService.save(user);

        return EntityServiceToDTO.getUserDTO(actual);
    }

    @Override
    public UserDTO updateUser(String login, String newName, String newLogin, String newPassword, String newRole, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<User> optionalUser = userService.findByLogin(login);

        if (!optionalUser.isPresent()) {return null;}

        User user = optionalUser.get();
        User userSession = userService.findById(session.getUserId()).get();

        if (!userSession.getRole().equals(UserRole.ADMIN)) {return null;}

        user.setName(newName);
        user.setLogin(newLogin);
        user.setRole(UserRole.valueOf(newRole.toUpperCase()));
        user.setPassword(Encryption.md5Custom(newPassword));

        User actual = userService.update(user);
        return EntityServiceToDTO.getUserDTO(actual);
    }

    @Override
    public UserDTO findByLoginUser(String login, Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        if (!userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return null;}

        Optional<User> optionalLogin = userService.findByLogin(login);

        if (!optionalLogin.isPresent()) {return null;}

        return EntityServiceToDTO.getUserDTO(optionalLogin.get());
    }

    @Override
    public UserDTO findByIdUser(String id, Session session) {
        if (!sessionService.checkSession(session)) {return null;}

        Optional<User> optionalUser = userService.findById(id);

        if (!optionalUser.isPresent()) {return null;}
        if (!optionalUser.get().getRole().equals(UserRole.ADMIN) && !session.getUserId().equals(optionalUser.get().getId())) {return null;}

        User user = optionalUser.get();

        return EntityServiceToDTO.getUserDTO(user);
    }

    @Override
    public boolean removeByLoginUser(String login, Session session) {
        if (!sessionService.checkSession(session)) {return false;}
        if (!userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return false;}

        return userService.removeByLogin(login);
    }

    @Override
    public List<UserDTO> getListUser(Session session) {
        if (!sessionService.checkSession(session)) {return null;}
        if (!userService.findById(session.getUserId()).get().getRole().equals(UserRole.ADMIN)) {return null;}

        List<UserDTO> forClientList = new ArrayList<>();
        List<User> list = userService.getList();
        for (User u: list) {
            forClientList.add(EntityServiceToDTO.getUserDTO(u));
        }

        return forClientList;
    }
}