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
				<li><a class="nav_link" href="task">Task</a></li>
				<li><a class="nav_link" href="#">User</a></li>
			</ul>
		</nav>
	</header>
	<main>

		<div class="tabs">
		  <input class="tabs__tab" type="radio" id="tabs__tab1" name="tabstab" checked="checked"/>
		  <label class="tabs__title" for="tabs__tab1">Создать юзера</label>
		  <div class="tabs__text">
		  	<form class="form_create" action="/createUser" method="post">
  			<label>
  				Введите имя юзера
  				<input type="text" name="name">
  			</label>
  			<label>
  				Введите логин юзера
  				<input type="text" name="login">
  			</label>
  			<label>
  				Введите пароль для юзера
  				<input type="password" name="password">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab2" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab2">Изменить юзера</label>
		  <div class="tabs__text">
		  	<form class="form_update" action="/updateTask" method="post">
  			<label>
  				Введите логин юзера
  				<input type="text" name="login">
  			</label>
  			<label>
  				Введите новый логин юзера
  				<input type="text" name="newLogin">
  			</label>
  			<label>
  				Введите новое имя юзера
  				<input type="text" name="name">
  			</label>
  			<label>
  				Введите новый пароль для юзера
  				<input type="password" name="password">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab3" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab3">Найти юзера</label>
		  <div class="tabs__text">
			<form class="form_find" action="/findUser" method="post">
  			<label>
  				Введите логин юзера
  				<input type="text" name="login">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>
		  
		  <input class="tabs__tab" type="radio" id="tabs__tab4" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab4">Удалить юзера</label>
		  <div class="tabs__text">
		  	<form class="form_remove" action="/removeUser" method="post">
  			<label>
  				Введите логин юзера
  				<input type="text" name="login">
  			</label>
			<input type="submit" value="Отправить">
		</form>
		  </div>

		  <input class="tabs__tab" type="radio" id="tabs__tab5" name="tabstab"/>
		  <label class="tabs__title" for="tabs__tab5">Список юзеров</label>
		  <div class="tabs__text">
		  	<table>
		  		<thead>
		  			<th>Имя юзера</th>
		  			<th>Логин юзера</th>
		  		</thead>
		  		<tbody>
		  			<c:forEach var="user"  items="${list}">
                	<tr>
                    	<td>${user.name}</td>
                    	<td>${user.login}</td>
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