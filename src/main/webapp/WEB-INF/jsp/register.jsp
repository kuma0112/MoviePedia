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
    <div class="user-options">
        <a href="/pages/login">로그인</a>
    </div>
</div>

<!-- 회원가입 폼 섹션 -->
<div class="signup-form-container">
    <h2>회원가입</h2>
    <form id="signup-form">
        <div class="form-group">
            <label for="nickname">닉네임: (1자 이상이어야 합니다.)</label>
            <input type="text" id="nickname" name="nickname" required>
        </div>
        <div class="form-group">
            <label for="email">이메일: (올바른 이메일 형식이어야 합니다.)</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">비밀번호: (영문자와 숫자 포함, 7자리 이상이어야 합니다.)</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="confirm-password">비밀번호 확인: </label>
            <input type="password" id="confirm-password" name="confirm-password" required>
        </div>
        <button type="submit">회원가입</button>
    </form>
    <div class="signup-footer">
        이미 가입하셨나요? <a href="/pages/login">로그인</a>
    </div>
</div>

<!-- 자바스크립트 -->
<script>
    document.getElementById('signup-form').addEventListener('submit', function (event) {
        event.preventDefault(); // 폼 제출 기본 동작 방지

        // 입력 필드 값 가져오기
        const nickname = document.getElementById('nickname').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        // Validation 체크
        if (nickname.length < 1) {
            alert('닉네임을 1자 이상 입력하세요.');
            return;
        }

        if (!validateEmail(email)) {
            alert('올바른 이메일 형식을 입력하세요.');
            return;
        }

        if (!validatePassword(password)) {
            alert('비밀번호는 영문자와 숫자를 포함하며, 7자리 이상이어야 합니다.');
            return;
        }

        if (password !== confirmPassword) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            return;
        }

        // 회원가입 데이터
        const signupData = {
            nickname: nickname,
            email: email,
            password: password
        };

        // API로 POST 요청 보내기
        fetch('/api/members', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(signupData),
        })
            .then(response => {
                if (response.ok) {
                    return response.json(); // 응답이 JSON이면 파싱
                } else {
                    // 성공이 아닌 상태 코드 처리 (예: 400, 500 등)
                    throw new Error('서버 오류 발생: 상태 코드 ' + response.status);
                }
            })
            .then(data => {
                // 성공적인 응답 처리
                alert('회원가입이 성공적으로 완료되었습니다.');
                window.location.href = '/pages/login'; // 로그인 페이지로 이동
            })
            .catch(error => {
                // 에러 처리 (예: 상태 코드가 400, 500일 경우)
                alert('회원가입에 실패했습니다: ' + error.message);
            });
    });

    // 이메일 유효성 검사 함수
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(String(email).toLowerCase());
    }

    // 비밀번호 유효성 검사 함수
    function validatePassword(password) {
        // 영문자와 숫자를 포함하고, 최소 7자리 이상이어야 함
        const re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{7,}$/;
        return re.test(password);
    }
</script>

</body>
</html>
