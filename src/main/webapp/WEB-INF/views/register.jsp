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
                                                       placeholder="Name" name="username" value="${userName }">
                            <span id="username-helpBlock" class="help-block"></span>
                        </div>
                        <div id="password" class="form-group">
                            <label class="control-label">Password</label> <input type="password"
                                                           class="form-control" placeholder="Password" name="password">
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
                $('#username').removeClass('has-error');
                $('#password').removeClass('has-error');
                $('#passwordConfirmation').removeClass('has-error');
                $('#username-helpBlock').hide();
                $('#password-helpBlock').hide();
                $('#passwordConfirmation-helpBlock').hide();
                $.each(result, function (index, element) {
                        if (index === "success") {
                            window.location.replace('/');
                        } else if (index === "username") {
                            $('#username').addClass("has-error");
                            $('#username-helpBlock').html(element);
                            $('#username-helpBlock').show();
                        } else if (index === "password") {
                            $('#password').addClass("has-error");
                            $('#password-helpBlock').html(element);
                            $('#password-helpBlock').show();
                        } else if (index === "passwordConfirmation") {
                            $('#passwordConfirmation').addClass("has-error");
                            $('#passwordConfirmation-helpBlock').html(element);
                            $('#passwordConfirmation-helpBlock').show();
                        }
                    }
                )
            }
        });
    }
</script>
