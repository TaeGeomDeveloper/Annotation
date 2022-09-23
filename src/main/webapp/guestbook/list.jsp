<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: 윤태검
  Date: 2022-09-05
  Time: 오전 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Guestbook List</title>

    <!-- 스타일시트 연결 -->
    <link rel="stylesheet" href="../CSS/ListBook.css" type="text/css">
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
            <h1> 방명록 </h1>
            <table id="list_table">
                <tr>
                    <th>아이디</th>
                    <th id="title">제목</th>
                    <th id="content">내용</th>
                    <th>날짜</th>
                    <th>Count</th>
                    <th colspan="2"> 버튼</th>
                </tr>
                <c:forEach var="vo" items="${list}">
                    <tr>
                        <td>
                                ${vo.userId}
                        </td>
                        <td>
                            <a href="read.do?seq=${vo.seq}">
                                    ${vo.title}
                            </a>
                        </td>
                        <td>
                                ${vo.content}
                        </td>
                        <td>
                                ${vo.date}
                        </td>
                        <td>
                                ${vo.readCount}
                        </td>
                        <td>
                            <a href="UpdatePage.do?seq=${vo.seq}">
                                <button>변경하기</button>
                            </a>
                        </td>
                        <td>
                            <a href="DeleteBook.do?seq=${vo.seq}">
                                <button>삭제하기</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </article>
</section>

<%--푸터--%>
<footer>
    <p>Footer</p>
</footer>


</body>
</html>


