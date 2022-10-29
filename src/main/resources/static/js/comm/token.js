const USER_GRADE = {
    ROLE_USER: '1',
    ROLE_BLOCK: '2'
}

const getTokenPayload = () => {
    const base64Url = parseCookie()._inf;
    if(base64Url == null) {
        return null;
    }

    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

const isLogin = (token=document.cookie.indexOf('_inf=')) => {
    return token >= 0;
}

const isBlock = (token=document.cookie.indexOf('_inf=')) => {
    if(!isLogin(token)) {
        throw '로그인이 필요합니다.';
    }
    if(getTokenPayload().grade == null) {
        throw '정상적인 토큰이 아닙니다. 로그아웃이후 다시 시도하세요.';
    }
    return getTokenPayload().grade == USER_GRADE.ROLE_BLOCK;
}

const parseCookie = () =>
    document.cookie
        .split(';')
        .map(v => v.split('='))
        .reduce((acc, v) => {
            acc[decodeURIComponent(v[0].trim())] = decodeURIComponent(v[1].trim());
            return acc;
        }, {});