<%--
  Created by IntelliJ IDEA.
  User: parkdaeho
  Date: 1/31/24
  Time: 7:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<button type="button" onclick="location.href='/'">홈으로</button>
<form action="/login" method="post">
    <label for="username">id: </label> <input type="text" id="username"><br>
    <label for="password">password: </label> <input type="password" id="password"><br>
    <button type="submit">로그인</button> <button type="button" onclick="location.href='/signup'">회원가입</button><br>
</form>
</body>
</html>
