package com.company.api;

import com.company.dto.ProjectDTO;
import com.company.model.Project;
import com.company.model.Session;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface ProjectWebServiceEndpoint {

    @WebMethod
    ProjectDTO saveProject(@WebParam(name="name") String name, @WebParam(name="description") String description , @WebParam(name="session") Session session);

    @WebMethod
    ProjectDTO findByNameProject(@WebParam(name="project_name") String name,@WebParam(name="session") Session session);

    @WebMethod
    ProjectDTO findByIdProject(@WebParam(name="project_id") String id,@WebParam(name="session") Session session);

    @WebMethod
    ProjectDTO updateProject(@WebParam(name="name") String name, @WebParam(name="newName") String newName, @WebParam(name="newDescription") String newDescription, @WebParam(name="session") Session session);

    @WebMethod
    boolean removeByNameProject(@WebParam(name="project_name") String name,@WebParam(name="session") Session session);

    @WebMethod
    List<ProjectDTO> getListProject(@WebParam(name="session") Session session);
}
