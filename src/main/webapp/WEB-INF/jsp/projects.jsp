<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Project</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
  <div class="px-2">
  <ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link active" href="#">Project</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/tasks">Task</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/users">User</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/logout">Logout</a>
  </li>
</ul>
<form action="/sort" method="get">
  <div class="form-group">
            <p>
                <label for="dateStart">Дата начала: </label>
                <input class="form-control" type="date" id="dateStart" name="start"/>
            </p>
          </div>
          <div class="form-group">
            <p>
                <label for="date">Дата конца: </label>
                <input class="form-control" type="date" id="date" name="end"/>
            </p>
          </div>
            <p>
                <button type="submit" class="btn btn-dark">Показать</button>
            </p>
        </form>
	<table class="table table-dark" id="table">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Login</th>
      <th scope="col">Date</th>
      <th scope="col">Remove</th>
      <th scope="col">Edit</th>
    </tr>
  </thead>
  <tbody >

 <c:forEach var="p"  items="${projects}">
    <tr>
      <td scope="row" id="<c:out value="${p.id}"/>">${p.id}</td>
      <td>${p.name}</td>
      <td>${p.description}</td>
      <td>${p.getUser().getLogin()}</td>
      <td>${p.date}</td>
      <td>
       <a class="btn btn-dark" href="removeProject?name=<c:out value="${p.name}"/>" name="<c:out value="${p.name}"/>">REMOVE</a> 
        </td>
      <td>
        <a class="btn btn-dark eBtn" href="editProject?name=<c:out value="${p.id}"/>" name="<c:out value="${p.id}"/>">EDIT</a> 
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<button class="btn btn-dark cBtn" href="editProject">Create Project</button> 
</div>
<div class="myForm">
  <form action="/editProject" method="post" id="editProject">
    <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLongTitle">Project</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="newName">Project new name</label>
              <input type="text" class="form-control" id="newName" aria-describedby="emailHelp" placeholder="Enter name Project" name="newName" value="<c:out value="${project.name}"/>">
            </div>
            <div class="form-group">
              <label for="newDescr">Project new description</label>
              <input type="text" class="form-control" id="newDescr" aria-describedby="emailHelp" placeholder="Enter description Project" name="newDescr" value="<c:out value="${project.description}"/>">
            </div>
            <div class="form-group">
              <label for="login">User login</label>
              <input type="text" class="form-control" id="newLogin" placeholder="User Login" name="newLogin" value="<c:out value="${project.getUser().getLogin()}"/>">
            </div>
            <div class="form-group block_hidden">
              <label for="idProject">Project id</label>
              <input type="text" class="form-control" id="idProject" aria-describedby="emailHelp" placeholder="id Project" name="idProject" value="<c:out value="${project.id}"/>" readonly="true">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary sBtn">Save changes</button>
          </div>
        </div>
      </div>
    </div>
  </form>
  <form action="/createProject" method="post" id="createProject">
    <div class="modal fade" id="exampleModalLong2" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLongTitle2">Project</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-group">
              <label for="newName">Project new name</label>
              <input type="text" class="form-control" id="name" aria-describedby="emailHelp" placeholder="Enter name Project" name="newName" value="<c:out value="${project.name}"/>">
            </div>
            <div class="form-group">
              <label for="newDescr">Project new description</label>
              <input type="text" class="form-control" id="descr" aria-describedby="emailHelp" placeholder="Enter description Project" name="newDescr" value="<c:out value="${project.description}"/>">
            </div>
            <div class="form-group">
              <label for="login">User login</label>
              <input type="text" class="form-control" id="login" placeholder="User Login" name="newLogin" value="<c:out value="${project.getUser().getLogin()}"/>">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary createProject">Create Project</button>
          </div>
        </div>
      </div>
    </div>
  </form>
</div>
<script type="text/javascript" src="/resources/js/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script type="text/javascript" src="/resources/js/my.js"></script>
</body>
</html>