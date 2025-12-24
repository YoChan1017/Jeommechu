package com.jeommechu.web.game;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeommechu.menu.common.JDBCUtil;

@WebServlet("/gameFour")
public class GameFourServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> lunches = new ArrayList<>();
        String query = "SELECT f.Name FROM Lunch l JOIN FoodList f ON l.foodlist_num = f.Num";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lunches.add(rs.getString("Name"));
            }

            // 메뉴가 없을 경우 메시지를 표시
            if (lunches.isEmpty()) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html lang='ko'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>점메추 - 룰렛</title>");
                out.println("<style>");
                out.println("body { font-family: 'Comic Sans MS', cursive, sans-serif; text-align: center; margin-top: 50px; display: flex; justify-content: center; align-items: center; height: 100vh; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>등록된 메뉴가 없습니다.</h1>");
                out.println("<button onclick=\"window.location.href='getLunches'\">메뉴 추가하기</button>");
                out.println("</body>");
                out.println("</html>");
                return; // 더 이상 진행하지 않음
            }

            Collections.shuffle(lunches);
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // HTML 및 JavaScript 코드
        out.println("<!DOCTYPE html>");
        out.println("<html lang='ko'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>점메추 - 룰렛</title>");
        out.println("<style>");
        out.println("@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css');");
        out.println("* { font-family: Pretendard; }");
        out.println("body { background: url('images/foods2.jpg') repeat; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
        out.println("canvas { transition: 2s; pointer-events: none; border-radius: 10px; }");
        out.println("button { background: #ffccbc; margin-top: 1rem; padding: .8rem 1.8rem; border: none; font-size: 1.5rem; font-weight: bold; border-radius: 5px; transition: .2s; cursor: pointer; }");
        out.println("button:hover { background: #ffab91; }");
        out.println("button:active { background: #ff8a65; color: #fff; }");
        out.println("div { width: 500px; display: flex; align-items: center; flex-direction: column; position: relative; justify-content: center; }");
        out.println("div::before { content: ''; position: absolute; width: 5px; height: 30px; border-radius: 5px; background: #000; top: -20px; left: 50%; transform: translateX(-50%); z-index: 22; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div>");
        out.println("<canvas width='600' height='600'></canvas>"); // 크기를 600x600으로 변경
        out.println("<button onclick='rotate()'>룰렛 돌리기</button>");
        out.println("<button onclick=\"window.location.href='getLunches'\" style=\"margin-top: 20px;\">메인으로</button>");
        out.println("</div>");
        out.println("<script>");
        out.println("const $c = document.querySelector('canvas');");
        out.println("const ctx = $c.getContext('2d');");
        out.println("let product = " + convertToJavaScriptArray(lunches) + ";");
        out.println("const colors = ['#d1495b', '#f79256', '#f9c74f', '#90be6d', '#43aa8b', '#577590', '#f94144', '#f3722c', '#f8961e', '#f9844a'];");
        out.println("const newMake = () => {");
        out.println("  const [cw, ch] = [$c.width / 2, $c.height / 2];");
        out.println("  const arc = Math.PI / (product.length / 2);");
        out.println("  for (let i = 0; i < product.length; i++) {");
        out.println("    ctx.beginPath();");
        out.println("    ctx.fillStyle = colors[i % colors.length];");
        out.println("    ctx.moveTo(cw, ch);");
        out.println("    ctx.arc(cw, ch, cw, arc * (i - 1), arc * i);");
        out.println("    ctx.fill();");
        out.println("    ctx.closePath();");
        out.println("  }");
        out.println("  ctx.fillStyle = '#fff';");
        out.println("  ctx.font = '18px Pretendard';");
        out.println("  ctx.textAlign = 'center';");
        out.println("  for (let i = 0; i < product.length; i++) {");
        out.println("    const angle = (arc * i) + (arc / 2);");
        out.println("    ctx.save();");
        out.println("    ctx.translate(cw + Math.cos(angle) * (cw - 50), ch + Math.sin(angle) * (ch - 50));");
        out.println("    ctx.rotate(angle + Math.PI / 2);");
        out.println("    ctx.fillText(product[i], 0, 0);");
        out.println("    ctx.restore();");
        out.println("  }");
        out.println("};");
        out.println("const rotate = () => {");
        out.println("  $c.style.transform = `initial`;"); 
        out.println("  $c.style.transition = `initial`;"); 
        out.println("  setTimeout(() => {");
        out.println("    const ran = Math.floor(Math.random() * product.length);");
        out.println("    const arc = 283 / product.length;");
        out.println("    const additionalRotations = Math.floor(Math.random() * 10) + 10;");
        out.println("    const rotate = (ran * arc) + (360 * additionalRotations) + (arc * 3) - (arc / 4);");
        out.println("    $c.style.transform = `rotate(-${rotate}deg)`;");
        out.println("    $c.style.transition = 'transform 4s ease-out';"); // 4초에 걸쳐 부드럽게 멈춤
        out.println("  }, 1);");
        out.println("};");
        out.println("newMake();");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
    }

    private String convertToJavaScriptArray(List<String> menuList) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < menuList.size(); i++) {
            sb.append("'").append(menuList.get(i)).append("'");
            if (i < menuList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}