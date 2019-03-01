package com.company.service;

import com.company.api.Service;
import com.company.model.Task;
import com.company.repository.TaskRepositoryData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;
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
    @SneakyThrows
    public Optional<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    @Override
    @SneakyThrows
    @Transactional
    public Task update(Task object) {
        Task task= taskRepository.save(object);
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
    @SneakyThrows
    public List<Task> getList() {
        return taskRepository.findAll();
    }
}