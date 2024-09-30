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

            <!-- 리뷰 쓰기 버튼 -->
            <div class="review-actions">
                <button id="write-review-btn" class="write-review-btn">리뷰 쓰기</button>
                <button id="like-btn" class="like-btn">
                    <span id="heart-icon">&#10084;</span> 좋아요
                </button>
            </div>
        </div>

        <!-- 영화 포스터 배치 -->
        <div class="movie-poster">
            <img src="${movie.imageUrl}" alt="${movie.title} 포스터">
        </div>
    </div>
</section>

<section class="movie-reviews">
    <h2>리뷰</h2>
    <div id="review-cards-container" class="review-cards-container">
        <!-- 리뷰 카드가 여기에 동적으로 추가될 예정 -->
    </div>
    <!-- 더보기 버튼 -->
    <button id="load-more" class="load-more-btn">더보기</button>
</section>

<!-- 리뷰 작성 모달 -->
<div id="review-modal" class="modal" style="display: none;">
    <div class="modal-content">
        <span id="close-modal" class="close">&times;</span>
        <h2>리뷰 작성</h2>
        <form id="review-form">
            <textarea id="review-content" rows="4" placeholder="리뷰를 작성하세요"></textarea>
            <button type="submit">리뷰 제출</button>
        </form>
    </div>
</div>

<script>
    window.onload = function () {
        checkLikeState();

        document.getElementById('searchButton').addEventListener('click', function () {
            const query = document.getElementById('searchQuery').value;
            const page = 0;  // 첫 페이지로 설정
            const size = 10;  // 한 페이지에 10개의 결과 표시
            if (query) {
                // 검색 페이지로 이동
                window.location.href = `/pages/search?query=\${query}&page=\${page}&size=\${size}`;
            } else {
                alert('검색어를 입력해주세요.');
            }
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        const reviewContainer = document.getElementById("review-cards-container");
        const loadMoreBtn = document.getElementById("load-more");
        let currentPage = 0; // 페이지 번호
        const reviewsPerPage = 10; // 한 번에 가져올 리뷰 개수
        const movieId = ${movie.movieId}; // movieId 변수 추가

        // 리뷰 가져오는 함수
        async function fetchReviews(reset = false) {
            if (reset) {
                currentPage = 0;
                reviewContainer.innerHTML = '';
            }

            currentPage++;
            const response = await fetch(`/api/movies/${movieId}/reviews?page=${currentPage}&size=${reviewsPerPage}`);
            const reviewsData = await response.json();
            const reviews = reviewsData.reviewList;

            // 리뷰를 화면에 추가
            reviews.forEach(review => {
                const reviewCard = `
                    <div class="review-card">
                        <div class="review-header">
                            <h3>\${review.nickname}</h3>
                        </div>
                        <div class="review-body">
                            <p>\${review.content}</p>
                        </div>
                    </div>
                `;
                reviewContainer.innerHTML += reviewCard;
            });

            // 리뷰가 모두 로드되었으면 더보기 버튼 숨기기
            if ((currentPage * reviewsPerPage) >= reviewsData.totalElements) {
                loadMoreBtn.style.display = "none";
            } else {
                loadMoreBtn.style.display = "block";
            }
        }

        // 페이지 로드 시 첫 번째 리뷰 세트를 가져옴
        fetchReviews();

        // 더보기 버튼 클릭 시 추가 리뷰 가져오기
        loadMoreBtn.addEventListener("click", function () {
            fetchReviews();
        });

        // 리뷰 쓰기 버튼 클릭 시 모달 열기
        document.getElementById("write-review-btn").addEventListener("click", function () {
            document.getElementById("review-modal").style.display = "flex";
        });

        // 모달 닫기 버튼 클릭 시 모달 닫기
        document.getElementById("close-modal").addEventListener("click", function () {
            document.getElementById("review-modal").style.display = "none";
        });

        // 리뷰 제출 시 서버에 요청을 보내고 리스트에 추가
        document.getElementById("review-form").addEventListener("submit", async function (event) {
            event.preventDefault(); // 폼 제출 방지

            const reviewContent = document.getElementById("review-content").value;
            if (!reviewContent.trim()) return alert("리뷰 내용을 입력하세요.");

            const token = localStorage.getItem("token");

            const response = await fetch(`/api/movies/${movieId}/reviews`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
                body: JSON.stringify({
                    content: reviewContent
                })
            });

            if (response.ok) {
                await fetchReviews(true);  // 기존 리뷰 리스트를 다시 로드

                // 모달 창 닫기 및 폼 초기화
                document.getElementById("review-modal").style.display = "none";
                document.getElementById("review-content").value = ''; // 폼 초기화
            } else {
                alert("리뷰 작성에 실패했습니다.");
            }
        });

        // 모달 열기 시 폼 초기화
        document.getElementById("write-review-btn").addEventListener("click", function () {
            document.getElementById("review-modal").style.display = "flex";
            document.getElementById("review-content").value = ''; // 모달 열 때 입력값 초기화
        });
    });

    // 좋아요 기능
    document.addEventListener("DOMContentLoaded", function() {
        const likeBtn = document.getElementById("like-btn");
        const movieId = ${movie.movieId}; // movieId 변수 추가
        const token = localStorage.getItem("token");

        // 좋아요 버튼 클릭 이벤트
        likeBtn.addEventListener("click", async function() {
            if (!token) {
                alert("로그인이 필요합니다.");
                return;
            }

            const response = await fetch(`/api/movies/${movieId}/likes`, {
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                },
            });

            if (response.ok) {
                const result = await response.text(); // JSON 대신 텍스트로 처리

                if (result === "좋아요가 등록되었습니다.") {
                    console.log("좋아요 등록됨");
                    likeBtn.classList.add("liked"); // 좋아요 상태로 변경
                } else if (result === "좋아요가 취소되었습니다.") {
                    console.log("좋아요 취소됨");
                    likeBtn.classList.remove("liked"); // 좋아요 취소 상태로 변경
                }
            } else {
                alert("좋아요 처리 중 오류가 발생했습니다.");
            }
        });
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
                mypageLink.style.display = 'inline-block';
            } else {
                userNicknameElement.style.display = 'none';
                registerLink.style.display = 'inline';
                loginLink.style.display = 'inline';
                logoutLink.style.display = 'none';
                mypageLink.style.display = 'none';
            }
        }

        function logout() {
            localStorage.removeItem('token');
            localStorage.removeItem('nickname');
            updateUI(null);
            window.location.href = '/';
        }
    });

    // 좋아요 상태를 확인하는 함수
    async function checkLikeState() {
        const movieId = ${movie.movieId}; // movieId 변수 사용
        const token = localStorage.getItem("token");

        if (!token) {
            console.log("로그인이 필요합니다.");
            return;
        }

        // 좋아요 상태를 확인하는 API 호출
        const response = await fetch(`/api/movies/\${movieId}/likes`, {
            method: 'GET',
            headers: {
                'Authorization': token,
            }
        });

        if (response.ok) {
            const result = await response.text();

            const likeBtn = document.getElementById("like-btn");

            // 좋아요 상태에 따라 버튼 스타일을 변경
            if (result === "좋아요한 영화입니다.") {
                likeBtn.classList.add("liked");  // 좋아요 상태로 변경
            } else if (result === "좋아요하지 않은 영화입니다.") {
                likeBtn.classList.remove("liked");  // 기본 상태로 유지
            }
        } else {
            console.error("좋아요 상태를 확인하는 중 오류 발생");
        }
    }


</script>

</body>
</html>
