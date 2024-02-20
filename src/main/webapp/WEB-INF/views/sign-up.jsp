<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>회원가입</h1>
<form action="/sign-up" method="post">
  <c:choose>
    <c:when test="${oauth2UserInfo != null}">
      <input type="text" id="nickname" placeholder="NICKNAME">
      <input type="text" id="email" placeholder="EMAIL" value="${oauth2UserInfo.email}" disabled>
      <input type="hidden" id="provider" value="${oauth2UserInfo.provider}">
      <button type="button" id="oauth2_sign_up_btn">가입하기</button>
    </c:when>
    <c:otherwise>
      <input type="text" id="username" placeholder="USERNAME">
      <input type="password" id="password" placeholder="PASSWORD">
      <input type="text" id="nickname" placeholder="NICKNAME">
      <input type="text" id="email" placeholder="EMAIL">
      <button type="button" id="sign_up_btn">가입하기</button>
    </c:otherwise>
  </c:choose>
</form>
<script src="/js/sign-up.js"></script>
</body>
</html>
