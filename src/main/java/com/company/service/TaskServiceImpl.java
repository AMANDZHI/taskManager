package com.company.service;

import com.company.api.Service;
import com.company.model.Task;
import com.company.repository.TaskRepositoryData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Qualifier("taskService")
public class TaskServiceImpl implements Service<String, Task> {

    @Autowired
    private TaskRepositoryData taskRepository;

    @Override
    @Transactional
    public Task save(Task object) {
        return taskRepository.save(object);
    }

    @Override
    @SneakyThrows
    public Optional<Task> findByName(String name) {
        return taskRepository.findByName(name);

    }

    @Override
    public Optional<Task> findByNameAndUserId(String name, String userId) {
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> findByIdAndUserId(String id, String userId) {
        return Optional.empty();
    }

    @Override
    @SneakyThrows
    @Transactional
    public Task update(Task object) {
        Task task = taskRepository.save(object);
        return task;
    }

    @Override
    @SneakyThrows
    @Transactional
    public boolean removeByName(String name) {
        Long answerDelete = taskRepository.deleteByName(name);
        return answerDelete > 0;
    }

    @Override
    public boolean removeByNameAndUserId(String name, String userId) {
        return false;
    }

    @Override
    public void removeById(String id) {
        taskRepository.deleteById(id);
    }

    @Override
    public boolean removeByIdAndUserId(String id, String userId) {
        Integer answerDelete = taskRepository.deleteByIdAndUserId(id, userId);
        return answerDelete > 0 ;
    }

    @Override
    @SneakyThrows
    public List<Task> getList() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getListByUserId(String id) {
        return taskRepository.findByUserId(id);
    }

    @Override
    public List<Task> getListByDate(Date startDate, Date endDate) {
        return null;
    }
}