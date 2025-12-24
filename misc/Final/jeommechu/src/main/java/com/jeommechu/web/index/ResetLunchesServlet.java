package com.jeommechu.web.index;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.jeommechu.menu.lunch.LunchDAO;

@WebServlet("/resetLunches")
public class ResetLunchesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LunchDAO lunchDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        lunchDAO = new LunchDAO(); // LunchDAO 인스턴스 생성
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            lunchDAO.deleteAllLunches(); // 모든 점심 메뉴 삭제
        } catch (SQLException e) {
            throw new ServletException("Database error while resetting lunches", e);
        }
        response.sendRedirect(request.getContextPath() + "/getLunches"); // 리다이렉트
    }
}
