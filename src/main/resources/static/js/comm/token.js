const USER_GRADE = {
    ROLE_USER: '1',
    ROLE_BLOCK: '2'
}

const getTokenPayload = (token=localStorage.Authorization) => {
    if(token == null) {
        return null;
    }
    if(token.indexOf("bearer ") != 0) {
        return null;
    }
    token = token.substring(7);

    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

const isLogin = (token=localStorage.Authorization) => {
    return token != null;
}

const isBlind = (token=localStorage.Authorization) => {
    if(!isLogin) {
        throw '로그인이 필요합니다.';
    }
    let payload = getTokenPayload();
    if(getTokenPayload().grade == null) {
        throw '정상적인 토큰이 아닙니다. 로그아웃이후 다시 시도하세요.';
    }
    return getTokenPayload().grade == USER_GRADE.ROLE_BLOCK;
}