<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.company.model.Task" %>
<% Object objects =request.getAttribute("tasks");
  Collection<Task> list = (Collection<Task>)objects;
 %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Task</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
  <div class="px-2">
  <ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link" href="/project">Project</a>
  </li>
  <li class="nav-item">
    <a class="nav-link active" href="#">Task</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/user">User</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/logout">Logout</a>
  </li>
</ul>
	<table class="table table-dark">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Name</th>
      <th scope="col">Description</th>
      <th scope="col">Project name</th>
      <th scope="col">Edit</th>
      <th scope="col">Remove</th>
    </tr>
  </thead>
  <tbody>
    <% int index = 1; %>
    <% for (Task t: list) { %>

  
    <tr>
      <th scope="row"><%=index%></th>
      <td><%=t.getName()%></td>
      <td><%=t.getDescription()%></td>
      <td><%=t.getProject().getName()%></td>
      <td>
       <a class="btn btn-dark" href="removeTask?name=<%=t.getName()%>">REMOVE</a>
        </td>
      <td>
        <a class="btn btn-dark" href="editTask?name=<%=t.getName()%>">EDIT</a>
      </td>
    </tr>
<% index++;%>
<%}%>

  </tbody>
</table>

<form action="/createTask" method="post">
  <div class="form-group">
    <label for="exampleInputEmail1">Task name</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter name Task" name="name">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">Task description</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter description Task" name="description">
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Project name</label>
    <input type="text" class="form-control" id="exampleInputPassword1" placeholder="name Project" name="nameProject">
  </div>
  <button type="submit" class="btn btn-dark">Create Task</button>
</form>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>