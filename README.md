# job4j_dreamjob
[![Build Status](https://travis-ci.com/VitaliyNasypov/job4j_dreamjob.svg?branch=master)](https://travis-ci.com/VitaliyNasypov/job4j_dreamjob)
[![codecov](https://codecov.io/gh/VitaliyNasypov/job4j_dreamjob/branch/master/graph/badge.svg?token=DA9SAMFJ7T)](https://codecov.io/gh/VitaliyNasypov/job4j_dreamjob)
<br>
<br>
Техническое задание - проект "Работа мечты".

Краткое описание проекта:
- В системе два типа пользователей: Кандидаты и кадровики.
- Кандидаты могут публиковать резюме и просматривать вакансии. 
- Кадровики могут публиковать вакансии о работе и просматривать кандидатов. 
- Система хранения паролей организованна в виде хэша (алгоритм + количество итераций + соль + хэш пароля). 
- Система хранения паролей, позволяет менять алгоритм для новых паролей, сохраняя работоспособность старых паролей пользователей.
- Система авторизации и регистрации.
- Фильтры для ограничения доступа не зарегистрированных пользователей.
- Cross-origin resource sharing
- Шаблон MVC
<br>
<br>


В данном проекте использовались:
- Servlet, JSP, JSTL.
- HTML, CSS, JavaScript, jQuery.
- PostgreSQL, Liquibase - все данные хранились в БД. По мере необходимости извлекались и использовались.
- Apache Commons DBCP - Для запросов к БД использовался Thread Pool.
- Apache Commons FileUpload - Использовался для загрузки изображений кандидатов.
- Mockito, Powermock, JUnit5 - Использовались для тестирования проекта.
- JSON - Использовался для обмена данными между jQuery и Servlet.
- SLF4J, Logback - Ведение логов в проекте.
