<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${movie.title} - MoviePedia</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- CSS 파일 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<!-- 네비게이션 바 -->
<nav class="navbar">
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
        <a href="/pages/login">로그인</a>
    </div>
</nav>

<!-- 영화 상세 정보 섹션 -->
<section class="movie-detail">
    <div class="movie-header">
        <!-- 영화 정보, 예약율, 줄거리 배치 -->
        <div class="movie-info">
            <div class="movie-title">
                <h1>${movie.title}</h1>
                <p>장르: ${movie.genre}</p>
                <p>개봉일: ${movie.releaseDate}</p>
                <p>감독: ${movie.director}</p>
            </div>

            <!-- 예약율 섹션 -->
            <div class="movie-reservation">
                <h2>예매율</h2>
                <p>${movie.reservationRate}%</p>
                <div class="reservation-rate-bar">
                    <!-- style 속성으로 너비를 동적으로 설정 -->
                    <div class="reservation-rate-fill" style="width: ${movie.reservationRate}%;">
                        ${movie.reservationRate}%
                    </div>
                </div>
            </div>

            <!-- 줄거리 섹션 -->
            <div class="movie-description">
                <h2>줄거리</h2>
                <p>${movie.description}</p>
            </div>
        </div>

        <!-- 영화 포스터 배치 -->
        <div class="movie-poster">
            <img src="${movie.imageUrl}" alt="${movie.title} 포스터">
        </div>
    </div>
</section>

</body>
</html>
