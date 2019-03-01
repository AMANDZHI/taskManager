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
				<li>
				    <a class="nav_link" href="#">Project</a>
				</li>
				<li>
				    <a class="nav_link" href="task">Task</a>
				</li>
				<li>
				    <a class="nav_link" href="user">User</a>
				 </li>
			</ul>
		</nav>
	</header>
	<main>
		<div class="tabs">
		  <input class="tabs__tab" type="radio" id="tabs__tab1" name="tabstab" checked="checked"/>
		  <label class="tabs__title" for="tabs__tab1">Создать проект</label>
		  <div class="tabs__text">
		  	<form class="form_create" action="/createProject" method="post">
	  			<label>
	  				Введите имя проекта
	  				<input type="text" name="name">
	  			</label>
	  			<label>
	  				Введите описание проекта
	  				<input type="text" name="descr">
	  			</label>
	  			<label>
	  				Введите логин юзера
	  				<input type="text" name="login">
	  			</label>
				<input type="submit" value="Отправить">
			</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab2" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab2">Изменить проект</label>
		  <div class="tabs__text">
		  	<form class="form_update" action="/updateProject" method="post">
	  			<label>
	  				Введите имя проекта
	  				<input type="text" name="name">
	  			</label>
	  			<label>
	  				Введите новое имя проекта
	  				<input type="text" name="newName">
	  			</label>
	  			<label>
	  				Введите новое описание проекта
	  				<input type="text" name="descr">
	  			</label>
	  			<label>
	  				Введите новый логин юзера
	  				<input type="text" name="login">
	  			</label>
				<input type="submit" value="Отправить">
			</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab3" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab3">Найти проект</label>
		  <div class="tabs__text">
			<form class="form_find" action="/findProject" method="post">
	  			<label>
	  				Введите имя проекта
	  				<input type="text" name="name">
	  			</label>
				<input type="submit" value="Отправить">
			</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab4" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab4">Удалить проект</label>
		  <div class="tabs__text">
		  	<form class="form_remove" action="/removeProject" method="post">
  				<label>
  					Введите имя проекта
  					<input type="text" name="name">
  				</label>
				<input type="submit" value="Отправить">
			</form>
		  </div>

		  <input class="tabs__tab" type="radio" id="tabs__tab5" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab5">Список проектов</label>
		  <div class="tabs__text">
		  	<table>
		  		<thead>
		  			<th>Название проекта</th>
		  			<th>Описание проекта</th>
		  			<th>Владелец проекта</th>
		  		</thead>
		  		<tbody>
		  			<c:forEach var="project"  items="${list}">
                	<tr>
                    	<td>${project.name}</td>
                    	<td>${project.description}</td>
                    	<td>${project.userId}</td>
                	</tr>
            	</c:forEach>
		  		</tbody>
		  	</table>
		  </div>
		</div>
	</main>
	<footer></footer>
</body>
</html>