package com.company.repository;

import com.company.api.DomainRepository;
import com.company.model.Domain;
import com.company.model.Project;
import com.company.model.Task;
import com.company.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class DomainRepositoryImpl implements DomainRepository {

    @Autowired
    private Domain domain;

    @Autowired
    private ProjectRepositoryData projectRepository;

    @Autowired
    private TaskRepositoryData taskRepository;

    @Autowired
    private UserRepositoryData userRepository;

    @Override
    @SneakyThrows
    public Domain getDomain() {
        List<Project> listProjects = projectRepository.findAll();
        List<Task> listTasks = taskRepository.findAll();
        List<User> listUsers = userRepository.findAll();

        domain.setProjectList(listProjects);
        domain.setTaskList(listTasks);
        domain.setUserList(listUsers);
        return domain;
    }
}
