package com.jeommechu.web.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import com.jeommechu.menu.user.MemberDAO;
import com.jeommechu.menu.user.MemberVO;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        MemberDAO dao = new MemberDAO();
        try {
            // 사용자 검증 및 사용자 정보를 가져옵니다.
            MemberVO user = dao.getUserByIdAndPassword(id, password);
            if (user != null) {
                // 로그인 성공: 세션에 사용자 정보를 저장하고 리다이렉트
                HttpSession session = request.getSession();
                session.setAttribute("memberId", user.getId()); // id 저장
                session.setAttribute("memberName", user.getMemberName());
                session.setAttribute("role", user.getRole());
                session.setAttribute("welcomeMessage", " 접속 중");
                response.sendRedirect("getLunches"); // 로그인 성공 후 이동할 페이지
            } else {
                // 로그인 실패: 오류 메시지를 쿼리 파라미터로 전달하여 로그인 페이지로 리다이렉트
                response.sendRedirect("login.html?error=Invalid%20ID%20or%20password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database access error", e);
        }
    }
}
