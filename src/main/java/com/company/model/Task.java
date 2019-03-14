package com.company.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="task_tbl")
@Getter
@Setter
@ToString
public class Task implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @JoinColumn(name = "projectId", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private Project project;

    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private User user;

    @Column(name="date", nullable = false)
    private LocalDateTime date;

    {
        this.id = UUID.randomUUID().toString();
        this.date = LocalDateTime.now();
    }

    public Task() {

    }

    public Task(String name, String description, Project project, User user) {
        this.name = name;
        this.description = description;
        this.project = project;
        this.user = user;
    }
}