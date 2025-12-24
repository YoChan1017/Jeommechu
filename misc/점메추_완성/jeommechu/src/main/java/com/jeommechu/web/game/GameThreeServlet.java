package com.jeommechu.web.game;

import com.jeommechu.menu.lunch.LunchDAO;
import com.jeommechu.menu.lunch.LunchVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@WebServlet("/LadderGame.do")
public class GameThreeServlet extends HttpServlet {
    private int numberOfLines; // 사다리의 세로줄 개수 (메뉴 개수에 따라 다르게 설정)
    private static final int NUMBER_OF_RUNGS = 4; // 사다리의 가로줄(칸막이) 개수
    private LunchDAO lunchDAO = new LunchDAO(); // 점심 메뉴 DAO

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 데이터베이스에서 메뉴 가져오기
        List<LunchVO> lunches;
        try {
            lunches = lunchDAO.getAllLunches(); // 데이터베이스에서 모든 메뉴 가져오기
            numberOfLines = lunches.size();  // 메뉴의 개수에 따라 세로줄 수 설정
            if (numberOfLines < 2) {
                throw new ServletException("메뉴가 부족합니다. 최소 2개의 메뉴가 필요합니다.");
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }

        // 클라이언트에서 시작 위치 값 받아오기
        int playerStart = Integer.parseInt(request.getParameter("playerStart")) - 1;
        if (playerStart >= numberOfLines) {
            playerStart = numberOfLines - 1; // 시작 위치가 메뉴 개수를 초과하지 않도록 제한
        }

        // 사다리 생성
        boolean[][] ladder = createLadder();
        
        // 최종 위치 계산
        int finalPosition = getFinalPosition(ladder, playerStart);
        String selectedMenu = lunches.get(finalPosition).getFoodName(); // 최종 위치의 메뉴 가져오기

        // JSON으로 사다리와 결과 전달
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // JSON 생성
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"finalPosition\":").append(finalPosition).append(",");
        json.append("\"selectedMenu\":\"").append(selectedMenu).append("\",");
        json.append("\"menus\":[");
        for (int i = 0; i < lunches.size(); i++) {
            json.append("\"").append(lunches.get(i).getFoodName()).append("\"");
            if (i < lunches.size() - 1) json.append(",");
        }
        json.append("],");
        json.append("\"ladder\":[");
        for (int i = 0; i < ladder.length; i++) {
            json.append("[");
            for (int j = 0; j < ladder[i].length; j++) {
                json.append(ladder[i][j]);
                if (j < ladder[i].length - 1) json.append(",");
            }
            json.append("]");
            if (i < ladder.length - 1) json.append(",");
        }
        json.append("]}");

        out.print(json.toString());
        out.flush();
    }

    // 사다리의 가로줄을 랜덤으로 생성하는 메서드
    private boolean[][] createLadder() throws ServletException {
        if (numberOfLines <= 1) {
            throw new ServletException("사다리 생성에 필요한 메뉴 수가 충분하지 않습니다.");
        }

        boolean[][] ladder = new boolean[NUMBER_OF_RUNGS][numberOfLines - 1];
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_RUNGS; i++) {
            for (int j = 0; j < numberOfLines - 1; j++) {
                ladder[i][j] = random.nextBoolean(); // 가로줄의 유무를 랜덤하게 결정
            }
        }
        return ladder;
    }

    // 사다리를 타고 내려가 최종 위치를 계산하는 메서드
    private int getFinalPosition(boolean[][] ladder, int startPosition) {
        int currentPosition = startPosition;

        for (int i = 0; i < ladder.length; i++) {
            if (currentPosition > 0 && ladder[i][currentPosition - 1]) {
                currentPosition--; // 왼쪽으로 이동
            } else if (currentPosition < numberOfLines - 1 && ladder[i][currentPosition]) {
                currentPosition++; // 오른쪽으로 이동
            }
        }

        return currentPosition; // 최종 도착 위치 반환
    }
}
