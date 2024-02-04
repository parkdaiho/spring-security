<%@ page import="me.parkdaiho.config.oauth2.OAuth2UserInfo" %>
<%@ page import="me.parkdaiho.domain.OAuth2Provider" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String email = (String) request.getAttribute("email");
    OAuth2Provider provider = (OAuth2Provider) request.getAttribute("provider");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/oauth2/sign-up" method="post">
    <input type="hidden" value="<%= email%>" name="email">
    <input type="hidden" value="<%= provider%>" name="provider">
    <label for="nickname">닉네임: </label><input type="text" id="nickname" name="nickname">
    <button type="submit">가입하기</button>
</form>
</body>
</html>
