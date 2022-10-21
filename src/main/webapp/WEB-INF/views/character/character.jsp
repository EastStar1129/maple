<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>캐릭터조회</title>
    <link rel="shortcut icon" href="/resources/favicon.ico"/>
    <script type="text/javascript" src="/resources/js/comm/fetch.js"></script>
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
            <div id="comment_area" class="inactive">
                <h1>comment</h1>
                <div class="active">
                    댓글작성은 로그인이 필요합니다.
                </div>
                <div class="inactive">
                    <form id="frmComment">
                        <input type="hidden" id="userId" name="userId" value="1">
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
    const selectUserInfo = async (characterName) => {
        const url = "/" + characterName + "/character";
        const method = METHOD_TYPE.GET;
        const header = {
            "Content-Type": "application/json",
        };

        const response = await mapleFetchAsync(url, method, header, null);

        response
            .json()
            .then((data) => {
                document.getElementById('character_area').className = 'inactive';

                document.getElementById('image').src = data.image;
                document.getElementById('characterId').value = data.id;
                document.getElementById('rank').innerHTML = threeComma(data.rank);
                document.getElementById('rankMove').innerHTML = threeComma(data.rankMove);
                document.getElementById('userName').innerHTML = data.userName;
                document.getElementById('job1').innerHTML = data.job1;
                document.getElementById('job2').innerHTML = data.job2;
                document.getElementById('level').innerHTML = data.level;
                document.getElementById('experience').innerHTML = threeComma(data.experience);
                document.getElementById('popularity').innerHTML = threeComma(data.popularity);
                document.getElementById('guildName').innerHTML = data.guildName===null?'-':data.guildName;
                selectComments();
            })
            .catch((err) => {
                document.getElementById('character_area').className = 'active';
                console.log(err);
            });
    }

    const validateCharacterURI = () => {
        const lastSegment = getURILastSegment();
        if(lastSegment === null || lastSegment === 'character') {
            return;
        }
        selectUserInfo(lastSegment);
    }

    const getURILastSegment = () => {
        let parts = window.location.pathname.split('/');
        return parts.pop() || parts.pop();  // handle potential trailing slash
    }

    const threeComma = (str) => {
        str += '';
        return str.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
    }

    const writeComment = () => {

        if(document.getElementById('comment').value === "") {
            alert('댓글을 입력해주세요.');
            document.getElementById('comment').focus();
            return;
        }

        const url = "/comments";
        const method = METHOD_TYPE.POST;
        const header = {
            'Content-Type': 'application/json'
        };
        const body = JSON.stringify(getFormDataJson("frmComment"));

        mapleFetch(url, method, header, body,
            (response) => {
                loading = false;
                if (response.status == 200) {
                    alert('댓글이 등록되었습니다.');
                    document.getElementById('comment').value = "";
                    selectComments();
                }
            }
        );
    }

    const selectComments = () => {
        const id = document.getElementById('characterId').value;
        if(id === null || id.length === 0) {
            return;
        }

        const url = "/" + id + "/comments";
        const method = METHOD_TYPE.GET;
        const header = {
            "Content-Type": "application/json",
        };

        mapleFetch(url, method, header, null,
            (response) => response.json().then((list) => {
                document.querySelectorAll('#commentList>div').forEach((ele)=>ele.remove());
                if(list.length === 0) {
                    return;
                }

                /**
                 * TODO
                 * id
                 * characterId
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
                    console.log(value);
                }
            })
        );
    }
    const documentOnload = () => {
        validateCharacterURI();
    }

    document.getElementById('btnWriteComment').addEventListener('click', writeComment, false);

</script>
</html>