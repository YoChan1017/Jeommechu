package com.jeommechu.web.menulist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import com.jeommechu.menu.lunch.LunchDAO;
import com.jeommechu.menu.lunch.LunchVO;

@WebServlet("/menuSelect")
public class MenuSelectServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LunchDAO lunchDAO = new LunchDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String foodlistNum = request.getParameter("num");
        Integer memberId = (Integer) session.getAttribute("memberId");

        // Validate parameters
        if (foodlistNum != null && !foodlistNum.trim().isEmpty() && memberId != null) {
            LunchVO lunch = new LunchVO();
            lunch.setFoodlistNum(foodlistNum);
            lunch.setMemberId(memberId.intValue()); // Integer를 int로 변환

            try {
                // Check if the user has already selected 2 items
                int currentCount = lunchDAO.getLunchCount(memberId.intValue());
                if (currentCount >= 2) {
                    // 클라이언트 측에서 오류 메시지를 표시하기 위해 세션에 설정
                    session.setAttribute("popupMessage", "더 이상 메뉴를 넣을 수 없습니다.");
                    response.sendRedirect("menuList"); // 메뉴 목록 페이지로 리다이렉트
                    return;
                }

                boolean result = lunchDAO.addLunch(lunch);
                if (result) {
                    // 성공 시 getLunches 서블릿으로 리다이렉트
                    response.sendRedirect("getLunches");
                } else {
                    // 실패 시 menuList 서블릿으로 리다이렉트
                    response.sendRedirect("menuList?error=saveFailed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // 실패 시 에러 정보를 쿼리 파라미터로 추가하여 menuList 서블릿으로 리다이렉트
                response.sendRedirect("menuList?error=databaseError");
            }
        } else {
            // 파라미터가 없거나 세션 정보가 없을 경우 menuList 서블릿으로 리다이렉트
            response.sendRedirect("menuList?error=invalidInput");
        }
    }
}
