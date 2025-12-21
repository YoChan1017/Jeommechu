package com.jeommechu.web.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.jeommechu.menu.user.MemberDAO;
import com.jeommechu.menu.user.MemberVO;

@WebServlet(urlPatterns = "/insertUser")
public class InsertUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		
		MemberVO vo = new MemberVO();
		vo.setMemberID(id);
		vo.setMemberPW(password);
		vo.setMemberName(name);
		vo.setRole(role);
		
		MemberDAO dao = new MemberDAO();
		try {
			dao.addMember(vo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException("Database access error", e);
		}
		response.sendRedirect("login.html");
	}

}
