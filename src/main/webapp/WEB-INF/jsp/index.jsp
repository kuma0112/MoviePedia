<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MoviePedia</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
        }

        /* 네비게이션 바 스타일 */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: white;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .navbar .logo {
            font-size: 28px;
            font-weight: bold;
            color: #FF0050;
        }

        .navbar .search-bar {
            display: flex;
            align-items: center;
            flex-grow: 1;
            justify-content: flex-end;
            gap: 10px;
        }

        .search-bar input[type="text"] {
            padding: 10px;
            width: 200px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .search-bar button {
            background-color: #FF0050;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }

        .navbar .user-options {
            display: flex;
        }

        .navbar .user-options a {
            text-decoration: none;
            color: black;
            font-size: 16px;
            padding: 8px 16px;
            border-radius: 4px;
            background-color: white;
            border: 1px solid white;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        .navbar .user-options a:hover {
            background-color: #f0f0f0;
            color: black;
        }

        /* 박스오피스 순위 스타일 */
        .box-office {
            padding: 50px;
            text-align: left;
        }

        .box-office h2 {
            margin-bottom: 20px;
            font-size: 23px; /* 글자 크기 줄임 */
            font-weight: bold;
            margin-left: 20px;
        }

        .box-office-grid {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 5px;
            justify-items: center;
        }

        .movie-card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 10px;
            width: 250px; /* 카드 크기 */
            text-align: center;
        }

        .movie-card img {
            width: 100%;
            border-radius: 8px;
        }

        .movie-card .rank {
            font-size: 20px;
            font-weight: bold;
            color: #FF0050;
            margin-top: 10px;
        }

        .movie-card .title {
            font-size: 16px;
            margin: 10px 0;
        }

        /* 개봉 예정작 스타일 */
        .upcoming-section {
            padding: 50px;
            text-align: left;
        }

        .upcoming-section h2 {
            margin-bottom: 20px;
            font-size: 23px; /* 글자 크기 줄임 */
            font-weight: bold;
            margin-left: 20px;
        }

        .upcoming-grid {
            display: grid;
            grid-template-columns: repeat(5, 1fr);
            gap: 5px;
            justify-items: center;
        }

        .upcoming-card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            padding: 10px;
            width: 250px;
            text-align: center;
        }

        .upcoming-card img {
            width: 100%;
            border-radius: 8px;
        }

        .upcoming-card .release-date {
            font-size: 14px;
            color: #FF0050;
            margin-top: 10px;
        }

        .upcoming-card .title {
            font-size: 16px;
            margin: 10px 0;
        }
    </style>
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
        <a href="signup.html">회원가입</a>
        <a href="login.html">로그인</a>
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
            console.log("foreach 후" + JSON.stringify(movie.title) + index)
            const movieCard = document.createElement('div');
            movieCard.classList.add('movie-card');

            movieCard.innerHTML = `
            <img src="\${movie.imageUrl}" alt="\${movie.title} 포스터">
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
