package controller;

import guestbook.dao.GuestBookDAO;
import guestbook.vo.GuestVO;
import guestbook.vo.ReplyVO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MemberController extends MultiActionController {
	private GuestBookDAO guestBookDAO;

	public void setGuestBookDAO(GuestBookDAO guestBookDAO) {
		this.guestBookDAO = guestBookDAO;
	}

	public ModelAndView basic(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		ModelAndView mav =new ModelAndView();
		String viewName = this.getViewName(request);

		mav.setViewName(viewName);
		return mav;
	}

	public ModelAndView Home(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		ModelAndView mav =new ModelAndView();
		String viewName = this.getViewName(request);

		mav.setViewName(viewName);
		return mav;
	}

	// 수정 페이지
	public ModelAndView UpdatePage(HttpServletRequest req, HttpServletResponse response) throws Exception  {
		ModelAndView mav =new ModelAndView();
		String viewName = this.getViewName(req);

		String seq = req.getParameter("seq");
		GuestVO guest = null;
		try {
			guest = guestBookDAO.selectOne(seq);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("guest", guest);
		mav.addObject("guest",guest);

		mav.setViewName(viewName);
		return mav;
	}

	// 작성페이지
	public ModelAndView writeBook(HttpServletRequest req, HttpServletResponse response) throws Exception  {
		ModelAndView mav =new ModelAndView();
		String viewName = this.getViewName(req);

		mav.setViewName(viewName);
		return mav;
	}

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
		req.setAttribute("list", list);
		mav.addObject("list",list);

		req.setAttribute("guest", guest);
		mav.addObject("guest",guest);
		mav.setViewName("read");
		return mav;
	}
	
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
	
	public ModelAndView viewJoinPage(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		ModelAndView mav =new ModelAndView();
		String viewName = this.getViewName(request);
		System.out.println(viewName);
		mav.setViewName("join");
		return mav;
	}
	
	public ModelAndView list(HttpServletRequest req, HttpServletResponse response) throws Exception  {
		ModelAndView mav =new ModelAndView();
		String viewName = this.getViewName(req);
		ArrayList<GuestVO> list = null;
		try {
			list = guestBookDAO.getGuestBookList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("list", list);
		mav.addObject("list",list);
		System.out.println("listBook");

		//mav.setViewName("listBook");
		mav.setViewName(viewName);
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
