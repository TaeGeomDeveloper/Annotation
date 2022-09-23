package controller;

import guestbook.dao.GuestBookDAO;
import guestbook.vo.GuestVO;

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

public class BookServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");

        resp.setContentType("text/html; charset=UTF-8");

        String command = req.getParameter("command");
        req.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        //out.print("<h1> Hi! Servlet~ </h1>");

        GuestBookDAO dao = new GuestBookDAO();

        String url = "guestbook/list.jsp";

        // 전체 회원 리스트
        if (command.equals("list")) {

            // 전체 조회
            List<GuestVO> list = new ArrayList<GuestVO>();
            try {
                list = dao.getGuestBookList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);
        }// 방명록 작성 페이지
        else if(command.equals("GuestBookWrite")) {
            url = "guestbook/writeBook.jsp";
        }

        else if(command.equals("UpdateBookWrite")) {
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
        }

        else if (command.equals("DeleteBook")) {

            String id = req.getParameter("id");
            try {
                dao.deleteOne(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // 전체 조회
            List<GuestVO> list = new ArrayList<GuestVO>();
            try {
                list = dao.getGuestBookList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("list", list);
        }

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println("doPost");

        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        String command = req.getParameter("command");

        PrintWriter out = resp.getWriter();
        //out.print("<h1> Hi! Servlet~ </h1>");
        GuestBookDAO dao = new GuestBookDAO();

        String url = "guestbook/list.jsp";

        // 회원 가입 추가하기
        if(command.equals("InsertGuest")){

            // 추가 회원 내용
            int seq = Integer.parseInt(req.getParameter("seq"));
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String readCount = req.getParameter("readCount");;
            String userId = req.getParameter("userId");

            GuestVO vo = new GuestVO(seq,userId,title,content,null,Integer.valueOf(readCount));

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

            url = "guestbook/list.jsp";
        }
        
        // 수정 적용하기
        if(command.equals("UpdateGuest")){

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

            url = "guestbook/list.jsp";
        }

        RequestDispatcher rd = req.getRequestDispatcher(url);
        rd.forward(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("destory");
    }

}
