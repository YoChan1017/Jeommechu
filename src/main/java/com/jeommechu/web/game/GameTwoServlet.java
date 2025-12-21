package com.jeommechu.web.game;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeommechu.menu.lunch.LunchDAO;
import com.jeommechu.menu.lunch.LunchVO;

@WebServlet("/gameTwo")
public class GameTwoServlet extends HttpServlet {
    private LunchDAO lunchDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        lunchDAO  = new LunchDAO(); // LunchDAO 인스턴스를 초기화합니다
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LunchVO> lunches;
        try {
            lunches = lunchDAO.getAllLunches(); // 모든 점심 메뉴 데이터를 가져옵니다
        } catch (SQLException e) {
            throw new ServletException("Error retrieving lunch items", e);
        }

        // 음식 이름 리스트를 추출합니다
        List<String> menuList = lunches.stream()
                                       .map(LunchVO::getFoodName)
                                       .collect(Collectors.toList());

        if (menuList.isEmpty()) {
            menuList.add("No menu items found");
        }

        // 랜덤으로 메뉴를 선택합니다
        Random random = new Random();
        String randomMenu = menuList.get(random.nextInt(menuList.size()));

        // 확률 계산
        double probability = 100.0 / menuList.size();

        // 이미지의 상대 경로 설정
        String imageURL = request.getContextPath() + "/images/random.png";  // 경로를 맞춰주세요

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Random Lunch Picker</title>");
        response.getWriter().println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css'>");
        response.getWriter().println("<style>");
        response.getWriter().println("body {");
        response.getWriter().println("    background: linear-gradient(45deg, #fbc2eb, #a6c1ee);");
        response.getWriter().println("    font-family: 'Noto Sans KR', sans-serif;");
        response.getWriter().println("    color: #333;");
        response.getWriter().println("    text-align: center;");
        response.getWriter().println("    padding: 50px;");
        response.getWriter().println("}");
        response.getWriter().println(".container {");
        response.getWriter().println("    background-color: white;");
        response.getWriter().println("    padding: 20px;");
        response.getWriter().println("    border-radius: 15px;");
        response.getWriter().println("    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);");
        response.getWriter().println("    max-width: 400px;");
        response.getWriter().println("    margin: 0 auto;");
        response.getWriter().println("}");
        response.getWriter().println("h1 {");
        response.getWriter().println("    font-size: 24px;");
        response.getWriter().println("    margin-bottom: 20px;");
        response.getWriter().println("    color: #6d6875;");
        response.getWriter().println("}");
        response.getWriter().println(".image-container {");
        response.getWriter().println("    margin-bottom: 20px;");
        response.getWriter().println("}");
        response.getWriter().println(".food-list {");
        response.getWriter().println("    margin-bottom: 20px;");
        response.getWriter().println("    max-height: 200px;");
        response.getWriter().println("    overflow-y: auto;");
        response.getWriter().println("}");
        response.getWriter().println(".food-list ul {");
        response.getWriter().println("    list-style-type: none;");
        response.getWriter().println("    padding: 0;");
        response.getWriter().println("}");
        response.getWriter().println(".food-list li {");
        response.getWriter().println("    font-size: 18px;");
        response.getWriter().println("    color: #495057;");
        response.getWriter().println("}");
        response.getWriter().println("button {");
        response.getWriter().println("    font-size: 16px;");
        response.getWriter().println("    padding: 10px 20px;");
        response.getWriter().println("    background-color: #6d6875;");
        response.getWriter().println("    color: white;");
        response.getWriter().println("    border: none;");
        response.getWriter().println("    border-radius: 5px;");
        response.getWriter().println("    cursor: pointer;");
        response.getWriter().println("    transition: background-color 0.3s;");
        response.getWriter().println("}");
        response.getWriter().println("button:hover {");
        response.getWriter().println("    background-color: #333;");
        response.getWriter().println("}");
        response.getWriter().println("#result {");
        response.getWriter().println("    margin-top: 20px;");
        response.getWriter().println("    font-size: 24px;");
        response.getWriter().println("    color: #6d6875;");
        response.getWriter().println("}");
        response.getWriter().println("#probability {");
        response.getWriter().println("    margin-top: 10px;");
        response.getWriter().println("    font-size: 18px;");
        response.getWriter().println("    color: #6d6875;");
        response.getWriter().println("}");
        response.getWriter().println(".add-menu-button {");
        response.getWriter().println("    font-size: 16px;");
        response.getWriter().println("    padding: 10px 20px;");
        response.getWriter().println("    background-color: #ff4d4d;");
        response.getWriter().println("    color: white;");
        response.getWriter().println("    border: none;");
        response.getWriter().println("    border-radius: 5px;");
        response.getWriter().println("    cursor: pointer;");
        response.getWriter().println("    transition: background-color 0.3s;");
        response.getWriter().println("    margin-top: 20px;");
        response.getWriter().println("}");
        response.getWriter().println(".add-menu-button:hover {");
        response.getWriter().println("    background-color: #cc0000;");
        response.getWriter().println("}");
        response.getWriter().println("</style>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<div class='container'>");

        response.getWriter().println("<div class='image-container'>");
        response.getWriter().println("<img src='" + imageURL + "' alt='Random Food Image' width='150'>");
        response.getWriter().println("</div>");

        response.getWriter().println("<h1>점심 메뉴 목록</h1>");
        response.getWriter().println("<div class='food-list'>");
        response.getWriter().println("<ul>");
        for (String menu : menuList) {
            response.getWriter().println("<li>" + menu + "</li>");
        }
        response.getWriter().println("</ul>");
        response.getWriter().println("</div>");

        response.getWriter().println("<button onclick=\"startDrawing()\">메뉴 뽑기 <i class='fas fa-dice'></i></button>");

        response.getWriter().println("<div id=\"result\">");
        response.getWriter().println("오늘의 메뉴: <span id=\"result-menu\">" + randomMenu + "</span>");
        response.getWriter().println("</div>");

        response.getWriter().println("<div id=\"probability\">");
        response.getWriter().println("각 메뉴가 뽑힐 확률: " + String.format("%.2f", probability) + "%");
        response.getWriter().println("</div>");

        response.getWriter().println("<button class='add-menu-button' onclick=\"window.location.href='getLunches'\">메뉴 추가하기</button>");

        response.getWriter().println("</div>"); // container 끝

        response.getWriter().println("<script>");
        response.getWriter().println("function startDrawing() {");
        response.getWriter().println("    const menus = [");
        for (String menu : menuList) {
            response.getWriter().println("        \"" + menu + "\",");
        }
        response.getWriter().println("    ];");

        response.getWriter().println("    const resultSpan = document.getElementById('result-menu');");
        response.getWriter().println("    let index = 0;");
        response.getWriter().println("    const interval = setInterval(() => {");
        response.getWriter().println("        resultSpan.innerText = menus[index];");
        response.getWriter().println("        index = (index + 1) % menus.length;");
        response.getWriter().println("    }, 100);");

        response.getWriter().println("    setTimeout(() => {");
        response.getWriter().println("        clearInterval(interval);");
        response.getWriter().println("        const randomIndex = Math.floor(Math.random() * menus.length);");
        response.getWriter().println("        resultSpan.innerText = menus[randomIndex];");
        response.getWriter().println("    }, 2000);");
        response.getWriter().println("}");
        response.getWriter().println("</script>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}