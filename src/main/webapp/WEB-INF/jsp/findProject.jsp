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
				    <a class="nav_link" href="project">Project</a>
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
		<div>
            <p><c:out value = "${project.name}"/></p>
            <p><c:out value = "${project.description}"/></p>
		</div>
	</main>
	<footer></footer>
</body>
</html>