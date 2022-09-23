<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %>

<%--  Created by IntelliJ IDEA.--%>
<%--  User: 윤태검--%>
<%--  Date: 2022-09-06--%>
<%--  Time: 오후 1:27--%>
<%--  To change this template use File | Settings | File Templates.--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read</title>

    <script>
        function fn_sendGuest() {

            var GuestBookWrite = document.GuestBookWrite;
            var title = GuestBookWrite.title.value;
            var content = GuestBookWrite.content.value;
            var readCount = GuestBookWrite.readCount.value;
            var userId = GuestBookWrite.userId.value;

            if (title.length === 0 || title === "") {
                alert("아이디는 필수 입니다.");
            } else if (content.length === 0 || content === "") {
                alert("비밀번호는 필수 입니다.");
            } else if (userId.length === 0 || userId === "") {
                alert("이메일은 필수 입니다.");
            } else {
                GuestBookWrite.method = "post";
                GuestBookWrite.action = "BookController";
                GuestBookWrite.submit();
            }
        }

        function fn_sendReply(){
            var GuestReplyWrite = document.GuestReplyWrite;

            GuestReplyWrite.method = "post";
            GuestReplyWrite.action = "InsertReply.do";
            GuestReplyWrite.submit();
        }

    </script>

    <!-- 스타일시트 연결 -->
    <link rel="stylesheet" href="../CSS/WriteBook.css" type="text/css">
    <link rel="stylesheet" href="../CSS/Main.css" type="text/css">
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
    </style>

</head>
<body>

<%--헤더--%>
<header>
    Guestbook List
</header>

<%--네비--%>
<ul>
    <li><a class="active" href="#home">Home</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#about">About</a></li>
</ul>

<%--몸통--%>
<section>
    <nav>
        <ul>
            <li>
                <a href="list.do">
                    <button> 리스트 불러오기</button>
                </a>
            </li>
            <li>
                <a href="writeBook.do">
                    <button> 방명록 작성하기 </button>
                </a>
            </li>
            <li>
                <a href="Home.do">
                    <button> Home</button>
                </a>
            </li>
        </ul>
    </nav>
    <article>
        <div id="Main_Box">
            <h1> 게시글 </h1>
            <form name="GuestBookWrite">
                <table id="member_table">
                    <tr>
                        <th>
                            title
                        </th>
                        <td>
                            <input type="text" name="title" value="${guest.title}" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            content
                        </th>
                        <td>
                            <input type="text" name="content" id="content" value="${guest.content}" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            readCount
                        </th>
                        <td>
                            <input type="text" name="readCount" value="${guest.readCount}" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            date
                        </th>
                        <td>
                            <input type="text" name="date" value="${guest.date}" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            userId
                        </th>
                        <td>
                            <input type="text" name="userId" value="${guest.userId}" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <input type="reset" value="다시 입력">
                        </th>
                        <th>
                            <input type="button" value="글 수정하기" onclick="fn_sendGuest()">
                            <input type="hidden" name="command" value="UpdateBookWrite.do"/>
                        </th>
                    </tr>
                </table>
            </form>
        </div>

        <div id="reply_box">
            <c:forEach var="vo" items="${list}">
                <table id="reply">
                    <tr>
                        <th> content </th>
                        <td>${vo.content}</td>
                    </tr>
                    <tr>
                        <th> 작성일</th>
                        <td>${vo.reg_date}</td>
                    </tr>
                </table>
            </c:forEach>
        </div>

        <div id="WriteReplyBox">
            <form name="GuestReplyWrite">
                <input type="text" name="ReplyContent" id="ReplyContent">
                <input type="hidden" name="seq" value="${guest.seq}">
                <input type="button" value="글 작성" onclick="fn_sendReply()">
            </form>
        </div>

    </article>
</section>

<%--푸터--%>
<footer>
    <p>Footer</p>
</footer>

</body>
</html>
