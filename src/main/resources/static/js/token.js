function searchPram(key) {
    return new URLSearchParams(location.search).get(key);
}

const token = searchPram('token');


if (token) {
    localStorage.setItem("access_token", token);
}