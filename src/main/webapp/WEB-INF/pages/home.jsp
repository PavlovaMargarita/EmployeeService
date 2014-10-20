<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    ${message}
    <br/><br/><br/>
    <form name="f" action="/j_spring_security_check" method="POST">
        <table>
            <tbody><tr><td>User:</td><td><input type="text" name="j_username" value=""></td></tr>
            <tr><td>Password:</td><td><input type="password" name="j_password"></td></tr>
            <tr><td colspan="2"><input name="submit" type="submit" value="Login"></td></tr>
            </tbody></table>
    </form>
    <a href="/user">User page(for logged users)</a>
</body>
</html>
