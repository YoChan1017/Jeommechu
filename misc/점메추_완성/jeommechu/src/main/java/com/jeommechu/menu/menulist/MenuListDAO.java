package com.jeommechu.menu.menulist;

import com.jeommechu.menu.common.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuListDAO {

    // 전체 리스트 불러오는 메서드
    public List<MenuListVO> getMenuList(String search) throws SQLException {
        List<MenuListVO> menuList = new ArrayList<>();
        String sql = "SELECT * FROM FoodList";
        
        // 검색어가 있을 경우 조건 추가
        if (search != null && !search.trim().isEmpty()) {
            sql += " WHERE Name LIKE ?";
        }

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 검색어가 있을 경우에만 파라미터 설정
            if (search != null && !search.trim().isEmpty()) {
                stmt.setString(1, "%" + search + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MenuListVO menu = new MenuListVO();
                    menu.setNum(rs.getString("Num"));
                    menu.setName(rs.getString("Name"));
                    menu.setAllKcal(rs.getDouble("AllKcal"));
                    menu.setOhKcal(rs.getDouble("OhKcal"));
                    menu.setW(rs.getDouble("W"));
                    menu.setP(rs.getDouble("P"));
                    menu.setF(rs.getDouble("F"));
                    menu.setC(rs.getDouble("C"));
                    menu.setS(rs.getDouble("S"));
                    menu.setNa(rs.getDouble("Na"));
                    menu.setSf(rs.getDouble("SF"));

                    menuList.add(menu);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("데이터베이스 오류 발생", e);
        }

        return menuList;
    }

    // 점심에 추가하는 메서드
    public boolean addToLunch(String foodlistNum, String memberId) {
        String sql = "INSERT INTO Lunch (foodlist_num, member_id) VALUES (?, ?)";
        boolean isAdded = false;

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, foodlistNum);
            stmt.setString(2, memberId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isAdded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 로그에 에러를 출력
        }

        return isAdded;
    }
}