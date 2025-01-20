document.addEventListener("DOMContentLoaded", function () {
    const jwtToken = localStorage.getItem("jwt");

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
});
