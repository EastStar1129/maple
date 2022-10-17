<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>회원가입</title>
    <script type="text/javascript" src="/resources/js/comm/fetch.js"></script>
</head>
<body>

<div class="container">
    <c:forEach var="item" items="${terms}" step="1">
        <c:if test="${item.requiredTerms eq 'N'} ">(필수)</c:if>
        <h5>${item.name}
            <c:choose>
                <c:when test="${item.requiredTerms eq 'Y'}">(필수)</c:when>
                <c:otherwise>(선택)</c:otherwise>
            </c:choose>
        </h5>
        <textarea>${item.content}</textarea>
        <div>
            <input type="checkbox" name="checkbox" code="${item.code}" types="${item.type}" required="${item.requiredTerms}">
            <span>동의합니다.</span>
        </div>
    </c:forEach>

    <form id="frmRegister">
        <div>
            <input type="text" id="name" name="name" placeholder="대표 캐릭터명" maxlength="20">
        </div>
        <div>
            <input type="password" id="password" name="password" placeholder="패스워드" maxlength="20"><br>
        </div>
        <div>
            <input type="text" id="otpNumber" name="otpNumber" placeholder="OTP 번호" readonly>

        </div>
        <div>
            <button type="button" id="btnCreateOtp">OTP 발급</button>
            <a href="https://maplestory.nexon.com/Community/Free/326252" target="_blank">메이플 게시판</a>
        </div>
        <div>
            <input type="number" id="pageNumber" name="pageNumber" min="1" placeholder="페이지 번호">
        </div>
        <button type="button" id="btnRegist">regist</button>
    </form>
    <div>
        <p>ID : 메이플스토리 대표 캐릭터 명</p>
        <p>PASSWORD : 로그인으로 사용할 패스워드 (메이플스토리 홈페이지와 상관없음)</p>
        <p>OTP 번호 : 메이플스토리 대표 캐릭터가 입력한 캐릭터 명과 일치하도록 설정 후 댓글에 OTP 번호를 입력하세요.</p>
        <p>페이지 번호 : 입력한 OTP 댓글이 있는 페이지의 번호를 입력하세요.</p>
    </div>
</div>

</body>

<script>
    const fncCreateOtp = () => {
        const nameValue = document.getElementById('name').value;

        if(!validationName(nameValue)) {
            return;
        }

        const url = "/"+nameValue+"/otps";
        const method = METHOD_TYPE.POST;

        mapleFetch(url, method, null, null,
            (response) => response.text().then((value) => {
                document.getElementById('otpNumber').value = value;
                alert('OTP 번호가 발급되었습니다.');
            })
        );
    }

    const fncRegist = () => {
        let serializedFrmRegisterData = getFormDataJson("frmRegister");
        serializedFrmRegisterData.terms = getTermsCheckbox();

        // validation 통합
        if(!validationRegist(serializedFrmRegisterData)) {
            return;
        }

        const url = "/users";
        const method = METHOD_TYPE.POST;
        const header = {
            "Content-Type": "application/json",
        };
        const body = JSON.stringify(serializedFrmRegisterData);

        mapleFetch(url, method, header, body,
            (response) => {
                if (response.status == 200) {
                    alert('회원가입이 완료되었습니다. 로그인해주세요.');
                    location.href = '/user/login';
                }
            }
        );
    }

    const validationRegist = (serializedFrmRegisterData) => {
        if(!validationTerms()) {
            return false;
        }

        if(!validationName(serializedFrmRegisterData.name)) {
            return false;
        }

        if(!validationPassword(serializedFrmRegisterData.password)) {
            return false;
        }

        if(!validationPageNumber(serializedFrmRegisterData.pageNumber)) {
            return false;
        }

        return true;
    }

    const getTermsCheckbox = () => {
        let result = [];
        let list = document.querySelectorAll('input[types="로그인"]:checked');
        for(let i=0;i<list.length;i++){
            result.push(list[i].attributes.code.value);
        }
        return result;
    }

    const validationTerms = () => {
        let list = document.querySelectorAll('input[required="Y"]');

        for(let i=0;i<list.length;i++) {
            if(!list[i].checked){
                alert('필수약관 동의여부에 체크해야합니다.');
                return false;
            }
        }
        return true;
    }

    const validationName = (to) => {
        if(to.length == 0) {
            console.log('아이디를 입력하세요.');
            return false;
        }

        return true;
    }
    const validationPassword = (to) => {
        if(to.length == 0) {
            console.log('패스워드를 입력하세요.');
            return false;
        }

        if(to.length >= 15) {
            console.log('15자리 이하 패스워드를 입력하세요.');
            return false;
        }
        if(to.length < 8) {
            console.log('8자리 이상 패스워드를 입력하세요.');
            return false;
        }

        return true;
    }

    const validationPageNumber = (to) => {
        if(to.length < 1) {
            console.log('댓글페이지 번호를 입력하세요. (1이상)');
            return false;
        }

        return true;
    }

    document.getElementById('btnCreateOtp').addEventListener('click', fncCreateOtp, false);
    document.getElementById('btnRegist').addEventListener('click', fncRegist, false);
</script>
</html>