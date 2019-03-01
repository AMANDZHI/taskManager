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

    private String userId;
}
