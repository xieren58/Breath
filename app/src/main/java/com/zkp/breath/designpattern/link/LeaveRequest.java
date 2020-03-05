package com.zkp.breath.designpattern.link;

public class LeaveRequest {
    // 请假的天数
    private int day;
    // 请假的人
    private String name;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
