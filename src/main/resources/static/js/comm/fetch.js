//fetch mehotd 선언
const METHOD_TYPE = {
    GET : "GET",
    POST : "POST",
    PUT : "PUT",
    DELETE : "DELETE"
};

// FormData to serialize
// 인증이 필요하지않은 경우
let loading = false;
const mapleFetch = (url, method, headers, body, function1) => {
    if(loading) {
        console.log("작업이 진행중입니다.");
        return;
    }
    loading = true;
    let fetchConfig = {};

    if(url == null || method == null) {
        return;
    }
    fetchConfig.method = method;

    if(headers != null) {
        fetchConfig.headers = headers;
    }

    let authorization = localStorage.getItem("Authorization")
    if(authorization != null) {
        fetchConfig.headers.authorization = authorization;
    }

    if(body != null) {
        fetchConfig.body = body;
    }

    fetch(url, fetchConfig)
        .then(function1)
        .finally(() => loading = false);
}

const mapleFetchAsync = async (url, method, headers, body) => {

    if(url == null || method == null) {
        return;
    }

    let fetchConfig = {};

    fetchConfig.method = method;
    if(headers != null) {
        fetchConfig.headers = headers;
    }

    let authorization = localStorage.getItem("Authorization")
    if(authorization != null) {
        fetchConfig.headers.authorization = authorization;
    }

    if(body != null) {
        fetchConfig.body = body;
    }

    const response = await fetch(url, fetchConfig);
    if (response.status == 401 && response.headers.get("X-Token") == 'true') {
        await reissue();
        fetchConfig.headers.authorization = localStorage.getItem("Authorization");
        return await fetch(url, fetchConfig);
    }
    return response;
}

//form id를 입력받아 폼데이터를 json으로 반환
const getFormDataJson = (id) => {
    const frmRegister = document.getElementById(id);
    const frmRegisterData = new FormData(frmRegister);
    return serialize(frmRegisterData);
}

const serialize = (rawData) => {
    let rtnData = {};
    for (let [key, value] of rawData) {
        let sel = document.querySelectorAll("[name=" + key + "]");

        // Array Values
        if (sel.length <= 1) {
            rtnData[key] = value;
            continue;
        }

        if (rtnData[key] == undefined) {
            rtnData[key] = [];
        }

        rtnData[key].push(value);
    }

    return rtnData;
}

/*
    accessToken과 refreshToken이 존재여부 확인.

    localStorage가 존재하면서 refreshToken이 존재하지 않는경우
    1. 브라우저가 종료되었다가 다시 생성된경우
    2. accessToken 탈취
    >> 로그아웃 처리
 */
const effectiveToken = async () => {
    if (localStorage.getItem("Authorization") != null) {
        let fetchConfig = {};

        let url = "/effectiveToken";
        fetchConfig.method = METHOD_TYPE.GET;
        fetchConfig.headers = {
            authorization: localStorage.getItem("Authorization")
        };

        const response = await fetch(url, fetchConfig);
        if (response.status == 200) {
            return;
        }

        if (response.status == 401 && response.headers.get("X-Token") == 'true') {
            await reissue();
            fetchConfig.headers.authorization = localStorage.getItem("Authorization");
            const response2 = await fetch(url, fetchConfig);
            if (response2.status == 200) {
                return;
            }
        }

        localStorage.removeItem("Authorization");
        alert("로그아웃 되었습니다.");
        location.reload();
    }
}

const reissue = async () => {
    if (localStorage.getItem("Authorization") != null) {
        let fetchConfig = {};

        let url = "/reissue";
        fetchConfig.method = METHOD_TYPE.GET;
        fetchConfig.headers = {
            authorization: localStorage.getItem("Authorization")
        };

        const response = await fetch(url, fetchConfig);
        if (response.headers.get("Authorization") == null) {
            alert("token 재발급에 실패했습니다.");
            throw 'token reissue error';
            return;
        }
        localStorage.setItem("Authorization", response.headers.get("Authorization"));
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    await effectiveToken();
});
