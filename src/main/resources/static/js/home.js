//import { apiFetch } from "./utils/api.js";
//


document.addEventListener("DOMContentLoaded", async function () {

    async function apiFetch(url, options = {}) {
        let jwtToken = localStorage.getItem("jwt");

        options.headers = {
            ...options.headers,
            "Authorization": "Bearer " + jwtToken,
            "Content-Type": "application/json"
        };

        console.log(`apiFetch() 호출됨: ${url}`);
        console.log(`현재 JWT 토큰: ${jwtToken}`);

        try {
            console.log("fetch 요청 시작");
            let response = await fetch(url, options);
            console.log("fetch 요청 완료");

            console.log("응답 상태 코드:", response.status);

            if (response.status === 401) {
                console.log("Access Token 만료됨, Refresh Token으로 갱신 시도");

                console.log("Refresh Token 요청 시작");
                const refreshResponse = await fetch("/api/auth/refresh", {
                    method: "POST",
                    credentials: "include"
                });

                console.log("Refresh Token 요청 완료");
                console.log("Refresh 응답 상태 코드:", refreshResponse.status);

                if (refreshResponse.ok) {
                    const refreshData = await refreshResponse.json();
                    const newAccessToken = refreshData.data.accessToken;

                    console.log("새 Access Token 저장:", newAccessToken);
                    localStorage.setItem("jwt", newAccessToken);

                    options = {
                        ...options,
                        headers: {
                            ...options.headers,
                            "Authorization": "Bearer " + newAccessToken
                        }
                    };

                    console.log("최종 Authorization 헤더 값:", options.headers["Authorization"]);

                    // 갱신된 Access Token으로 원래 요청을 재시도
                    response = await fetch(url, options);
                }
            }

            return response;
        } catch (error) {
            console.error("apiFetch() 오류 발생:", error);
            throw error;
        }
    }

    let jwtToken = localStorage.getItem("jwt");
    console.log("현재 저장된 JWT 토큰:", jwtToken);

    if (!jwtToken || jwtToken === "undefined") {
        console.log("JWT 토큰 없음. 로그인 필요.");
        return;
    }

    if (window.isFetchingUser) {
        return;
    }
    window.isFetchingUser = true;

    try {
        console.log("사용자 정보 요청");
        const response = await apiFetch("/api/user/me");

        console.log("사용자 정보 응답 상태 코드:", response.status);
        const responseData = await response.json();
        console.log("응답 데이터:", responseData);

        if (responseData.code !== 200) {
            throw new Error(responseData.message);
        }

        const user = responseData.data;
        document.getElementById("username-display").innerText = `${user.nickname}님 안녕하세요!`;
        document.getElementById("login-section").style.display = "none";
        document.getElementById("logout-section").style.display = "block";
    } catch (error) {
        console.error("사용자 정보 불러오기 실패:", error);
    } finally {
        window.isFetchingUser = false;
    }

    document.getElementById("logoutForm").addEventListener("submit", async function (event) {
        event.preventDefault();

        console.log("현재 저장된 JWT 토큰: ", jwtToken);

        try {
            const response = await apiFetch('/api/auth/logout', {
                method: 'POST'
            });

            console.log("로그아웃 응답 상태 코드:", response.status); // ✅ 로그아웃 응답 코드 출력
            const responseData = await response.json();
            console.log('로그아웃 성공:', responseData);
            localStorage.removeItem("jwt");
            window.location.href = '/auth/login';
        } catch (error) {
            console.error('로그아웃 오류:', error);
        }
    });
});
