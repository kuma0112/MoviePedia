<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- CSS 파일 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- 네비게이션 바 -->
<div class="navbar">
    <div class="logo">MoviePedia</div>
    <div class="search-bar">
        <input type="text" placeholder="콘텐츠, 인물, 컬렉션 검색">
        <button>검색</button>
    </div>
    <div class="user-options">
        <a href="/pages/login">로그인</a>
    </div>
</div>

<!-- 회원가입 폼 섹션 -->
<div class="box-office">
    <h2>회원가입</h2>
    <form action="${pageContext.request.contextPath}/signup" method="POST">
        <div class="form-group">
            <label for="username">사용자 이름:</label><br>
            <input type="text" id="username" name="username" required><br><br>
        </div>
        <div class="form-group">
            <label for="email">이메일:</label><br>
            <input type="email" id="email" name="email" required><br><br>
        </div>
        <div class="form-group">
            <label for="password">비밀번호:</label><br>
            <input type="password" id="password" name="password" required><br><br>
        </div>
        <div class="form-group">
            <label for="confirm-password">비밀번호 확인:</label><br>
            <input type="password" id="confirm-password" name="confirm-password" required><br><br>
        </div>
        <button type="submit">회원가입</button>
    </form>
</div>

</body>
</html>
