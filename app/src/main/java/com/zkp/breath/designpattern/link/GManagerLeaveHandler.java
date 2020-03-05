package com.zkp.breath.designpattern.link;

// 总经理
public class GManagerLeaveHandler extends AbsLeaveHandler {

    @Override
    public void handlerRequest(LeaveRequest request) {
        if (request.getDay() > MIDDLE && request.getDay() < MAX) {
            System.out.println(request.getName() + "请假了" + request.getName() + "天，总经理批准了");
            return;
        }

        if (nextHandler != null) {
            nextHandler.handlerRequest(request);
        } else {
            System.out.println("不合规的申请，不予批准");
        }
    }
}
