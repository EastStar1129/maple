//method type
const METHOD_TYPE = {
    GET : "GET",
    POST : "POST",
    PUT : "PUT",
    DELETE : "DELETE"
};

//response api
const RESPONSE_KEY = {
    CODE: 'code',
    MESSAGE: 'message',
    DATA: 'data'
};

//code type
const CODE_TYPE = {
    SUCCESS: 'SUCCESS',
    FAIL: 'FAIL'
}

//response class
ResponseData = function(data) {
    this.data = data;

    this.getMessage = function() {
        return data[RESPONSE_KEY.MESSAGE];
    }

    this.getCode = function() {
        return data[RESPONSE_KEY.CODE];
    }

    this.isSuccess = function() {
        return data[RESPONSE_KEY.CODE] == CODE_TYPE.SUCCESS;
    }

    this.isFail = function() {
        return data[RESPONSE_KEY.CODE] == CODE_TYPE.FAIL;
    }

    this.getData = function() {
        return data[RESPONSE_KEY.DATA];
    }
}

// FormData to serialize
const mapleFetch = (url, method, headers, body, function1) => {
    let fetchConfig = getRequestConfig(url, method, headers, body);

    fetch(url, fetchConfig)
        .then(function1);
}

const mapleFetchAsync = async (url, method, headers, body) => {
    let fetchConfig = getRequestConfig(url, method, headers, body);

    const response = await fetch(url, fetchConfig);
    const data = await response.json();
    return new ResponseData(data);
}

const getRequestConfig = (url, method, headers, body) => {
    if(url == null || method == null) {
        throw ("url or method is null");
        return;
    }

    return {
        method: method,
        headers: headers,
        body: body
    };
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
    let url = "/effectiveToken";
    let fetchConfig = {
        method: METHOD_TYPE.GET
    };
    fetch(url, fetchConfig)
        .then((response) => {
            if(response.status !== 200)
                console.log(response.status);
        });
}

const getURILastSegment = () => {
    const parts = window.location.pathname.split('/');
    return parts.pop() || parts.pop();  // handle potential trailing slash
}

const threeComma = (str) => {
    str += '';
    return str.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

document.addEventListener("DOMContentLoaded", async () => {
    await effectiveToken();
});
