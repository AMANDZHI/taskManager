<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>User</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
  <div class="px-2">
   <ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link" href="/projects">Project</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/tasks">Task</a>
  </li>
  <li class="nav-item">
    <a class="nav-link active" href="/users">User</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/logout">Logout</a>
  </li>
</ul>
	

<form action="/editUser" method="post">
  <div class="form-group">
    <label for="exampleInputEmail1">User new name</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter new name User" name="newName" value="<c:out value="${user.name}"/>">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">User new login</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter new login User" name="newLogin" value="<c:out value="${user.login}"/>">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">User new password</label>
    <input type="password" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="newPassword">
  </div>
  <button type="submit" class="btn btn-dark">Submit</button>
  <div class="form-group block_hidden">
    <label for="exampleInputEmail1">User old login</label>
    <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Login User" name="oldLogin" value="<c:out value="${user.login}"/>" readonly="true">
  </div>
</form>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>