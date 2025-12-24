package com.jeommechu.web.index;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/gameSelect")
public class GameSelectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 세션 확인
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("memberName") == null) {
            // 로그인하지 않은 사용자라면 로그인 페이지로 리다이렉트
            response.sendRedirect("login.html");
            return;
        }

        // 로그인된 사용자에게 게임 선택 페이지 제공
        response.setContentType("text/html;charset=UTF-8");
        try (var out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("    <meta charset=\"UTF-8\">");
            out.println("    <title>게임 선택</title>");
            out.println("    <link rel='stylesheet' href='static/main.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("    <header>");
            out.println("        <a href='getLunches' class='left'>JEOMMECHU</a>");
            out.println("        <div class=\"rights\">");
            String memberName = (String) session.getAttribute("memberName");
            String welcomeMessage = (String) session.getAttribute("welcomeMessage");
            if (memberName != null && welcomeMessage != null) {
                out.println(memberName + welcomeMessage);
                out.println("<a href='logout'>Logout</a>");
            } else {
                response.sendRedirect("login.html");
            }
            out.println("        </div>");
            out.println("        <h1>Game Selection</h1>");
            out.println("    </header>");
            out.println("    <hr>");
            out.println("    <main>");
            out.println("        <br>");
            
            out.println("        <form action=\"gameOne\" method=\"get\">");
            out.println("            <button type=\"submit\">Game 1</button>");
            out.println("        </form>");
            out.println("        <p> GAME.1 : 간단하게 메뉴 뽑기 </p>");
            out.println("        <br>");
            
            out.println("        <form action=\"gameTwo\" method=\"get\">");
            out.println("            <button type=\"submit\">Game 2</button>");
            out.println("        </form>");
            out.println("        <p> GAME.2 : 랜덤 슬롯 </p>");
            out.println("        <br>");
            
            out.println("        <form action=\"LadderGame.html\" method=\"get\">");
            out.println("            <button type=\"submit\">Game 3</button>");
            out.println("        </form>");
            out.println("        <p> GAME.3 : 사다리타기 </p>");
            out.println("        <br>");
            
            out.println("        <form action=\"gameFour\" method=\"get\">");
            out.println("            <button type=\"submit\">Game 4</button>");
            out.println("        </form>");
            out.println("        <p> GAME.4 : 돌려 돌려 !돌림판! </p>");
            out.println("        <br>");
            
            out.println("        <form onsubmit=\"return showAlert();\" method=\"get\">");
            out.println("            <button type=\"submit\">Game 5</button>");
            out.println("        </form>");
            out.println("        <script>");
            out.println("            function showAlert() {");
            out.println("                alert('앞으로의 업데이트를 기다려주세요');");
            out.println("                return false;"); // 폼 제출을 방지
            out.println("            }");
            out.println("        </script>");
            out.println("        <p> GAME.5 : 업데이트 예정 </p>");
            out.println("        <br>");
            
            out.println("    </main>");
            out.println("    <hr>");
            out.println("    <footer>");
            out.println("        <br>");
            out.println("        <br>");
            out.println("        <form action=\"getLunches\" method=\"get\">");
            out.println("            <button type=\"submit\">Back</button>");
            out.println("        </form>");
            out.println("        <div class=\"footer-text\">Protein Cigarette</div>");
            out.println("    </footer>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
