<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>로그인</title>
    <link rel="shortcut icon" href="/resources/favicon.ico"/>
    <script type="text/javascript" src="/resources/js/comm/fetch.js"></script>
</head>
<body>

<div class="container">
    <form id="frmRegister">
        <div>
            <h1>login</h1>
        </div>
        <div>
            <input type="text" id="name" name="name" placeholder="대표 캐릭터명" maxlength="10">
        </div>
        <div>
            <input type="password" id="password" name="password" placeholder="패스워드" maxlength="20"><br>
        </div>
        <button type="button" id="btnLogin">login</button>
    </form>
</div>

</body>
<script>
    const fncLogin = () => {
        const serializedFrmRegisterData = getFormDataJson("frmRegister");

        if(!validationName(serializedFrmRegisterData.name)) {
            return;
        }

        if(!validationPassword(serializedFrmRegisterData.password)) {
            return;
        }

        const url = "/login";
        const method = METHOD_TYPE.POST;
        const header = {
            "Content-Type": "application/json",
        };

        const body = JSON.stringify(serializedFrmRegisterData);

        mapleFetch(url, method, header, body,
            (response) => {
                if (response.status == 200) {
                    location.href = '/';
                    return;
                }
                alert('회원정보를 확인하세요.');
            }
        );
    }

    const validationName = (to) => {
        if(to.length == 0) {
            alert('아이디를 입력하세요.');
            return false;
        }

        return true;
    }

    const validationPassword = (to) => {
        if(to.length == 0) {
            alert('패스워드를 입력하세요.');
            return false;
        }

        if(to.length >= 20) {
            alert('20자리 미만 패스워드를 입력하세요.');
            return false;
        }
        if(to.length < 8) {
            alert('8자리 이상 패스워드를 입력하세요.');
            return false;
        }

        return true;
    }

    document.getElementById('btnLogin').addEventListener('click', fncLogin, false);
</script>
</html>