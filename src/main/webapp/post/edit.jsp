<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Post" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="../js/validate.js"></script>
    <title>Работа мечты</title>
</head>

<body>
<% String id = request.getParameter("id");
    Post post = new Post(0, "", "", LocalDateTime.now(ZoneId.of("UTC")));
    if (id != null) {
        post = PsqlStore.instOf().findByIdPost(Integer.parseInt(id));
    }
    request.setAttribute("Post", post);
%>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/index.do'/>">Вернутся на главную</a>
            </li>
            <li class="nav-item">
                <c:choose>
                    <c:when test="${user != null}"><a class="nav-link"
                                                      href="<c:url value='/index.do?out=${true}'/>"><c:out
                            value="${user.name}"/> | Выйти</a></c:when>
                    <c:when test="${user == null}"><a class="nav-link" href="<c:url value='/login.jsp'/>">Войти</a>
                    </c:when>
                </c:choose>
            </li>
        </ul>
        <div class="card" style="width: 100%">
            <div class="card-header">
                <% if (id == null) { %>
                Добавить новую вакансию
                <% } else { %>
                Редактирование вакансии
                <% } %>
            </div>
            <div class="card-body">
                <form action="<c:url value='/posts.do?id=${Post.id}'/>" method="post">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" name="name" id="name"
                               value="<c:out value="${Post.name}"/>">
                        <span id="resultCheckName" style="color: #ff0000;"></span>
                    </div>
                    <div class="form-group">
                        <label for="description">Описание</label>
                        <input type="text" class="form-control" name="description" id="description"
                               value="<c:out value="${Post.description}"/>">
                        <span id="resultCheckDescription" style="color: #ff0000;"></span>
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="return validatePostEdit()">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>