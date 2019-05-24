import { sha256 } from "js-sha256";

export default {
    authenticate() {
        return fetch('http://localhost:8085/musicvoting/api/auth/validate', {
            method: 'GET',
            credentials: 'include',
        })
            .then(res => {
                if (res.status == 200 && localStorage.getItem("isLoggedIn") != "true") {
                    localStorage.setItem("isLoggedIn", "true")
                }
                else if (res.status == 403) {
                    localStorage.setItem("isLoggedIn", "false")
                }
                return Promise.resolve(res)
            });
    },

    login(password: string) {
        // if he is already logged in don't log in again
        if (localStorage.getItem("isLoggedIn") == "true")
            return Promise.reject()

        return fetch('http://localhost:8085/musicvoting/api/auth', {
            method: 'POST',
            headers: {
                "Content-Type": "text/plain",
            },
            credentials: 'include',
            body: sha256(password)
        })
            .then(res => {
                if (res.status == 200) {
                    localStorage.setItem("isLoggedIn", "true")
                }
                return Promise.resolve(res)
            });
    }
}