const signUpBtn = document.getElementById("sign_up_btn");
const oauth2SignUpBtn = document.getElementById("oauth2_sign_up_btn");

if (signUpBtn) {
    signUpBtn.addEventListener("click", () => {
        let body = JSON.stringify({
            username: document.getElementById("username").value,
            password: document.getElementById("password").value,
            nickname: document.getElementById("nickname").value,
            email: document.getElementById("email").value,
        });

        signUpRequest("sign-up", body);
    });
}

if (oauth2SignUpBtn) {
    oauth2SignUpBtn.addEventListener("click", () => {
        let body = JSON.stringify({
            nickname: document.getElementById("nickname").value,
            email: document.getElementById("email").value,
            provider: document.getElementById("provider").value,
        });

        signUpRequest("/oauth2/sign-up", body);
    });
}

function signUpRequest(url, body) {
    fetch(url, {
        method: "POST",
        headers: {
            "Content-type": "application/json",
        },
        body: body
    })
        .then(response => {
            if (response.ok) {
                alert("회원가입을 축하드립니다.");

                return response.json();
            }
        })
        .then(result => {
            let params = new URLSearchParams(result);
            let url = "/login?" + params.toString();

            fetch(url, {
                method: "POST",
            })
                .then(res => {
                    if(res.ok) {
                        location.replace("/");
                    }
                });
        });
}