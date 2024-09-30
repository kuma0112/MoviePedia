<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이 페이지</title>
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
        <span id="user-nickname" style="display: none;"></span>
        <a href="/pages/login" id="login-link">로그인</a>
        <a href="#" id="logout-link" style="display: none;">로그아웃</a>
    </div>
</nav>

<!-- 마이 페이지 메인 섹션 -->
<div class="mypage-container">
    <h2>${myPage.nickname}님의 영화 보관소</h2>

    <!-- 내가 좋아요한 영화들 -->
    <div class="movies">
        <div class="mypage-movie-card">
            <!-- 큰 버튼을 만들어서 누르면 mymovie.jsp로 가도록 -->
            <a href="/pages/mymovie">
                <button>내가 좋아요한 영화들 보러 가기</button>
            </a>
        </div>
    </div>

    <!-- 나의 리뷰 -->
    <div class="mypage-section-title">지금까지 어떤 영화에 리뷰를 남기셨나요?</div>
    <div class="reviews">
        <!-- 리뷰 카드들이 여기에 동적으로 추가됩니다 -->
    </div>
</div>

<script>
    // 마이 페이지 데이터를 받아오는 함수
    async function getMyPageData() {
        try {
            const token = localStorage.getItem("token");

            const response = await fetch('/api/members/mypage', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token
                }
            });

            if (response.ok) {
                const myPageData = await response.json();
                renderMyPage(myPageData);
            } else {
                console.error('Failed to fetch my page data');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    // 받아온 데이터를 HTML에 반영하는 함수
    function renderMyPage(data) {
        const nicknameElement = document.querySelector('h2');
        nicknameElement.textContent = `\${data.nickname}님의 영화 보관소`;

        // 리뷰 데이터를 받아와서 렌더링
        renderReviews(data.reviewList);
    }

    // 리뷰 목록을 동적으로 추가하는 함수
    function renderReviews(reviews) {
        const reviewContainer = document.querySelector('.reviews');
        reviewContainer.innerHTML = ''; // 이전에 렌더링된 리뷰를 지우기 위해 초기화

        reviews.forEach(review => {
            const reviewCard = document.createElement('div');
            reviewCard.classList.add('mypage-review-card');
            reviewCard.innerHTML = `
                <h4>영화 제목: \${review.movieTitle}</h4>
                <p>리뷰 내용: \${review.content}</p>
                <span class="review-author">작성자: \${review.nickname}</span>
                <div class="review-actions">
                    <button class="mypage-modify-button" data-review-id="\${review.reviewId}" data-movie-id="\${review.movieId}">수정</button>
                    <button class="mypage-delete-button" data-review-id="\${review.reviewId}" data-movie-id="\${review.movieId}">삭제</button>
                </div>
            `;
            reviewContainer.appendChild(reviewCard);
        });
    }

    // 페이지가 로드될 때 getMyPageData 함수를 호출해서 데이터를 불러옴
    document.addEventListener('DOMContentLoaded', () => {
        getMyPageData();
    });


    document.addEventListener("DOMContentLoaded", function () {
        checkLoginStatus();

        // 로그아웃 기능 추가
        document.getElementById('logout-link').addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });

        function checkLoginStatus() {
            const token = localStorage.getItem('token');
            const nickname = localStorage.getItem('nickname');
            updateUI(nickname);
        }

        function updateUI(nickname) {
            const userNicknameElement = document.getElementById('user-nickname');
            const loginLink = document.getElementById('login-link');
            const logoutLink = document.getElementById('logout-link');

            if (nickname) {
                userNicknameElement.textContent = nickname;
                userNicknameElement.style.display = 'inline';
                loginLink.style.display = 'none';
                logoutLink.style.display = 'inline';
            } else {
                userNicknameElement.style.display = 'none';
                loginLink.style.display = 'inline';
                logoutLink.style.display = 'none';
            }
        }

        function logout() {
            localStorage.removeItem('token');
            localStorage.removeItem('nickname');
            updateUI(null);
            window.location.href = '/';
        }
    });
</script>

</body>
</html>
