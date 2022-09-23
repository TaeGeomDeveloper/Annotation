package guestbook.controller;

import guestbook.dao.GuestBookDAO;
import guestbook.vo.GuestVO;
import guestbook.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller("guestbookController")
@RequestMapping("/guestbook")
public class GuestbookController {

    @Autowired
    private GuestBookDAO guestBookDAO;

    public ModelAndView basic(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        ModelAndView mav = new ModelAndView();
        String viewName = this.getViewName(req);

        mav.setViewName(viewName);
        return mav;
    }

    @RequestMapping(value="/Home.do",method= RequestMethod.GET)
    public ModelAndView Home(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        ModelAndView mav = new ModelAndView();
        String viewName = this.getViewName(req);

        mav.setViewName("Home");
        return mav;
    }

    @RequestMapping(value="/list.do",method= RequestMethod.GET)
    public ModelAndView list(HttpServletRequest req, HttpServletResponse resp)throws Exception {
        ModelAndView mav = new ModelAndView();
        String viewName = this.getViewName(req);

        ArrayList<GuestVO> list = null;
        try {
            list = guestBookDAO.getGuestBookList();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //req.setAttribute("list", list);
        mav.addObject("list",list);
        System.out.println("listBook");

        mav.setViewName("list");
        return mav;
    }

    @RequestMapping(value="/read.do",method= RequestMethod.GET)
    public ModelAndView read(HttpServletRequest req, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();
        //String viewName = this.getViewName(request);
        String seq = req.getParameter("seq");
        List<ReplyVO> list = null;
        GuestVO guest = null;
        try {
            guest = guestBookDAO.selectOne(seq);
            list = guestBookDAO.getReplyList(seq);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //req.setAttribute("list", list);
        mav.addObject("list",list);

        //req.setAttribute("guest", guest);
        mav.addObject("guest",guest);
        mav.setViewName("read");
        return mav;
    }

    // 수정 페이지
    @RequestMapping(value="/UpdatePage.do",method= RequestMethod.GET)
    public ModelAndView UpdatePage(HttpServletRequest req, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();
        //String viewName = this.getViewName(req);

        String seq = req.getParameter("seq");
        GuestVO guest = null;
        try {
            guest = guestBookDAO.selectSeq(seq);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //req.setAttribute("guest", guest);
        mav.addObject("guest",guest);

        mav.setViewName("UpdatePage");
        return mav;
    }

    // 작성페이지
    @RequestMapping(value="/writeBook.do",method= RequestMethod.GET)
    public ModelAndView writeBook(HttpServletRequest req, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();
        String viewName = this.getViewName(req);

        mav.setViewName("writeBook");
        return mav;
    }

    // 삭제하기
    @RequestMapping(value="/DeleteBook.do",method= RequestMethod.GET)
    public ModelAndView DeleteBook(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();
        String seq = request.getParameter("seq");
        try {
            guestBookDAO.deleteOne(seq);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mav.setViewName("redirect:./list.do");
        return mav;
    }

    // 변경하기
    @RequestMapping(value="/Update.do",method= RequestMethod.POST)
    public ModelAndView Update(HttpServletRequest req, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();

        // 추가 회원 내용
        String seq = req.getParameter("seq");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String readCount = req.getParameter("readCount");;
        String userId = req.getParameter("userId");

        GuestVO vo = new GuestVO(Integer.valueOf(seq),userId,title,content,null,Integer.valueOf(readCount));

        // 로직 처리
        try {
            boolean flag = guestBookDAO.updateOne(vo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mav.setViewName("redirect:./list.do");
        return mav;
    }

    // 글 삽입하기
    @RequestMapping(value="/InsertGuest.do",method= RequestMethod.POST)
    public ModelAndView InsertGuest(HttpServletRequest req, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();

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
            boolean flag = guestBookDAO.InsertGuest(vo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mav.setViewName("redirect:./list.do");
        return mav;
    }

    // 댓글 삽입하기
    @RequestMapping(value="/InsertReply.do",method= RequestMethod.POST)
    public ModelAndView InsertReply(HttpServletRequest req, HttpServletResponse response) throws Exception  {
        ModelAndView mav =new ModelAndView();

        // 추가 댓글 내용
        String content = req.getParameter("ReplyContent");
        String seq = req.getParameter("seq");

        ReplyVO vo = new ReplyVO();
        vo.setSeq(Integer.parseInt(seq));
        vo.setContent(content);

        // 로직 처리
        try {
            boolean flag = guestBookDAO.InsertReply(vo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        mav.setViewName("redirect:./read.do?seq="+seq);
        return mav;
    }

    private String getViewName(HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        if (uri == null || uri.trim().equals("")) {
            uri = request.getRequestURI();
        }

        int begin = 0; //
        if (!((contextPath == null) || ("".equals(contextPath)))) {
            begin = contextPath.length();
        }

        int end;
        if (uri.indexOf(";") != -1) {
            end = uri.indexOf(";");
        } else if (uri.indexOf("?") != -1) {
            end = uri.indexOf("?");
        } else {
            end = uri.length();
        }


        String fileName = uri.substring(begin, end);
        if (fileName.indexOf(".") != -1) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));

        }
        if (fileName.lastIndexOf("/") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());

        }
        return fileName;
    }

}
