document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    const loginId = document.getElementById("loginId").value;
    const password = document.getElementById("password").value;

    fetch("/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ loginId, password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("로그인 실패");
        }
        return response.json();
    })
    .then(responseData => { // BaseResponse 구조 적용
        console.log("로그인 성공:", responseData);

        if (responseData.code !== 200) {
            throw new Error(responseData.message); // 실패 시 예외 처리
        }

        localStorage.setItem("jwt", responseData.data.accessToken);

        console.log("저장된 JWT:", localStorage.getItem("jwt"));

        window.location.href = "/home";
    })
    .catch(error => {
        console.error("로그인 실패:", error);
        document.getElementById("errorMessage").style.display = "block";
    });
});
