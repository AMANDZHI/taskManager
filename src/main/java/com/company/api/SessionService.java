package com.company.api;

import com.company.model.Session;

public interface SessionService {
    Session openSession(String login, String password);
    boolean checkSession(Session session);
}