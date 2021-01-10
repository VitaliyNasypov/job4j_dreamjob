function validateLogin() {
    let checkValidateLogin = true;
    const regEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if ($('#email').val() === '' || !regEmail.test($('#email').val())) {
        document.getElementById("resultCheckEmail").innerHTML = 'Введите E-mail';
        checkValidateLogin = false;
    } else {
        document.getElementById("resultCheckEmail").innerHTML = '';
    }
    if ($('#password').val() === '') {
        document.getElementById("resultCheckPassword").innerHTML = 'Введите Пароль';
        checkValidateLogin = false;
    } else {
        document.getElementById("resultCheckPassword").innerHTML = '';
    }
    return checkValidateLogin;
}

function validateReg() {
    let checkName = validateName();
    let checkEmailPassword = validateLogin();
    return checkName && checkEmailPassword;
}

function validateName() {
    let checkValidateName = true;
    if ($('#name').val() === '') {
        document.getElementById("resultCheckName").innerHTML = 'Введите Имя';
        checkValidateName = false;
    } else {
        document.getElementById("resultCheckName").innerHTML = '';
    }
    return checkValidateName;
}

function validatePostEdit() {
    let checkValidatePostEdit = validateName();
    if ($('#description').val() === '') {
        document.getElementById("resultCheckDescription").innerHTML = 'Введите Описание';
        checkValidatePostEdit = false;
    } else {
        document.getElementById("resultCheckDescription").innerHTML = '';
    }
    return checkValidatePostEdit;
}

function validateCandidateEdit() {
    let checkValidateCandidateEdit = validateName();
    if ($('#lastName').val() === '') {
        document.getElementById("resultCheckLastName").innerHTML = 'Введите Фамилию';
        checkValidateCandidateEdit = false;
    } else {
        document.getElementById("resultCheckLastName").innerHTML = '';
    }
    if ($('#age').val() <= 0) {
        document.getElementById("resultCheckAge").innerHTML = 'Введите Возраст';
        checkValidateCandidateEdit = false;
    }
    else {
        document.getElementById("resultCheckAge").innerHTML = '';
    }
    const regCity =/[a-zA-Z]+/;
    if ($('#city').val() <= 0 || !regCity.test($('#city').val())) {
        document.getElementById("resultCheckCity").innerHTML = 'Введите Город';
        checkValidateCandidateEdit = false;
    }
    else {
        document.getElementById("resultCheckCity").innerHTML = '';
    }
    return checkValidateCandidateEdit;
}

function validateCandidateEditPhoto() {
    let checkValidateCandidateEdit = true;
    if ($('#photo').val() === '') {
        document.getElementById("resultCheckPhoto").innerHTML = 'Добавьте фотографию';
        checkValidateCandidateEdit = false;
    } else {
        document.getElementById("resultCheckPhoto").innerHTML = '';
    }
    return checkValidateCandidateEdit;
}
