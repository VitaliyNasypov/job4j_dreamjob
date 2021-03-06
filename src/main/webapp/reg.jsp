<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
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

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<script>
    $(function() {
        $("#city").autocomplete(function() {
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/job4j_dreamjob/city',
                data: JSON.stringify({text: $('#city').val()}),
                dataType: 'json'
            }).done(function(data) {
                document.getElementById("out").innerHTML = 'JSON. Текст принятого сообщения: ' + data.text;
            }).fail(function(err) {
                document.getElementById("out").innerHTML = 'JSON. Ошибка: ' + err;
            });
        });
    });
</script>

<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">Регистрация</div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" name="name" id="name">
                        <span id="resultCheckName" style="color: #ff0000;"></span>
                    </div>
                    <div class="form-group">
                        <label for="email">E-mail</label>
                        <input type="email" class="form-control" name="email" id="email">
                        <span id="resultCheckEmail" style="color: #ff0000;"></span>
                    </div>
                    <c:if test="${error != null}">
                        <p><span style="color: #ff0000;">
                            <c:out value="${error}"/>
                        </span></p>
                    </c:if>
                    <div class="form-group">
                        <label for="password">Пароль</label>
                        <input type="password" class="form-control" name="password" id="password">
                        <span id="resultCheckPassword" style="color: #ff0000;"></span>
                    </div>
                    <div class="form-group">
                        <label for="city">Город</label>
                        <input type="text" class="form-control" name="city" id="city">
                        <span id="resultCheckCity" style="color: #ff0000;"></span>
                    </div>
                    <p><input type="radio" name="group" value="hr"> HR-менеджер<br>
                        <input type="radio" name="group" value="candidate" checked> Соискатель</p>
                    <a href="<c:url value='/login.jsp'/>" class="btn btn-primary">Войти</a>
                    <button type="submit" class="btn btn-primary"
                            onclick="return validateReg()">Зарегистрироваться
                    </button>
                    <a href="<c:url value='/'/>" class="btn btn-primary">Вернутся на главную</a>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>