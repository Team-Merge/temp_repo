export async function apiFetch(url, options = {}) {
    let jwtToken = localStorage.getItem("jwt");

    options.headers = {
        ...options.headers,
        "Authorization": "Bearer " + jwtToken,
        "Content-Type": "application/json"
    };

    console.log(`apiFetch() 호출됨: ${url}`);
    console.log(`현재 JWT 토큰: ${jwtToken}`);

    let response = await fetch(url, options);

    if (response.status === 401) {
        console.log("Access Token 만료됨, Refresh Token으로 갱신 시도");

        const refreshResponse = await fetch("/api/auth/refresh", {
            method: "POST",
            credentials: "include"
        });

        if (refreshResponse.ok) {
            const refreshData = await refreshResponse.json();
            const newAccessToken = refreshData.data.accessToken;
            localStorage.setItem("jwt", newAccessToken);
            options.headers["Authorization"] = "Bearer " + newAccessToken;
            response = await fetch(url, options);
        } else {
            console.error("Refresh Token이 만료됨. 다시 로그인 필요.");
            window.location.href = "/auth/login";
        }
    }

    return response.json();
}
