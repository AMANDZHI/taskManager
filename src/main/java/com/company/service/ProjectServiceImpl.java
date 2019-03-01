package com.company.service;

import com.company.api.Service;
import com.company.model.Project;
import com.company.repository.ProjectRepositoryData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Qualifier("projectService")
public class ProjectServiceImpl implements Service<String, Project> {

    @Autowired
    private ProjectRepositoryData projectRepository;

    @Override
    @SneakyThrows
    @Transactional
    public Project save(Project object) {
        return projectRepository.save(object);
    }

    @Override
    @SneakyThrows
    public Optional<Project> findByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    @SneakyThrows
    public Optional<Project> findById(String id) {
        return projectRepository.findById(id);
    }

    @Override
    @SneakyThrows
    @Transactional
    public Project update(Project object) {
        return projectRepository.save(object);
    }

    @Override
    @SneakyThrows
    @Transactional
    public boolean removeByName(String name) {
        Long answerDelete = projectRepository.deleteByName(name);
        return answerDelete > 0;
    }

    @Override
    @SneakyThrows
    public List<Project> getList() {
        return projectRepository.findAll();
    }
}