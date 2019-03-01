package com.company.endpoint;

import com.company.api.*;
import com.company.model.Domain;
import com.company.model.Project;
import com.company.model.Task;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@Component
@WebService(endpointInterface = "com.company.api.SerializationWebServiceEndpoint")
public class SerializationWebServiceEndpointImpl implements SerializationWebServiceEndpoint {

    @Autowired
    private DomainService domainService;

    @Autowired
    private SerializationService serializationService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("projectService")
    private Service<String, Project> projectService;

    @Autowired
    @Qualifier("taskService")
    private Service<String, Task> taskService;

    @WebMethod
    @Override
    public void writeObjectsToFiles() {
        List<Project> listProjects = domainService.getDomain().getProjectList();
        List<Task> listUsers = domainService.getDomain().getTaskList();
        List<User> listTasks = domainService.getDomain().getUserList();

        String filePathUsers = "exportData/users.txt";
        String filePathTasks = "exportData/tasks.txt";
        String filePathProjects = "exportData/projects.txt";

        if (listProjects.size() != 0) {
            serializationService.writeObjectToFile(filePathProjects, listProjects);
        }
        if (listUsers.size() != 0) {
            serializationService.writeObjectToFile(filePathUsers, listUsers);
        }
        if (listTasks.size() != 0) {
            serializationService.writeObjectToFile(filePathTasks, listTasks);
        }
    }

    @WebMethod
    @Override
    public void readFilesToObjects() {
        String filePathUsers = "exportData/users.txt";
        String filePathTasks = "exportData/tasks.txt";
        String filePathProjects = "exportData/projects.txt";

        List<Project> listProjects = serializationService.readFileToObject(filePathProjects);
        List<User> listUsers = serializationService.readFileToObject(filePathUsers);
        List<Task> listTasks = serializationService.readFileToObject(filePathTasks);

        for (User u: listUsers) {
            if (!userService.findByLogin(u.getLogin()).isPresent()) {
                userService.save(u);
            }
        }

        for (Project p: listProjects) {
            if (!projectService.findById(p.getId()).isPresent()) {
                projectService.save(p);
            }
        }

        for (Task t: listTasks) {
            if (!taskService.findById(t.getId()).isPresent()) {
                taskService.save(t);
            }
        }
    }

    @WebMethod
    @Override
    public void writeAllToJson() {
        String filePath = "exportData/all.json";
        Domain domain = domainService.getDomain();
        serializationService.writeAllToJson(filePath, domain);
    }

    @WebMethod
    @Override
    public void writeAllToXml() {
        String filePath = "exportData/all.xml";
        Domain domain = domainService.getDomain();
        serializationService.writeAllToXml(filePath, domain);
    }

    @WebMethod
    @Override
    public void readJsonToObjects() {
        String filePath = "exportData/all.json";
        serializationService.readJsonToObjects(filePath);
    }

    @WebMethod
    @Override
    public void readXmlToObjects() {
        String filePath = "exportData/all.xml";
        serializationService.readXmlToObjects(filePath);
    }
}
