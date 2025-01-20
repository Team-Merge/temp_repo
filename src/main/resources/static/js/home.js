document.addEventListener("DOMContentLoaded", function () {
    const jwtToken = localStorage.getItem("jwt");
    console.log("현재 저장된 JWT 토큰:", jwtToken);
    if (!jwtToken || jwtToken === "undefined") {
        console.log("JWT 토큰 없음. 로그인 필요.");
        return;
    }

    if (window.isFetchingUser) {
        return;
    }
    window.isFetchingUser = true;

    fetch("/api/user/me", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + jwtToken
        }
    })
    .then(response => response.json())
    .then(responseData => {
        console.log("응답 데이터:", responseData);

        if (responseData.code !== 200) {
            throw new Error(responseData.message);
        }

        // ✅ UserDto 데이터 활용
        const user = responseData.data;
        document.getElementById("username-display").innerText = `${user.nickname}님 안녕하세요!`;
        document.getElementById("login-section").style.display = "none";
        document.getElementById("logout-section").style.display = "block";
    })
    .catch(error => {
        console.error("사용자 정보 불러오기 실패:", error);
    })
    .finally(() => {
        window.isFetchingUser = false;
    });

    // 로그아웃 폼 제출 이벤트 처리
    document.getElementById("logoutForm").addEventListener("submit", function (event) {
        event.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

        console.log("현재 저장된 JWT 토큰2 : ", jwtToken);

        fetch('/api/auth/logout', {
            method: 'POST',
            headers: {
                "Authorization": "Bearer " + jwtToken
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("로그아웃 실패");
            }
            return response.json();
        })
        .then(data => {
            console.log('로그아웃 성공:', data);
            localStorage.removeItem("jwt");
            window.location.href = '/auth/login'; // 로그아웃 후 로그인 페이지로 리다이렉트
        })
        .catch(error => {
            console.error('로그아웃 오류:', error);
        });
    });
});