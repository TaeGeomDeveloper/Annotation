package controller;

import guestbook.dao.GuestBookDAO;
import guestbook.vo.GuestVO;
import guestbook.vo.ReplyVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 코드를 내부적으로 대신 만들어준다.
//@WebServlet("*.do")
public class BookController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doGet");

        resp.setContentType("text/html; charset=UTF-8");

        //String command = req.getParameter("command");
        String uri = req.getRequestURI();
        String[] temp = uri.split("/");
        String command = temp[temp.length - 1];

        req.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        //out.print("<h1> Hi! Servlet~ </h1>");

        GuestBookDAO dao = new GuestBookDAO();

        String url = "guestbook/list.jsp";

        // 전체 회원 리스트
        if (command.equals("list.do")) {

            // 전체 조회
            List<GuestVO> list = new ArrayList<GuestVO>();
            try {
                list = dao.getGuestBookList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);
        }// 방명록 작성 페이지
        else if(command.equals("GuestBookWrite.do")) {
            url = "guestbook/writeBook.jsp";
        }

        else if(command.equals("read.do")) {

            // 개별 회원 정보 조회
            String seq = req.getParameter("seq");
            GuestVO vo = null;

            // 댓글 전체 조회
            ArrayList<ReplyVO> list = new ArrayList<ReplyVO>();

            try {
                vo = dao.selectSeq(seq);
                list = dao.getReplyList(seq);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("guest", vo);
            req.setAttribute("list", list);

            url = "guestbook/read.jsp";
        }

        else if(command.equals("UpdateBookWrite.do")) {
            // 개별 회원 정보 조회
            String id = req.getParameter("id");
            GuestVO vo = null;

            try {
                vo = dao.selectOne(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("guest", vo);
            url = "guestbook/UpdatePage.jsp";
        } else if (command.equals("jquery.do")) {

            url = "./jquery/jquery_test.jsp";
        }

        else if (command.equals("DeleteBook.do")) {

            // 개별 회원 정보 조회
            String seq = req.getParameter("seq");

            // 전체 조회
            List<GuestVO> list = new ArrayList<GuestVO>();

            try {
                dao.deleteOne(seq);
                list = dao.getGuestBookList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);
            url = "guestbook/list.jsp";
        }

        if(!command.equals("list.do")){
            RequestDispatcher rd = req.getRequestDispatcher(url);
            rd.forward(req, resp);
        } else {
         resp.sendRedirect(url);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        System.out.println("doPost");

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        //String command = req.getParameter("command");

        String uri = req.getRequestURI();
        String[] temp = uri.split("/");
        String command = temp[temp.length - 1];

        PrintWriter out = resp.getWriter();
        //out.print("<h1> Hi! Servlet~ </h1>");
        GuestBookDAO dao = new GuestBookDAO();

        String url = "guestbook/list.jsp";

        // 회원 가입 추가하기
        if(command.equals("InsertGuest.do")){

            // 추가 회원 내용
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String userId = req.getParameter("userId");

            GuestVO vo = new GuestVO();
            vo.setTitle(title);
            vo.setContent(content);
            vo.setUserId(userId);

            // 로직 처리
            try {
                boolean flag = dao.InsertGuest(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 리스트 처리
            List<GuestVO> list = new ArrayList<GuestVO>();
            try {
                list = dao.getGuestBookList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);

            url = "list.do";
        }

        else if(command.equals("InsertReply.do")){

            // 추가 댓글 내용
            String content = req.getParameter("ReplyContent");
            String seq = req.getParameter("seq");

            ReplyVO vo = new ReplyVO();
            vo.setSeq(Integer.parseInt(seq));
            vo.setContent(content);

            // 로직 처리
            try {
                boolean flag = dao.InsertReply(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            url = "read.do?seq="+seq;
        }

        // 수정 적용하기
        else if(command.equals("UpdateGuest.do")){

            // 추가 회원 내용
            int seq = Integer.parseInt(req.getParameter("seq"));
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String readCount = req.getParameter("readCount");;
            String userId = req.getParameter("userId");

            GuestVO vo = new GuestVO(seq,userId,title,content,null,Integer.valueOf(readCount));

            // 로직 처리
            try {
                boolean flag = dao.updateOne(vo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // 리스트 처리
            List<GuestVO> list = new ArrayList<GuestVO>();
            try {
                list = dao.getGuestBookList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);

            url = "list.do";
        }

        else if(command.equals("read.do")) {

            // 개별 회원 정보 조회
            String seq = req.getParameter("seq");
            GuestVO vo = null;

            // 댓글 전체 조회
            ArrayList<ReplyVO> list = new ArrayList<ReplyVO>();

            try {
                vo = dao.selectSeq(seq);
                list = dao.getReplyList(seq);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("guest", vo);
            req.setAttribute("list", list);

            url = "guestbook/read.jsp";
        }

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);

    }

//    private void test(){
//        String uri = req.getRequestURI();
//        String[] temp = uri.split("/");
//        String uri1 = temp[temp.length - 1];
//        String url = req.getRequestURL().toString();
//        String context = req.getContextPath();
//
//        int len = context.length();
//        String uri2 = uri.substring(len+1);
//
//        out.print("<h1> hi servlet </h1>");
//        out.print("<h1> uri = "+uri+" </h1>");
//        out.print("<h1> uri = "+uri1+" </h1>");
//        out.print("<h1> uri = "+uri2+" </h1>");
//        out.print("<h1> url = "+url+" </h1>");
//        out.print("<h1> context path = "+context+" </h1>");
//    }

    @Override
    public void destroy() {
        System.out.println("destory");
    }
}
