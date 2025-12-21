package com.jeommechu.web.menulist;

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

import com.jeommechu.menu.menulist.MenuListDAO;
import com.jeommechu.menu.menulist.MenuListVO;

@WebServlet("/menuList")
public class MenuListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MenuListDAO menuListDAO = new MenuListDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String errorMessage = (String) session.getAttribute("errorMessage");
        session.removeAttribute("errorMessage");

        String popupMessage = (String) session.getAttribute("popupMessage");
        session.removeAttribute("popupMessage");

        String role = (String) session.getAttribute("role");

        List<MenuListVO> menuList = null;

        try (PrintWriter out = response.getWriter()) {
            try {
                String search = request.getParameter("search");
                menuList = menuListDAO.getMenuList(search); // 검색어를 전달하여 메뉴 리스트를 가져옴
            } catch (SQLException e) {
                throw new ServletException("데이터베이스 오류 발생", e); // 서블릿 예외로 래핑
            }

            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");

            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>"); // 반응형 웹을 위한 meta 태그 추가
            out.println("<title>메뉴 목록과 Tableau 대시보드</title>");
            out.println("<link rel='stylesheet' href='static/main.css'>");

            // 반응형 스타일 추가 및 Tableau 대시보드 크기 조정
            out.println("<style>");
            out.println("body { margin: 0; padding: 0; font-family: 'Do Hyeon', sans-serif; }");
            out.println("main { padding: 20px; display: flex; flex-direction: row; justify-content: space-between; }");
            out.println(".menu-container { background-color: #FFFFFF; border-radius: 8px; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); width: 100%; max-width: 600px; }"); // 메뉴 컨테이너 너비를 고정
            out.println(".menu-table-wrapper { max-height: 400px; overflow-y: auto; }"); // 스크롤을 추가하기 위한 래퍼
            out.println(".menu-table { width: 100%; border-collapse: collapse; }"); // 테이블 너비를 100%로 설정
            out.println("table th, table td { padding: 10px; text-align: center; border: 1px solid #ddd; }");
            out.println("#vizContainer { width: 100%; height: 600px; border: 1px solid #ddd; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin-left: 20px; }"); // Tableau 컨테이너 조정
            out.println("footer { text-align: center; padding: 10px; background-color: #f8f8f8; border-top: 1px solid #ddd; }");
            out.println("footer button { padding: 10px 20px; font-size: 16px; cursor: pointer; }");
            out.println("#tooltip { display: none; position: absolute; background-color: rgba(255, 255, 255, 0.9); border: 1px solid #ccc; padding: 10px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); font-size: 12px; color: #333; z-index: 1000; transition: opacity 0.2s ease-in-out; }");
            out.println("#tooltip.show { opacity: 1; }");
            out.println("</style>");

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
            out.println("<h1>메뉴 목록</h1>");
            out.println("</header>");
            
            out.println("<hr>");

            out.println("<main>");
            out.println("<div class='menu-container'>");
            out.println("<h2>▶ 메뉴 검색 ◀</h2>");
            out.println("<form action='menuList' method='get'>");
            out.println("검색: <input type='text' name='search' value='" + request.getParameter("search") + "'/>");
            out.println("<input type='submit' value='검색'/>");
            out.println("</form>");

            out.println("<br><br>");
            out.println("<h2>▶ MENU LIST ◀</h2>");
            out.println("<div class='menu-table-wrapper'>"); // 메뉴 테이블을 감싸는 div 추가
            out.println("<table class='menu-table' border='1'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>번호</th>");
            out.println("<th>이름</th>");
            out.println("<th>고르기</th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            if (menuList != null && !menuList.isEmpty()) {
                for (MenuListVO menu : menuList) {
                    String tooltipData = "전체 칼로리: " + menu.getAllKcal() + " kcal<br>"
                                       + "100g당 칼로리: " + menu.getOhKcal() + " kcal<br>"
                                       + "중량: " + menu.getW() + "g<br>" 
                                       + "탄수화물: " + menu.getC() + "g<br>"
                                       + "단백질: " + menu.getP() + "g<br>"
                                       + "지방: " + menu.getF() + "g<br>"
                                       + "당: " + menu.getS() + "g<br>"
                                       + "나트륨: " + menu.getNa() + "mg";

                    out.println("<tr onmouseover=\"showTooltip('" + tooltipData + "', event, '" + menu.getName() + "')\" onmouseout=\"hideTooltip()\">");
                    out.println("<td>" + menu.getNum() + "</td>");
                    out.println("<td>" + menu.getName() + "</td>");
                    out.println("<td><a href='menuSelect?num=" + menu.getNum() + "'>선택</a></td>");
                    out.println("</tr>");
                }
            } else {
                out.println("<tr><td colspan='3'>선택된 메뉴가 없습니다.</td></tr>");
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>"); // 메뉴 테이블 래퍼 div 닫기
            out.println("</div>");

            out.println("<div id='vizContainer'></div>");
            out.println("</main>");
            
            out.println("<hr>");

            out.println("<footer>");
            out.println("<button type='button' onclick=\"location.href='getLunches'\">Back</button>");
            out.println("<div class='footer-text'>Protein Cigarette</div>");
            out.println("</footer>");

            out.println("<div id='tooltip' style='display:none;'></div>");

         // JavaScript for Tableau integration and tooltip handling
            out.println("<script type='text/javascript' src='https://public.tableau.com/javascripts/api/tableau-2.min.js'></script>");
            out.println("<script type='text/javascript'>");
            out.println("var viz;");
            out.println("function initViz() {");
            out.println("  var containerDiv = document.getElementById('vizContainer'),");
            out.println("  url = 'https://public.tableau.com/views/FoodList_17260373323020/Sheet1';");
            out.println("  var options = {");
            out.println("    hideTabs: true,");
            out.println("    width: '100%',");
            out.println("    height: '100%',");
            out.println("    onFirstInteractive: function () {");
            out.println("      console.log('Tableau 대시보드 로드 완료');");
            out.println("    }");
            out.println("  };");
            out.println("  viz = new tableau.Viz(containerDiv, url, options);");
            out.println("}");
            out.println("window.onload = function() {");
            out.println("  initViz();");
            if (popupMessage != null) {
                out.println("  alert('" + popupMessage + "');");
            }
            out.println("};");
            out.println("function showTooltip(menuData, event, menuName) {");
            out.println("  var tooltip = document.getElementById('tooltip');");
            out.println("  tooltip.innerHTML = menuData;");
            out.println("  tooltip.style.display = 'block';");
            out.println("  tooltip.style.left = (event.pageX + 15) + 'px';");
            out.println("  tooltip.style.top = (event.pageY + 15) + 'px';");
            out.println("  tooltip.classList.add('show');");
            out.println("  var sheet = viz.getWorkbook().getActiveSheet();");
            out.println("  sheet.applyFilterAsync('Name', menuName, tableau.FilterUpdateType.REPLACE);");
            out.println("}");
            out.println("function hideTooltip() {");
            out.println("  var tooltip = document.getElementById('tooltip');");
            out.println("  tooltip.classList.remove('show');");
            out.println("  setTimeout(function() {");
            out.println("    tooltip.style.display = 'none';");
            out.println("  }, 10000);");
            out.println("}");
            out.println("</script>");


            out.println("</body>");
            out.println("</html>");
        }
    }
}
