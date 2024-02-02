const logout = document.getElementById("logout");

if(logout) {
    logout.addEventListener("click", event => {
        localStorage.removeItem("access_token");
        location.replace("/logout");
    });
}

const token = searchParam('token');

if(token) {
    localStorage.setItem("access_token", token);
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}
