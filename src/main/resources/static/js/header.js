const logoutBtn = document.getElementById("logout-btn");

if(logoutBtn) {
    logoutBtn.addEventListener("click", () => {
        localStorage.removeItem("access_token");

        fetch("/logout", {
            method: "GET",
            headers: {
                "Content-type": "application/json",
            }
        })
            .then(response => {
                if(response.ok) {
                    let headers = response.headers;
                    location.replace(headers.get("Location"));
                }
            })
    });
}