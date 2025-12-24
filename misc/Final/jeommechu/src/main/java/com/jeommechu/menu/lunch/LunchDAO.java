package com.jeommechu.menu.lunch;

import com.jeommechu.menu.common.JDBCUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LunchDAO {

    // 전체 조회 메서드
    public List<LunchVO> getAllLunches() throws SQLException {
        List<LunchVO> lunches = new ArrayList<>();
        String query = "SELECT l.lunch_id, l.foodlist_num, l.member_id, f.Name AS foodName, m.memberName " +
                       "FROM Lunch l " +
                       "JOIN FoodList f ON l.foodlist_num = f.Num " +
                       "JOIN MEMBER m ON l.member_id = m.id";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int lunchId = rs.getInt("lunch_id");
                String foodlistNum = rs.getString("foodlist_num");
                int memberId = rs.getInt("member_id");
                String foodName = rs.getString("foodName");
                String memberName = rs.getString("memberName");

                LunchVO lunch = new LunchVO(lunchId, foodlistNum, memberId);
                lunch.setFoodName(foodName); // LunchVO에 foodName을 추가해야 합니다
                lunch.setMemberName(memberName); // LunchVO에 memberName을 추가해야 합니다
                lunches.add(lunch);
            }
        }
        return lunches;
    }

    // 전체 삭제 메서드
    public void deleteAllLunches() throws SQLException {
        String query = "DELETE FROM Lunch";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        }
    }

    
    private Connection getConnection() throws SQLException {
        return JDBCUtil.getConnection(); // 커넥션 풀 사용 권장
    }
    // 정보 추가 메서드
    public boolean addLunch(LunchVO lunch) throws SQLException {
        String sql = "INSERT INTO Lunch (foodlist_num, member_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // foodlist_num이 존재하는지 확인
            if (!isFoodlistNumExists(conn, lunch.getFoodlistNum())) {
                throw new SQLException("Foodlist number does not exist");
            }

            // member_id가 존재하는지 확인
            if (!isMemberIdExists(conn, lunch.getMemberId())) {
                throw new SQLException("Member ID does not exist");
            }

            pstmt.setString(1, lunch.getFoodlistNum());
            pstmt.setInt(2, lunch.getMemberId());
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        }
    }

    // foodlist_num이 존재하는지 확인
    private boolean isFoodlistNumExists(Connection conn, String foodlistNum) throws SQLException {
        String sql = "SELECT COUNT(*) FROM FoodList WHERE Num = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, foodlistNum);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // member_id가 존재하는지 확인
    private boolean isMemberIdExists(Connection conn, Integer memberId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    boolean exists = rs.getInt(1) > 0;
                    System.out.println("Member ID: " + memberId + " exists: " + exists);
                    return exists;
                }
            }
        }
        return false;
    }
    
    
 // 특정 사용자가 추가한 메뉴의 개수를 세는 메서드
    public int countMenusForMember(String memberId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lunch WHERE member_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("데이터베이스 오류 발생", e);
        }
        return 0;
    }

    // 메뉴를 추가하는 메서드
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
    
    public int getLunchCount(int memberId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Lunch WHERE member_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, memberId);
             try (ResultSet rs = stmt.executeQuery()) {
                 if (rs.next()) {
                     return rs.getInt(1);
                 }
             }
        }
        return 0; // 기본값
    }

}
