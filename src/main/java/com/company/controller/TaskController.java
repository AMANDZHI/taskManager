package com.company.controller;

import com.company.api.Service;
import com.company.dto.TaskDTO;
import com.company.model.Task;
import com.company.model.User;
import com.company.service.EntityServiceToDTO;
import com.company.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    @Qualifier("taskService")
    private Service<String, Task> taskService;

    @GetMapping("task")
    public ModelAndView getTasks() {
        ModelAndView modelAndView = new ModelAndView();
        List<Task> list = taskService.getList();
        List<TaskDTO> forClientList = EntityServiceToDTO.getTasks(list);

        modelAndView.addObject("list", forClientList);
        modelAndView.setViewName("task");
        return modelAndView;
    }
}
