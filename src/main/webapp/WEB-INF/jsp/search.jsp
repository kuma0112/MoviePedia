<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>검색 결과 - MoviePedia</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
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
        <input type="text" id="searchQuery" placeholder="콘텐츠, 인물, 컬렉션 검색">
        <button id="searchButton">검색</button>
    </div>
    <div class="user-options">
        <span id="user-nickname" style="display: none;"></span>
        <a href="/pages/register" id="register-link">회원가입</a>
        <a href="/pages/login" id="login-link">로그인</a>
        <a href="/pages/mypage" id="mypage-link" style="display: none;">마이페이지</a>
        <a href="#" id="logout-link" style="display: none;">로그아웃</a>
    </div>
</nav>

<section class="search-results">
    <h2>검색 결과</h2>
    <div id="searchResultsContainer">
        <!-- 검색 결과가 여기에 표시됩니다 -->
    </div>
</section>

<script>
    window.onload = function () {
        checkLoginStatus();
    }

    async function fetchSearchResults(query, page, size) {
        const searchResultsContainer = document.getElementById('searchResultsContainer');
        searchResultsContainer.innerHTML = '<p>검색 결과를 불러오는 중입니다...</p>';

        try {
            const response = await fetch(`/api/movies/searches?query=\${query}&page=\${page}&size=\${size}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            displaySearchResults(data.movieList);
        } catch (error) {
            searchResultsContainer.innerHTML = `<p>검색 결과를 불러오는 중 오류가 발생했습니다: ${error.message}</p>`;
        }
    }

    function displaySearchResults(movieList) {
        const searchResultsContainer = document.getElementById('searchResultsContainer');
        searchResultsContainer.innerHTML = '';
        const fragment = document.createDocumentFragment();

        if (movieList && movieList.length > 0) {
            movieList.forEach(movie => {
                const movieCard = document.createElement('div');
                movieCard.classList.add('movie-card');
                movieCard.innerHTML = `
                    <a href="/pages/movies/\${movie.movieId}">
                        <img src="\${movie.imageUrl}" alt="\${movie.title} 포스터">
                    </a>
                    <div class="title">\${movie.title}</div>
                    <div class="director">감독: \${movie.director}</div>
                    <div class="genre">장르: \${movie.genre}</div>
                `;
                fragment.appendChild(movieCard);
            });
        } else {
            searchResultsContainer.innerHTML = '<p>검색 결과가 없습니다.</p>';
        }
        searchResultsContainer.appendChild(fragment);
    }

    // URL 파라미터에서 검색어를 가져와 검색 결과를 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const query = urlParams.get('query');
    const page = urlParams.get('page') || 0;
    const size = urlParams.get('size') || 10;
    fetchSearchResults(query, page, size);

    function checkLoginStatus() {
        const token = localStorage.getItem('token');
        const nickname = localStorage.getItem('nickname');
        updateUI(nickname);
    }

    function updateUI(nickname) {
        const userNicknameElement = document.getElementById('user-nickname');
        const registerLink = document.getElementById('register-link');
        const loginLink = document.getElementById('login-link');
        const logoutLink = document.getElementById('logout-link');
        const mypageLink = document.getElementById('mypage-link');

        if (nickname) {
            userNicknameElement.textContent = nickname;
            userNicknameElement.style.display = 'inline';
            registerLink.style.display = 'none';
            loginLink.style.display = 'none';
            logoutLink.style.display = 'inline';
            mypageLink.style.display = 'inline-block'; // 마이페이지 버튼 표시
        } else {
            userNicknameElement.style.display = 'none';
            registerLink.style.display = 'inline';
            loginLink.style.display = 'inline';
            logoutLink.style.display = 'none';
            mypageLink.style.display = 'none'; // 마이페이지 버튼 숨김
        }
    }

    function logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('nickname');
        updateUI(null);
        window.location.href = '/';
    }

</script>

</body>
</html>
