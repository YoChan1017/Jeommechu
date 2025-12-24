package com.jeommechu.menu.lunch;

public class LunchVO {
    private int lunchId;
    private String foodlistNum;
    private int memberId;
    private String foodName; // 추가된 필드
    private String memberName; // 추가된 필드

    // 기본 생성자
    public LunchVO() {
    }

    // 매개변수가 있는 생성자
    public LunchVO(int lunchId, String foodlistNum, int memberId) {
        this.lunchId = lunchId;
        this.foodlistNum = foodlistNum;
        this.memberId = memberId;
    }

    // Getter와 Setter
    public int getLunchId() {
        return lunchId;
    }

    public void setLunchId(int lunchId) {
        this.lunchId = lunchId;
    }

    public String getFoodlistNum() {
        return foodlistNum;
    }

    public void setFoodlistNum(String foodlistNum) {
        this.foodlistNum = foodlistNum;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "LunchVO{" +
                "lunchId=" + lunchId +
                ", foodlistNum='" + foodlistNum + '\'' +
                ", memberId=" + memberId +
                ", foodName='" + foodName + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
