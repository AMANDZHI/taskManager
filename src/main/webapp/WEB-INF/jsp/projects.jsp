<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Project</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" href="">
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
	<table class="table table-dark">
  <thead>
    <tr>
      <th scope="col">ID</th>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Login</th>
      <th scope="col">Remove</th>
      <th scope="col">Edit</th>
    </tr>
  </thead>
  <tbody>

 <c:forEach var="p"  items="${projects}">
    <tr>
      <th scope="row">${p.id}</th>
      <td>${p.name}</td>
      <td>${p.description}</td>
      <td>${p.getUser().getLogin()}</td>
      <td>
       <a class="btn btn-dark" href="removeProject?name=<c:out value="${p.name}"/>">REMOVE</a> 
        </td>
      <td>
        <a class="btn btn-dark" href="editProject?name=<c:out value="${p.name}"/>">EDIT</a> 
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<a class="btn btn-dark" href="editProject">Create Project</a> 
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>