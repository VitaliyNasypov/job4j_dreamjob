$(function () {
    $("#city").autocomplete({
        source: 'http://localhost:8080/job4j_dreamjob/city',
        delay: 500,
        autoFill: true,
        minLength: 1,
    });
});