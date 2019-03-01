package com.company.service;

import com.company.api.SessionService;
import com.company.api.UserService;
import com.company.model.Session;
import com.company.model.User;
import com.company.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserService userService;

    @Override
    public Session openSession(String login, String password) {
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getPassword().equals(Encryption.md5Custom(password))) {
                Session session = new Session(optionalUser.get().getId());
                session.setToken(Encryption.encryptAes(session.getSessionId(), session.getUserId()));
                return session;
            }
        }
        return null;
    }

    @Override
    public boolean checkSession(Session session) {
        Date currentTime = new Date();
        long expireTime = (currentTime.getTime() - session.getDate().getTime())/ 1000;
        if (expireTime > 600) {
            return false;
        }

        String checkToken = Encryption.encryptAes(session.getSessionId(), session.getUserId());
        if (!checkToken.equals(session.getToken())) {
            return false;
        }
        return true;
    }
}
