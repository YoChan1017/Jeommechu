package com.jeommechu.menu.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jeommechu.menu.common.JDBCUtil;

public class MemberDAO {

	private Connection getConnection() throws SQLException {
        return JDBCUtil.getConnection();
    }
	
	// 사용자 검증 및 사용자 객체 반환
    public MemberVO getUserByIdAndPassword(String id, String password) throws SQLException {
        String sql = "SELECT * FROM MEMBER WHERE memberID = ? AND memberPW = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, id);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MemberVO user = new MemberVO();
                    user.setId(rs.getInt("id"));
                    user.setMemberID(rs.getString("memberID"));
                    user.setMemberPW(rs.getString("memberPW"));
                    user.setMemberName(rs.getString("memberName"));
                    user.setRole(rs.getString("role"));
                    return user;
                } else {
                    return null;
                }
            }
        }
    }
	
	// 사용자 검증 메서드
    public boolean validateUser(String id, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE memberID = ? AND memberPW = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        }
    }

    // 회원 추가
    public void addMember(MemberVO member) throws SQLException {
        String sql = "INSERT INTO MEMBER (memberID, memberPW, memberName, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberID());
            pstmt.setString(2, member.getMemberPW());
            pstmt.setString(3, member.getMemberName());
            pstmt.setString(4, member.getRole());
            pstmt.executeUpdate();
        }
    }

    // 회원 조회
    public MemberVO getMember(int id) throws SQLException {
        String sql = "SELECT * FROM MEMBER WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new MemberVO(
                        rs.getInt("id"),
                        rs.getString("memberID"),
                        rs.getString("memberPW"),
                        rs.getString("memberName"),
                        rs.getString("role")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    // 모든 회원 조회
    public List<MemberVO> getAllMembers() throws SQLException {
        List<MemberVO> members = new ArrayList<>();
        String sql = "SELECT * FROM MEMBER";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                members.add(new MemberVO(
                    rs.getInt("id"),
                    rs.getString("memberID"),
                    rs.getString("memberPW"),
                    rs.getString("memberName"),
                    rs.getString("role")
                ));
            }
        }
        return members;
    }

    // 회원 정보 수정
    public void updateMember(MemberVO member) throws SQLException {
        String sql = "UPDATE MEMBER SET memberID = ?, memberPW = ?, memberName = ?, role = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getMemberID());
            pstmt.setString(2, member.getMemberPW());
            pstmt.setString(3, member.getMemberName());
            pstmt.setString(4, member.getRole());
            pstmt.setInt(5, member.getId());
            pstmt.executeUpdate();
        }
    }

    // 회원 삭제
    public void deleteMember(int id) throws SQLException {
        String sql = "DELETE FROM MEMBER WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
