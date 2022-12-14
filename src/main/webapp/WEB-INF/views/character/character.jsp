<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>캐릭터조회</title>
    <script type="text/javascript" src="/resources/js/comm/fetch.js"></script>
    <script type="text/javascript" src="/resources/js/comm/token.js"></script>
    <style>
        #character_area > div{
            display: none;
        }
        #character_area.active > .active{
            display: block;
        }
        #character_area.inactive > .inactive{
            display: block;
        }

        #comment_area > div{
            display: none;
        }
        #comment_area.active > .active{
            display: block;
        }
        #comment_area.inactive > .inactive{
            display: block;
        }
    </style>
</head>
<body onload="documentOnload();">

<div class="container">
    <div id="character_area" class="active">
        <div class="active">
            캐릭터가 존재하지않습니다.
        </div>
        <div class="inactive">
            <h1>캐릭터 정보</h1>
            <img id="image"/>
            <div id="rank"></div>
            <div id="rankMove"></div>
            <div id="userName"></div>
            <div id="job1"></div>
            <div id="job2"></div>
            <div id="level"></div>
            <div id="experience"></div>
            <div id="popularity"></div>
            <div id="guildName"></div>
        </div>
        <div class="inactive">
            <div id="comment_area" class="active">
                <h1>comment</h1>
                <div class="inactive">
                    댓글작성은 로그인이 필요합니다.
                </div>
                <div class="active">
                    <form id="frmComment">
                        <input type="hidden" id="characterId" name="characterId">
                        <textarea id="comment" name="comment"></textarea>
                        <button type="button" id="btnWriteComment">작성</button>
                    </form>
                </div>
            </div>
        </div>
        <div id="commentList" class="inactive">
            <h1>comment List</h1>
            <div>
                댓글 1
            </div>
            <div>
                댓글 2
            </div>
            <div>
                댓글 3
            </div>
        </div>
    </div>


</div>

</body>
<script>

    const selectInfo = async() => {
        const lastSegment = getURILastSegment();
        if(lastSegment == null || lastSegment == 'character') {
            return;
        }
        await selectUserInfo(lastSegment);
        await selectComments(lastSegment);
    }
    const selectUserInfo = async (characterName) => {
        const url = "/" + characterName + "/character";
        const method = METHOD_TYPE.GET;
        const header = {
            "Content-Type": "application/json",
        };

        const response = await mapleFetchAsync(url, method, header, null);

        if(!response.isSuccess() || response.getData() == null) {
            alert(response.getMessage());
            document.getElementById('character_area').className = 'active';
            return;
        }

        const info = response.getData();

        document.getElementById('character_area').className = 'inactive';

        document.getElementById('image').src = info.image;
        document.getElementById('rank').innerHTML = threeComma(info.rank);
        document.getElementById('rankMove').innerHTML = threeComma(info.rankMove);
        document.getElementById('userName').innerHTML = info.userName;
        document.getElementById('job1').innerHTML = info.job1;
        document.getElementById('job2').innerHTML = info.job2;
        document.getElementById('level').innerHTML = info.level;
        document.getElementById('experience').innerHTML = threeComma(info.experience);
        document.getElementById('popularity').innerHTML = threeComma(info.popularity);
        document.getElementById('guildName').innerHTML = info.guildName==null?'-':info.guildName;
    }

    const selectComments = async (characterName) => {
        document.querySelectorAll('#commentList>div').forEach((ele)=>ele.remove());
        if(characterName == null || characterName.length == 0) {
            return;
        }

        const url = "/" + characterName + "/comments";
        const method = METHOD_TYPE.GET;
        const header = {
            "Content-Type": "application/json",
        };

        let response = await mapleFetchAsync(url, method, header, null);
        if(!response.isSuccess()) {
            alert(response.getMessage());
            return;
        }

        let list = response.getData();
        /**
         * TODO
         * id
         * characterName
         * userId
         * comment
         * createdAt
         * 활용해서 댓글리스트 만들기 ( design )
        */
        const commentList = document.getElementById('commentList');
        for(const value of list) {
            let element = document.createElement('div');
            element.innerHTML = JSON.stringify(value);
            commentList.appendChild(element);
        }
    }

    const writeComment = async () => {

        if(document.getElementById('comment').value == "") {
            alert('댓글을 입력해주세요.');
            document.getElementById('comment').focus();
            return;
        }

        const url = "/comments";
        const method = METHOD_TYPE.POST;
        const header = {
            'Content-Type': 'application/json'
        };
        let body = getFormDataJson("frmComment");
        body.characterName = getURILastSegment();
        body = JSON.stringify(body);

        const response = await mapleFetchAsync(url, method, header, body);

        if(!response.isSuccess()) {
            alert(response.getMessage());
            return;
        }

        alert('댓글이 등록되었습니다.');
        document.getElementById('comment').value = "";
        await selectComments(getURILastSegment());
    }

    const commentWriteBlind = () => {
        const element = document.getElementById('comment_area');
        if(!isLogin()) {
            element.className = 'inactive';
            return ;
        }

        //block
        if(isBlock()) {
            element.className = 'inactive';
            element.querySelector('.inactive').innerText = "차단된 유저입니다.";
            return ;
        }

        document.getElementById('comment_area').className = 'active';
    }

    const documentOnload = () => {
        selectInfo();
        commentWriteBlind();
    }

    document.getElementById('btnWriteComment').addEventListener('click', writeComment, false);

</script>
</html>