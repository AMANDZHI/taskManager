<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
	<title>TaskManager</title>
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<header>
		<nav>
			<ul>
				<li><a class="nav_link" href="project">Project</a></li>
				<li><a class="nav_link" href="#">Task</a></li>
				<li><a class="nav_link" href="user">User</a></li>
			</ul>
		</nav>
	</header>
	<main>
		<div class="tabs">
		  <input class="tabs__tab" type="radio" id="tabs__tab1" name="tabstab" checked="checked"/>
		  <label class="tabs__title" for="tabs__tab1">Создать таск</label>
		  <div class="tabs__text">
		  	<form class="form_create" action="/createTask" method="post">
  			<label>
  				Введите имя таска
  				<input type="text" name="name">
  			</label>
  			<label>
  				Введите описание таска
  				<input type="text" name="descr">
  			</label>
  			<label>
  				Введите имя проекта
  				<input type="text" name="nameProject">
  			</label>
  			<label>
  				Введите логин юзера
  				<input type="text" name="login">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab2" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab2">Изменить таск</label>
		  <div class="tabs__text">
		  	<form class="form_update" action="/updateProject" method="post">
  			<label>
  				Введите имя таска
  				<input type="text" name="name">
  			</label>
  			<label>
  				Введите новое имя таска
  				<input type="text" name="newName">
  			</label>
  			<label>
  				Введите новое описание таска
  				<input type="text" name="descr">
  			</label>
  			<label>
  				Введите новое имя проекта
  				<input type="text" name="nameProject">
  			</label>
  			<label>
  				Введите новый логин юзера
  				<input type="text" name="login">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab3" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab3">Найти таск</label>
		  <div class="tabs__text">
			<form class="form_find" action="/findTask" method="post">
  			<label>
  				Введите имя таска
  				<input type="text" name="name">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab4" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab4">Удалить таск</label>
		  <div class="tabs__text">
		  	<form class="form_remove" action="/removeTask" method="post">
  			<label>
  				Введите имя таска
  				<input type="text" name="name">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>

		  <input class="tabs__tab" type="radio" id="tabs__tab5" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab5">Список тасков</label>
		  <div class="tabs__text">
		  	<table>
		  		<thead>
		  			<th>Название таска</th>
		  			<th>Описание таска</th>
		  			<th>Название проекта</th>
		  			<th>Владелец таска</th>
		  		</thead>
		  		<tbody>
		  			<c:forEach var="task"  items="${list}">
                	<tr>
                    	<td>${task.name}</td>
                    	<td>${task.description}</td>
                    	<td>${task.projectId}</td>
                    	<td>${task.userId}</td>
                	</tr>
            		</c:forEach>
		  		</tbody>
		  	</table>
		  </div>
		</div>
	</main>
	<footer>
		<p>by Mandzhiev</p>
	</footer>
</body>
</html>