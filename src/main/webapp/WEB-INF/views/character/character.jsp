<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>로그인</title>
    <script type="text/javascript" src="/resources/js/comm/fetch.js"></script>
</head>
<body>

<div class="container">
    <form id="frmRegister">
        <div>
            <h1>login</h1>
        </div>
        <div>
            <input type="text" id="name" name="name" placeholder="대표 캐릭터명" maxlength="20">
        </div>
        <div>
            <input type="password" id="password" name="password" placeholder="패스워드" maxlength="20"><br>
        </div>
        <button type="button" id="btnLogin">login</button>
    </form>
</div>

</body>
<script>
    const selectUserInfo = () => {
        let parts = window.location.pathname.split('/');
        let lastSegment = parts.pop() || parts.pop();  // handle potential trailing slash
        if(lastSegment === null || lastSegment === 'character') {
            return;
        }

        const url = "/" + lastSegment + "/character";
        const method = METHOD_TYPE.GET;
        const header = {
            "Content-Type": "application/json",
        };

        /**
         TODO console.log에 출력된 내용 활용
         화면 구성
         * **/

        mapleFetch(url, method, header, null,
            (response) => response.json().then((value) => {
                console.log(value);
            })
        );
    }
    selectUserInfo();
    document.getElementById('btnLogin').addEventListener('click', selectUserInfo, false);
</script>
</html>