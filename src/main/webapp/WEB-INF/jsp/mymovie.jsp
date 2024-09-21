<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내가 좋아요한 영화들</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <!-- CSS 파일 링크 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" href="data:;base64,=">
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
        <a href="/pages/login">로그인</a>
    </div>
</nav>

<!-- 내가 좋아요한 영화들 메인 섹션 -->
<div class="mypage-container">
    <h2>좋아요한 영화들</h2>

    <div class="mymovie-movies" id="mymovie-movieListContainer">
        <!-- 영화 카드들이 여기에 동적으로 추가됩니다 -->
    </div>
</div>

<script>

    // 서버에서 내가 좋아요한 영화 리스트 데이터를 받아오는 함수
    async function getLikedMovies() {
        try {
            const token = localStorage.getItem("jwtToken");

            const response = await fetch('/api/members/mymovie', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token
                }
            });

            if (response.ok) {
                const movieList = await response.json();  // List<MyMovieDto>를 받음
                makeListHtml(movieList);
            } else {
                console.error('Failed to fetch liked movies');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    // 받아온 영화 리스트를 HTML에 동적으로 추가하는 함수
    function makeListHtml(movieList) {
        const movieListContainer = document.getElementById('mymovie-movieListContainer');
        movieListContainer.innerHTML = '';  // 먼저 기존 내용을 지우고
        const fragment = document.createDocumentFragment();  // Fragment 사용
        movieList.forEach((movie, index) => {
            console.log("Movie ID: ", movie.movieId);
            const movieCard = document.createElement('div');
            movieCard.classList.add('mymovie-movie-card');

            movieCard.innerHTML = `
            <a href="/pages/movies/\${movie.movieId}">
                <img src="\${movie.imageUrl}" alt="\${movie.title} 포스터">
            </a>
            <div class="mymovie-rank">\${index + 1}</div>
            <div class="mymovie-title">\${movie.title}</div>
            <div class="mymovie-releaseDate">개봉일: \${new Date(movie.releaseDate).toLocaleDateString()}</div>
            `;
            fragment.appendChild(movieCard);  // Fragment에 추가
        });
        movieListContainer.appendChild(fragment);  // 마지막에 한 번에 추가
    }

    // 페이지가 로드될 때 getLikedMovies 함수를 호출해서 데이터를 불러옴
    document.addEventListener('DOMContentLoaded', () => {
        getLikedMovies();
    });
</script>

</body>
</html>
