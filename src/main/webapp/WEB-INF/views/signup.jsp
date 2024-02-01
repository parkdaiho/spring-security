<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>회원가입</h2>
<form action="/sign-up" method="post">
    <label for="username">id: </label><input type="text" id="username" name="username"><br>
    <label for="password">password: </label><input type="password" id="password" name="password"><br>
    <label for="nickname">nickname: </label><input type="text" id="nickname" name="nickname"><br>
    <label for="email">email: </label><input type="email" id="email" name="email"><br>
    <button type="submit">가입하기</button> <button type="button" onclick="location.href='/login'">돌아가기</button><br>
</form>
</body>
</html>
