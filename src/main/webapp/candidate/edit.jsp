<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.job4j.dream.store.PsqlStore" %>
<%@ page import="ru.job4j.dream.model.Candidate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="../js/autocomplete-city.js"></script>
    <script src="../js/validate.js"></script>
    <title>Работа мечты</title>
</head>

<body>
<% String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", "", 0);
    if (Integer.parseInt(request.getParameter("id")) > 0) {
        candidate = PsqlStore.instOf().findByIdCandidate(Integer.parseInt(id));
    }
    if (request.getAttribute("imageFile") != null) {
        candidate.setIdPhoto((String) request.getAttribute("imageFile"));
    }
    request.setAttribute("Candidate", candidate);
%>
<div class="container pt-3">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Вернутся на главную</a>
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
                <c:choose>
                    <c:when test="${Candidate.id == 0}"> Добавить нового кандидата </c:when>
                    <c:when test="${Candidate.id > 0}"> Редактирование кандидата </c:when>
                </c:choose>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <td><img height="30%"
                             src="<c:url value='/download?image=${Candidate.idPhoto}'/>"/>
                        <br> <br>
                        <c:if test="${Candidate.id > 0}">
                            <a href="<c:url value='/download?image=${Candidate.idPhoto}'/>">Download photo</a>
                        </c:if>
                        <span id="resultCheckPhoto" style="color: #ff0000;"></span>
                    </td>
                    <td><h2>Upload image</h2><br>
                        <h6>Only image format. <br>File size not more than 1MB.</h6>
                        <form action="<c:url value='/upload?id=${Candidate.id}'/>" method="post"
                              enctype="multipart/form-data">
                            <div class="checkbox">
                                <input type="file" name="file" id="photo">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-primary"
                                    onclick="return validateCandidateEditPhoto()">Add photo</button>
                        </form>
                    </td>
                </tr>
                </thead>
            </table>
            <div class="card-body">
                <form action=" <c:url value='/candidates.do'/>"
                      method="post">
                    <input type="hidden" class="form-control" name="imageId"
                           value="<c:out value="${Candidate.idPhoto}"/>">
                    <input type="hidden" class="form-control" name="id"
                           value="<c:out value="${Candidate.id}"/>">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" name="firstName" id="name"
                               value="<c:out value="${Candidate.firstName}"/>">
                        <span id="resultCheckName" style="color: #ff0000;"></span>
                    </div>
                    <div class="form-group">
                        <label for="lastName">Фамилия</label>
                        <input type="text" class="form-control" name="lastName" id="lastName"
                               value="<c:out value="${Candidate.lastName}"/>">
                        <span id="resultCheckLastName" style="color: #ff0000;"></span>
                    </div>
                    <div class="form-group">
                        <label for="city">Город</label>
                        <input type="text" class="form-control" name="city" id="city"
                               value="<c:out value="${Candidate.city}"/>">
                        <span id="resultCheckCity" style="color: #ff0000;"></span>
                    </div>
                    <div class="form-group">
                        <label for="age">Возраст</label>
                        <c:choose>
                            <c:when test="${Candidate.id == 0}">
                                <input type="number" class="form-control" name="age" align="left" id="age"
                                       value=""></c:when>
                            <c:when test="${Candidate.id > 0}">
                                <input type="number" class="form-control" name="age" align="left" id="age"
                                       value="<c:out value="${Candidate.age}"/>"></c:when>
                        </c:choose>
                        <span id="resultCheckAge" style="color: #ff0000;"></span>
                    </div>
                    <button type="submit" class="btn btn-primary"
                            onclick="return validateCandidateEdit()">Сохранить</button>
                </form>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>