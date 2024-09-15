<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MoviePedia</title>
    <!-- Google Fonts 링크는 여기에 위치 -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- CSS 파일 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- 네비게이션 바 -->
<nav class="navbar">
    <div class="logo">MoviePedia</div>
    <div class="search-bar">
        <input type="text" placeholder="콘텐츠, 인물, 컬렉션 검색">
        <button>검색</button>
    </div>
    <div class="user-options">
        <a href="/pages/register">회원가입</a>
        <a href="/pages/login">로그인</a>
    </div>
</nav>

<!-- 박스오피스 순위 -->
<section class="box-office">
    <h2>박스오피스 순위</h2>
    <div class="box-office-grid" id="movieListContainer">
        <!-- 영화 카드들이 여기에 동적으로 추가됩니다 -->
    </div>
</section>

<!-- 개봉 예정작 섹션 -->
<section class="upcoming-section">
    <h2>공개 예정작</h2>
    <div class="upcoming-grid">
        <div class="upcoming-card">
            <img src="https://via.placeholder.com/150x200" alt="Upcoming Movie 1">
            <div class="release-date">2024.09.14</div>
            <div class="title">영화 제목 1</div>
        </div>
    </div>
</section>
<script>
    let LIST_ROW_COUNT = 10; // 한 페이지에 10개 로우
    let OFFSET = 0;

    window.onload = function () {
        listMovie();
    }

    async function listMovie() {
        const movieListContainer = document.getElementById('movieListContainer');
        movieListContainer.innerHTML = '<p>영화 데이터를 불러오는 중입니다...</p>';

        try {
            let fetchOptions = {
                headers: {
                    'ajax': 'true'
                }
            };

            let url = "/api/movies/rankings";
            let urlParams = "?page=" + OFFSET + "&size=" + LIST_ROW_COUNT;
            let response = await fetch(url + urlParams, fetchOptions);

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            let data = await response.json();
            console.log("Received data:", data); // 데이터 로그 출력

            if (data.movieList && data.movieList.length > 0) {
                makeListHtml(data.movieList);
            } else {
                movieListContainer.innerHTML = '<p>표시할 영화 데이터가 없습니다.</p>';
            }
        } catch (error) {
            console.error("Error fetching movie data:", error);
            movieListContainer.innerHTML = `<p>영화 데이터를 불러오는 중 오류가 발생했습니다: ${error.message}</p>`;
        }
    }

    function makeListHtml(movieList) {
        movieListContainer.innerHTML = '';  // 먼저 기존 내용을 지우고
        const fragment = document.createDocumentFragment();  // Fragment 사용
        movieList.forEach((movie, index) => {
            console.log("Movie ID: ", movie.movieId);
            const movieCard = document.createElement('div');
            movieCard.classList.add('movie-card');

            movieCard.innerHTML = `
            <a href="/pages/movies/\${movie.movieId}">
                <img src="\${movie.imageUrl}" alt="\${movie.title} 포스터">
            </a>
            <div class="rank">\${index + 1}</div>
            <div class="title">\${movie.title}</div>
            <div class="reservationRate">예매율: \${movie.reservationRate}%</div>
        `;
            fragment.appendChild(movieCard);  // Fragment에 추가
        });
        movieListContainer.appendChild(fragment);  // 마지막에 한 번에 추가
    }
</script>

</body>
</html>
