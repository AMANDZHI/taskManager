package com.company.service;

import com.company.api.Service;
import com.company.model.Project;
import com.company.repository.ProjectRepositoryData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;
import java.util.Date;
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
    public Optional<Project> findByNameAndUserId(String name, String userId) {
        return projectRepository.findByNameAndUserId(name, userId);
    }

    @Override
    @SneakyThrows
    public Optional<Project> findById(String id) {
        return projectRepository.findById(id);
    }

    @Override
    public Optional<Project> findByIdAndUserId(String id, String userId) {
        return projectRepository.findByIdAndUserId(id, userId);
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
    @Transactional
    public boolean removeByNameAndUserId(String name, String userId) {
        Optional<Project> findProject = projectRepository.findByName(name);
        if (!findProject.isPresent()) { return false; }
        if (!findProject.get().getUser().getId().equals(userId)) {return false;}

        projectRepository.delete(findProject.get());
        return true;
    }

    @Override
    @Transactional
    public void removeById(String id) {
        projectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean removeByIdAndUserId(String id, String userId) {
        Optional<Project> findProject = projectRepository.findById(id);
        if (!findProject.isPresent()) { return false; }
        if (!findProject.get().getUser().getId().equals(userId)) {return false;}

        projectRepository.delete(findProject.get());
        return true;
    }

    @Override
    @SneakyThrows
    public List<Project> getList() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getListByUserId(String id) {
        return projectRepository.findByUserId(id);
    }

    @Override
    public List<Project> getListByDate(Date startDate, Date endDate) {
        return projectRepository.findByDate(startDate, endDate);
    }
}