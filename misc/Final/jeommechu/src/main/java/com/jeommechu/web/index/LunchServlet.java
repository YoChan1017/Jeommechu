package com.jeommechu.web.index;

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

import com.jeommechu.menu.lunch.LunchDAO;
import com.jeommechu.menu.lunch.LunchVO;

@WebServlet("/getLunches")
public class LunchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LunchDAO lunchDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        lunchDAO = new LunchDAO(); // LunchDAO 인스턴스 생성
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<LunchVO> lunchList;
            try {
                lunchList = lunchDAO.getAllLunches();
            } catch (SQLException e) {
                e.printStackTrace(); // 데이터베이스 오류 처리
                lunchList = null;
            }

            HttpSession session = request.getSession();
            String errorMessage = (String) session.getAttribute("errorMessage");
            session.removeAttribute("errorMessage");
            String role = (String) session.getAttribute("role");

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>LUNCH</title>");
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
            
            out.println("<h1>LUNCH</h1>");
            
            out.println("</header>");
            
            out.println("<hr>");

            out.println("<main>");
            out.println("<br><br>");
            out.println("<h2>▶ Lunch Add ◀</h2>");
            out.println("<br>");
            // 메뉴 리스트 보기 버튼을 직접 배치
            out.println("<form action='menuList' method='get'>");
            out.println("<button type='submit'>메뉴 리스트 보기</button>");
            out.println("</form>");

            out.println("<br><br>");
            out.println("<h2>▶ Lunch Menu ◀</h2>");
            out.println("<br>");

            out.println("<div class='menupan'>");
            out.println("<table border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>선택한 메뉴</th>");
            out.println("<th>회원</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            if (lunchList != null && !lunchList.isEmpty()) {
                for (LunchVO lunch : lunchList) {
                    out.println("<tr>");
                    out.println("<td>" + lunch.getFoodName() + "</td>");
                    out.println("<td>" + lunch.getMemberName() + "</td>");
                    out.println("</tr>");
                }
            } else {
                out.println("<tr>");
                out.println("<td colspan='2'>선택된 메뉴가 없습니다.</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");

            if ("ADMIN".equals(role)) {
                out.println("<br>");
                out.println("<form action='resetLunches' method='post'>");
                out.println("<button type='submit'>메뉴 리셋</button>");
                out.println("</form>");
            } else {
                out.println("<br><p>* 메뉴 리셋은 관리자한테 문의해주세요 *</p>");
            }

            out.println("<br>");
            out.println("</main>");
            out.println("<hr>");

            out.println("<footer>");
            out.println("<br><br>");
            out.println("<button type='button' onclick=\"location.href='gameSelect'\">메뉴 뽑기</button>");
            out.println("<br>");
            out.println("<div class='left'>");
            if ("ADMIN".equals(role)) {
                out.println("<br>");
                out.println("<a href='memberList'>회원 관리</a>");
            }
            out.println("</div>");
            out.println("<div class='footer-text'>Protein Cigarette</div>");
            out.println("</footer>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}

