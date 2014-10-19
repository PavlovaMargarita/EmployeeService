<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    ${message}
    <br/><br/><br/>
    <form name='loginForm'
          action="j_spring_security_check" method='POST'>
        User<br/>
        <input type='text' name='username' value=''><br/>
        Password<br/>
        <input type='password' name='password' /><br/>
        <button type="submit">Login</button>

        <%--<input type="hidden" name="${_csrf.parameterName}"--%>
               <%--value="${_csrf.token}" />--%>
    </form>
</body>
</html>
