<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<div class="container" id="form-register">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Register</h3>
                </div>

                <div class="panel-body">
                    <form id="registerForm" action="register" method="post">
                        <div id="username" class="form-group">
                            <label class="control-label">Name</label> <input type="text" class="form-control"
                                                                             placeholder="Name" name="username"
                                                                             value="${userName }">
                            <span id="username-helpBlock" class="help-block"></span>
                        </div>
                        <div id="password" class="form-group">
                            <label class="control-label">Password</label> <input type="password"
                                                                                 class="form-control"
                                                                                 placeholder="Password" name="password">
                            <span id="password-helpBlock" class="help-block"></span>
                        </div>
                        <div id="passwordConfirmation" class="form-group">
                            <label class="control-label">Confirm password</label> <input type="password"
                                                                                         class="form-control"
                                                                                         placeholder="Password confirmation"
                                                                                         name="passwordConfirmation">
                            <span id="passwordConfirmation-helpBlock" class="help-block"></span>
                        </div>
                        <input name="${_csrf.parameterName }" value="${_csrf.token }"
                               type="hidden">
                        <button class="btn btn-default" type="button"
                                onclick="register()">Register
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function register() {
        $.ajax({
            type: 'post',
            url: 'register',
            dataType: 'json',
            data: $('#registerForm').serialize(),
            success: function (result) {
                var userNameError = $('#username');
                var passwordError = $('#password');
                var passwordConfirmationError = $('#passwordConfirmation');

                var usernameHelpBlock = $('#username-helpBlock');
                var passwordHelpBlock = $('#password-helpBlock');
                var passwordConfirmationHelpBlock = $('#passwordConfirmation-helpBlock');

                userNameError.removeClass('has-error');
                passwordError.removeClass('has-error');
                passwordConfirmationError.removeClass('has-error');
                usernameHelpBlock.hide();
                passwordHelpBlock.hide();
                passwordConfirmationHelpBlock.hide();
                $.each(result, function (index, element) {
                        if (index === "success") {
                            window.location.replace('/');
                        } else if (index === "username") {
                            userNameError.addClass("has-error");
                            usernameHelpBlock.html(element);
                            usernameHelpBlock.show();
                        } else if (index === "password") {
                            passwordError.addClass("has-error");
                            passwordHelpBlock.html(element);
                            passwordHelpBlock.show();
                        } else if (index === "passwordConfirmation") {
                            passwordConfirmationError.addClass("has-error");
                            passwordConfirmationHelpBlock.html(element);
                            passwordConfirmationHelpBlock.show();
                        }
                    }
                )
            }
        });
    }
</script>
