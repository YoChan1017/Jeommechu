package com.jeommechu.web.game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jeommechu.menu.lunch.LunchDAO;
import com.jeommechu.menu.common.JDBCUtil;

@WebServlet("/gameOne")
public class GameOneServlet extends HttpServlet {

    private LunchDAO LunchDAO;

    @Override
    public void init() throws ServletException {
        LunchDAO = new LunchDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<String> lunches = new ArrayList<>();
        
        String query = "SELECT f.Name FROM Lunch l JOIN FoodList f ON l.foodlist_num = f.Num";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lunches.add(rs.getString("Name")); // FoodList의 name 컬럼
            }

            if (lunches.isEmpty()) {
                throw new ServletException("No menu items found in the database.");
            }

            Collections.shuffle(lunches);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        String selectedMenu = lunches.get(0);

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>LUNCH-GAME-1</title>");
            out.println("<link rel='stylesheet' href='static/game.css'>");
            
            out.println("<style>");
            out.println("#game-one .game-one-container { max-width: 800px; margin: 0 auto; padding: 20px; background-color: #FFFFFF; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println("#game-one .game-one-table { width: 100%; border-collapse: collapse; }");
            out.println("#game-one .game-one-table td { padding: 15px; text-align: center; }");
            out.println("#game-one .game-one-overlay { background: rgba(0, 0, 0, 0.7); color: white; cursor: pointer; display: flex; align-items: center; justify-content: center; height: 100%; width: 100%; }");
            out.println("#game-one .game-one-content { display: none; }");
            out.println("</style>");
            
            out.println("<script>");
            out.println("function showMenu() {");
            out.println("    document.getElementById('menu-overlay').style.display = 'none';");
            out.println("    document.getElementById('menu-content').style.display = 'block';");
            out.println("}");
            out.println("</script>");
            
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<section id=\"bg\">");
            out.println("<div id='game-one'>");
            
            out.println("<h1 class=\"sixth\"> What is Today’s Lunch Menu? </h1>");
            
            out.println("<br><br><br>");
            
            out.println("<div class='game-one-container'>");
            out.println("<table class='game-one-table'>");
            out.println("<tbody>");
            out.println("<tr>");
            out.println("<td class='menu-container'>");
            out.println("<div id='menu-overlay' class='game-one-overlay' onclick='showMenu()'>");
            out.println("오늘의 점심메뉴 확인하기");
            out.println("</div>");
            out.println("<div id='menu-content' class='game-one-content'>");
            out.println(selectedMenu);
            out.println("<br>");
            out.println("</div>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            
            out.println("<br><br><br><br><br>");
            out.println("<button class=\"learn-more\" onClick=\"window.location.reload()\">ReStart</button>");
            out.println("<br><br><br>");
            out.println("<form action=\"getLunches\" method=\"get\">");
            out.println("<button class=\"learn-more\" type=\"submit\">Main</button>");
            out.println("</form>");
            
            out.println("</div>");
            out.println("</section>");
            out.println("</center>");
            out.println("<div class=\"footer-text\">Protein Cigarette</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
