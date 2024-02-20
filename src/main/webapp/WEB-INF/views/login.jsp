<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>로그인</h1>
<form action="/login" method="post">
  <ul>
    <li><input type="text" name="username" placeholder="USERNAME"></li>
    <li><input type="password" name="password" placeholder="PASSWORD"></li>
  </ul>
  <button type="submit">로그인</button>
  <a href="/oauth2/authorization/google">구글로 로그인</a>
  <button type="button" onclick="location.href='/sign-up'">회원가입</button>
</form>
</body>
</html>
