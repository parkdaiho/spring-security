<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>index</h1>
<a href="/login">로그인</a>
<c:if test="${userNickname != null}">
<button type="button" id="logout-btn">${loginUser} 로그아웃</a>
</c:if>
<script src="/js/header.js"></script>
</body>
</html>
