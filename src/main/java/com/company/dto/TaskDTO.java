package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private String id;

    private String name;

    private String description;

    private String projectId;

    private String projectName;

    private String userId;

    private String userLogin;

    private String date;
}
