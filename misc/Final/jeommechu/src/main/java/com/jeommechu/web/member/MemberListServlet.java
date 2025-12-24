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
import java.util.List;

import com.jeommechu.menu.user.MemberDAO;
import com.jeommechu.menu.user.MemberVO;

@WebServlet("/memberList")
public class MemberListServlet extends HttpServlet {

    private MemberDAO memberDAO;

    @Override
    public void init() throws ServletException {
        memberDAO = new MemberDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html;charset=UTF-8");
    	try {
            List<MemberVO> members = memberDAO.getAllMembers();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            HttpSession session = request.getSession();
            String errorMessage = (String) session.getAttribute("errorMessage");
            session.removeAttribute("errorMessage");
            String role = (String) session.getAttribute("role");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>회원 목록</title>");
            out.println("<link rel='stylesheet' href='static/main.css'>");
            out.println("</head>");
            
            out.println("<body id='lunch-page'>");
            
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
            out.println("<h1>회원 목록</h1>");
            out.println("</header>");
            
            out.println("<hr>");
            
            out.println("<main>");
            out.println("<div class='menupan'>");
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr><th>NO</th><th>USER ID</th><th>NAME</th><th>권한</th><th>수정</th><th>삭제</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            for (MemberVO member : members) {
                out.println("<tr>");
                out.println("<td>" + member.getId() + "</td>");
                out.println("<td>" + member.getMemberID() + "</td>");
                out.println("<td>" + member.getMemberName() + "</td>");
                out.println("<td>" + member.getRole() + "</td>");
                out.println("<td><a href='memberEdit?id=" + member.getId() + "'>수정</a></td>");
                out.println("<td><a href='memberDelete?id=" + member.getId() + "'>삭제</a></td>");
                out.println("</tr>");
            }
        
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</main>");
            
            out.println("<hr>");
            
            out.println("<footer>");
            out.println("<form action=\"getLunches\" method=\"get\">");
            out.println("<button type=\"submit\">Back</button>");
            out.println("</form>");
            out.println("<div class='footer-text'>Protein Cigarette</div>");
            out.println("</footer>");
            out.println("</body>");
            out.println("</html>");
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }
}
