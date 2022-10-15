//fetch mehotd 선언
const METHOD_TYPE = {
    GET : "GET",
    POST : "POST",
    PUT : "PUT",
    DELETE : "DELETE"
};

// FormData to serialize
let loading = false;
const mapleFetch = (url, method, header, body, then) => {
    if(loading) {
        console.log("작업이 진행중입니다.");
        return;
    }
    if(url === null || method === null) {
        return;
    }
    if(header === null) {
        header = {};
    }
    if(body === null) {
        body = {};
    }

    const fetchConfig = {
        method: method,
        headers: header,
        body: body
    }

    fetch(url, fetchConfig)
        .then(then)
        .finally(() => loading = false);
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

        if (rtnData[key] === undefined) {
            rtnData[key] = [];
        }

        rtnData[key].push(value);
    }

    return rtnData;
}