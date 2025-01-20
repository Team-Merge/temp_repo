document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("register-form");

    registerForm.addEventListener("submit", function (event) {
        event.preventDefault();  // 기본 폼 제출 동작 막기

        const formData = {
            loginId: document.getElementById("loginId").value,
            password: document.getElementById("password").value,
            nickname: document.getElementById("nickname").value,
            birthDate: document.getElementById("birthDate").value,
            gender: document.getElementById("gender").value,
            location: document.getElementById("location").value
        };

        console.log("회원가입 데이터:", formData);

        fetch("/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("회원가입 실패");
            }
            console.log("서버 응답:", response);
            return response.text();
        })
        .then(data => {
            document.getElementById("message").innerText = "회원가입 성공! 로그인하세요.";
            registerForm.reset();  // 폼 초기화
        })
        .catch(error => {
            document.getElementById("message").innerText = "회원가입 실패: " + error.message;
        });
    });
});
