$(document).ready(function(){

    let emailValidator = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    let usernameValidator = /^[a-zA-Z0-9_.]{4,15}$/;
    let validEmail = false;
    let validPwd = false;
    let validPwdCheck = false;

    let slideEmail = 0;
    let slideUsername = 0; // Slide variable for username validation

    $("#email").keyup(function() {
        validateEmail();
    });

    $("#username").keyup(function (){
        validateUsername();
    });

    $("#password").keyup(function (){
        validatePassword();
    });

    $("#passwordCheck").keyup(function () {
        validatePasswordMatch();
    });

    $("#registrazione").submit(function(event) {
        validateAllFields(event);
    });

    function validateEmail() {
        let email = $("#email").val();

        if (email.match(emailValidator)) {
            $.ajax({
                type: "POST",
                url: "registrazione",
                data: {
                    mode: "checkEmail",
                    email: email,
                },
                dataType: "html",
                success: function(data) {
                    if (data.match("non disponibile")) {
                        validEmail = false;
                        if (slideEmail === 0) {
                            $("#error-email").slideDown();
                            slideEmail = 1;
                        }
                        $("#error-email").html("Email gi&agrave; in uso");
                    } else if (data.match("disponibile")) {
                        validEmail = true;
                        slideEmail = 0;
                        $("#error-email").slideUp();
                        $("#error-email").text("");
                    }
                }
            });

        } else {
            validEmail = false;
            if (slideEmail === 0) {
                $("#error-email").slideDown();
                slideEmail = 1;
            }
            $("#error-email").text("Inserisci un'email valida");
        }

        if (email === "") {
            validEmail = false;
            if (slideEmail === 1) {
                slideEmail = 0;
                $("#error-email").slideUp();
            }
            $("#error-email").text("");
        }
    }

    function validateUsername() {
        let username = $("#username").val();

        if(username.match(usernameValidator)){
            $.ajax({
                type: "POST",
                url: "registrazione",
                data: {
                    mode: "checkUsername",
                    username: username,
                },
                dataType: "html",
                success: function(data) {
                    if(data.match("non disponibile")) {
                        validUsername = false;
                        if(slideUsername === 0) {
                            $("#error-username").slideDown();
                            slideUsername = 1;
                        }
                        $("#error-username").html("Username non disponibile e/o gi&agrave; in uso");
                    } else if (data.match("disponibile")){
                        validUsername = true;
                        slideUsername = 0;
                        $("#error-username").slideUp();
                        $("#error-username").text("");
                    }
                }
            });

        } else {
            validUsername = false;
            if(slideUsername === 0) {
                $("#error-username").slideDown();
                slideUsername = 1;
            }
            $("#error-username").text("Username non valido");
        }

        if(username === ""){
            validUsername = false;
            if(slideUsername === 1) {
                slideUsername = 0;
                $("#error-username").slideUp();
            }
            $("#error-username").text("");
        }
    }

    function validatePassword() {
        let pwd = $("#password").val();
        let slidePwd = 0;

        if(pwd.length < 5){
            validPwd = false;
            if(slidePwd === 0){
                $("#error-pwd").text("Password troppo corta!");
                $("#error-pwd").slideDown();
                slidePwd = 1;
            } else {
                $("#error-pwd").text("Password troppo corta!");
            }
        } else {
            validPwd = true;

            if(slidePwd === 1){
                $("#error-pwd").slideUp();
                slidePwd = 0;
            }

            $("#error-pwd").text("");
        }
    }

    function validatePasswordMatch() {
        let pwd = $("#password").val();
        let pwdCheck = $("#passwordCheck").val();
        let slidePwd = 0;

        if(pwdCheck === pwd){
            validPwdCheck = true;
            $("#error-pwdchk").slideUp();
            $("#error-pwdchk").text("");
        } else {
            validPwdCheck = false;
            $("#error-pwdchk").slideDown();
            $("#error-pwdchk").text("Le password non corrispondono");
        }
    }

    function validateAllFields(event) {
        validateEmail();
        validateUsername();
        validatePassword();
        validatePasswordMatch();

        if(!(validEmail && validPwdCheck && validUsername && validPwd)){
            event.preventDefault();
        }
    }

});