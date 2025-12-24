package com.jeommechu.menu.user;

public class MemberVO {
    private int id;
    private String memberID;
    private String memberPW;
    private String memberName;
    private String role;

    // 기본 생성자
    public MemberVO() {}

    // 모든 필드를 초기화하는 생성자
    public MemberVO(int id, String memberID, String memberPW, String memberName, String role) {
        this.id = id;
        this.memberID = memberID;
        this.memberPW = memberPW;
        this.memberName = memberName;
        this.role = role;
    }

    // Getter 및 Setter 메서드
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberPW() {
        return memberPW;
    }

    public void setMemberPW(String memberPW) {
        this.memberPW = memberPW;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
               "id=" + id +
               ", memberID='" + memberID + '\'' +
               ", memberPW='" + memberPW + '\'' +
               ", memberName='" + memberName + '\'' +
               ", role='" + role + '\'' +
               '}';
    }
}
