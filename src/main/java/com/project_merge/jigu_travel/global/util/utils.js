
function fetchWithAuth(url, options = {}) {
    const jwtToken = localStorage.getItem("jwt");

    if (jwtToken) {
        options.headers = {
            ...options.headers,
            "Authorization": `Bearer ${jwtToken}`
        };
    }

    return fetch(url, options);
}
