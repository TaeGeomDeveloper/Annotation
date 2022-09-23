<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: 윤태검
  Date: 2022-09-05
  Time: 오전 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WriteBook</title>

    <script>
        function fn_sendGuest(){

            var GuestBookWrite = document.GuestBookWrite;
            var title = GuestBookWrite.title.value;
            var content = GuestBookWrite.content.value;
            var readCount = GuestBookWrite.readCount.value;
            var userId = GuestBookWrite.userId.value;

            if(title.length === 0 || title === "") {
                alert("아이디는 필수 입니다.");
            } else if(content.length === 0 || content === "") {
                alert("비밀번호는 필수 입니다.");
            } else if(userId.length === 0 || userId === "") {
                alert("이메일은 필수 입니다.");
            }
            else {
                GuestBookWrite.method = "post";
                GuestBookWrite.action = "BookController";
                GuestBookWrite.submit();
            }

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

<%
    String now = null;
    // 요청에 따라 날짜, 시간 , 날짜 + 시간 정보를 제공한다.
    String pattern = "yyyy년MM월dd일 HH시mm분ss초";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    now = sdf.format(new Date());
%>

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
            <h1> 방명록 작성하기 </h1>
            <form action="InsertGuest.do" method="post">
                <table id="member_table">
                    <tr>
                        <th>
                            title
                        </th>
                        <td>
                            <input type="text" name="title">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            content
                        </th>
                        <td>
                            <input type="text" name="content" id="content">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            readCount
                        </th>
                        <td>
                            <input type="text" name="readCount" value="0" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            date
                        </th>
                        <td>
                            <input type="text" name="date" value="<%=now%>" readonly="readonly">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            userId
                        </th>
                        <td>
                            <input type="text" name="userId">
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <input type="reset" value="다시 입력">
                        </th>
                        <th>
                            <input type="submit" value="글 작성">
                        </th>
                    </tr>
                </table>
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
