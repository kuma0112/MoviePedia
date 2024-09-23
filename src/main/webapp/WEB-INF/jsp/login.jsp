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
    <link rel="icon" href="data:;base64,=">
</head>
<body>
<!-- 네비게이션 바 -->
<div class="navbar">
    <div class="logo">
        <a href="/">
            <img src="${pageContext.request.contextPath}/images/logo.png" alt="MoviePedia 로고">
        </a>
    </div>
    <div class="search-bar">
        <input type="text" placeholder="콘텐츠, 인물, 컬렉션 검색">
        <button>검색</button>
    </div>
    <div class="user-options">
        <a href="/pages/register">회원가입</a>
    </div>
</div>


<!-- 로그인 폼 섹션 -->
<div class="login-form-container">
    <h2>로그인</h2>
    <form id="login-form" method="POST" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
            <label for="email">이메일:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">로그인</button>
    </form>
    <div class="login-footer">
        계정이 없으신가요? <a href="/pages/register">회원가입</a>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {

        document.getElementById('login-form').addEventListener('submit', async function(event) {
            event.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            if (!email || !password) {
                alert('이메일과 비밀번호를 모두 입력해주세요.');
                return;
            }

            try {
                const response = await fetch('/api/members/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ email, password })
                });

                if (response.ok) {
                    const token = response.headers.get('Authorization');
                    const data = await response.json();
                    const nickname = data.nickname;

                    if (token && nickname) {
                        localStorage.setItem('token', token);
                        localStorage.setItem('nickname', nickname);
                        alert('로그인에 성공했습니다!');
                        window.location.href = '/';
                    } else {
                        alert('토큰 또는 닉네임이 전달되지 않았습니다.');
                    }
                } else {
                    const errorMessage = await response.text();
                    alert(`로그인 실패: ${errorMessage}`);
                }
            } catch (error) {
                console.error('Error:', error);
                alert('로그인 중 문제가 발생했습니다.');
            }
        });
    });
</script>

</body>
</html>