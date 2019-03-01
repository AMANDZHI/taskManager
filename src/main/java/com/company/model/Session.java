package com.company.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Session {
    private String sessionId;
    private String userId;
    private Date date;
    private String token;

    public Session() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public Session(String userId) {
        this.userId = userId;
        this.sessionId = UUID.randomUUID().toString();
        this.date = new Date();
    }
}
