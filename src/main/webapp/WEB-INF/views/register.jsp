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
                    <div id="registerForm-error" class="alert alert-danger" role="alert" style="display: none">
                        <p id="registerForm-usernameError" style="margin: 0"></p>
                        <p id="registerForm-passwordError" style="margin: 0"></p>
                        <p id="registerForm-passwordConfirmationError" style="margin: 0"></p>
                    </div>
                    <form id="registerForm" action="register" method="post">
                        <div class="form-group">
                            <label>Name</label> <input type="text" class="form-control"
                                                       placeholder="Name" name="username" value="${userName }">
                        </div>
                        <div class="form-group">
                            <label>Password</label> <input type="password"
                                                           class="form-control" placeholder="Password" name="password">
                        </div>
                        <div class="form-group">
                            <label>Confirm password</label> <input type="password"
                                                                   class="form-control"
                                                                   placeholder="Password confirmation"
                                                                   name="passwordConfirmation">
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
                var userNameError = $('#registerForm-usernameError');
                var passwordError = $('#registerForm-passwordError');
                var passwordConfirmationError = $('#registerForm-passwordConfirmationError');
                userNameError.hide();
                passwordError.hide();
                passwordConfirmationError.hide();
                $.each(result, function (index, element) {

                        if (index === "success") {
                            $('registerForm-error').hide();
                            window.location.replace('/');
                        } else if (index === "username") {
                            userNameError.html(element);
                            userNameError.show();
                            $('#registerForm-error').show();
                        } else if (index === "password") {
                            passwordError.html(element);
                            passwordError.show();
                            $('#registerForm-error').show();
                        } else if (index === "passwordConfirmation") {
                            passwordConfirmationError.html(element);
                            passwordConfirmationError.show();
                            $('#registerForm-error').show();
                        }
                    }
                )
            }
        });
    }
</script>
