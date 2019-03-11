package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProjectDTO {
    private String id;

    private String name;

    private String description;

    private String userId;

    private String userLogin;

    private String date;
}
