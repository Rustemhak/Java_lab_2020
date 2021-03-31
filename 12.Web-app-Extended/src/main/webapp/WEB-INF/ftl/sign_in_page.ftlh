<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>

<h2><a href="?lang=ru">РУ</a> </h2>
<h2><a href="?lang=en">EN</a> </h2>
<h1><@spring.message 'sign_in_page.title'/></h1>
<@spring.bind "userSignInForm"/>
<#if namesErrorMessage??>
    <p style=".error">${namesErrorMessage}</p>
</#if>
<form action="/signIn" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label>Email</label>
    <@spring.formInput "userSignInForm.email"/>
    <@spring.showErrors "<br>" "error"/>
    <label><@spring.message 'sign_in_page.password'/></label>
    <@spring.formPasswordInput "userSignInForm.password"/>
    <@spring.showErrors "<br>" "error"/>
    <label>
        <input type="checkbox" name="remember-me"><@spring.message 'sign_in_page.remember'/>
    </label>
    <input type="submit" value=<@spring.message 'sign_in_page.title'/>>
</form>
</body>
</html>