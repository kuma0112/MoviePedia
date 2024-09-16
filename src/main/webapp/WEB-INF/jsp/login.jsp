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
        <a href="/pages/login">로그인</a>
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
    document.getElementById('login-form').addEventListener('submit', async function(event) {
        event.preventDefault(); // 폼의 기본 제출 동작 방지

        // 입력 필드 값 가져오기
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // 유효성 검사
        if (!email || !password) {
            alert('이메일과 비밀번호를 모두 입력해주세요.');
            return;
        }

        try {
            // 로그인 API 요청
            const response = await fetch('/api/members/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                // 성공적인 응답일 경우 JWT 토큰을 헤더에서 가져오기
                const token = response.headers.get('Authorization');

                if (token) {
                    // JWT 토큰을 로컬 스토리지에 저장 (또는 세션 스토리지)
                    localStorage.setItem('jwtToken', token);
                    alert('로그인에 성공했습니다!');
                    // 로그인 성공 시 홈 또는 다른 페이지로 리디렉션
                    window.location.href = '/'; // 원하는 리디렉션 경로 설정
                } else {
                    alert('토큰이 전달되지 않았습니다.');
                }
            } else {
                const errorMessage = await response.text();
                alert(`로그인 실패: \${errorMessage}`);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('로그인 중 문제가 발생했습니다.');
        }
    });
</script>

</body>
</html>