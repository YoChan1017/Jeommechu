package com.jeommechu.web.member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.jeommechu.menu.user.MemberDAO;
import com.jeommechu.menu.user.MemberVO;

@WebServlet("/memberEdit")
public class MemberEditServlet extends HttpServlet {

    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	try {
            int id = Integer.parseInt(request.getParameter("id"));
            MemberVO member = memberDAO.getMember(id);
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            HttpSession session = request.getSession();
            String errorMessage = (String) session.getAttribute("errorMessage");
            session.removeAttribute("errorMessage");
            String role = (String) session.getAttribute("role");
            
            if (member != null) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head><title>회원 수정</title>");
                out.println("<link rel='stylesheet' href='static/main.css'>");
                out.println("</head>");
                
                out.println("<body>");
                
                out.println("<header>");
                out.println("<a href='getLunches' class='left'>JEOMMECHU</a>");
                out.println("<div class='rights'>");

                String memberName = (String) session.getAttribute("memberName");
                String welcomeMessage = (String) session.getAttribute("welcomeMessage");
                if (memberName != null && welcomeMessage != null) {
                    out.println(memberName + welcomeMessage);
                    out.println("<a href='logout'>Logout</a>");
                } else {
                    response.sendRedirect("login.html");
                    return; // 페이지 로드 중단
                }
                out.println("</div>");
                out.println("<h1>회원 수정</h1>");
                out.println("</header>");
                
                out.println("<hr>");
                
                out.println("<main>");
                out.println("<br><br>");
                out.println("<form action='memberEdit' method='post'>");
                out.println("<input type='hidden' name='id' value='" + member.getId() + "'/>");
                out.println("<label for='memberID'>회원 ID:</label>");
                out.println("<input type='text' id='memberID' name='memberID' value='" + member.getMemberID() + "' required/><br/>");
                out.println("<br>");
                out.println("<label for='memberPW'>비밀번호:</label>");
                out.println("<input type='text' id='memberPW' name='memberPW' value='" + member.getMemberPW() + "' required/><br/>");
                out.println("<br>");
                out.println("<label for='memberName'>회원 이름:</label>");
                out.println("<input type='text' id='memberName' name='memberName' value='" + member.getMemberName() + "' required/><br/>");
                out.println("<br>");
                out.println("<label for='role'>권한:</label>");
                out.println("<input type='text' id='role' name='role' value='" + member.getRole() + "' required/><br/>");
                out.println("<input type='submit' value='수정 완료'/>");
                out.println("<br><br>");
                out.println("</form>");
                out.println("</main>");
                
                out.println("<hr>");
                
                out.println("<footer>");
                out.println("<br><br>");
                out.println("<form action=\"memberList\" method=\"get\">");
                out.println("<button type=\"submit\">Back</button>");
                out.println("</form>");
                out.println("<div class='footer-text'>Protein Cigarette</div>");
                out.println("</footer>");
                out.println("</body>");
                out.println("</html>");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "회원 정보를 찾을 수 없습니다.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String memberID = request.getParameter("memberID");
            String memberPW = request.getParameter("memberPW");
            String memberName = request.getParameter("memberName");
            String role = request.getParameter("role");

            MemberVO member = new MemberVO(id, memberID, memberPW, memberName, role);
            memberDAO.updateMember(member);
            response.sendRedirect("memberList");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }
}
