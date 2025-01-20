document.addEventListener("DOMContentLoaded", function () {
    const jwtToken = localStorage.getItem("jwt");  // 로컬스토리지에서 JWT 토큰을 가져옴

    if (!jwtToken || jwtToken === "undefined") {
        console.log("JWT 토큰 없음. 로그인 필요.");
        return;
    }

    fetch("/user-info", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + jwtToken  // Authorization 헤더에 JWT 토큰을 포함시킴
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log("응답 데이터:", data);
        document.getElementById("username-display").innerText = `Logged in as: ${data.username}`;
        document.getElementById("login-section").style.display = "none";
        document.getElementById("logout-section").style.display = "block";
    })
    .catch(error => {
        console.error("사용자 정보 불러오기 실패:", error);
    });
});