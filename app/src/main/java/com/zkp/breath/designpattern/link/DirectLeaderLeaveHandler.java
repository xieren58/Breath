package com.zkp.breath.designpattern.link;

// 部门经理
public class DirectLeaderLeaveHandler extends AbsLeaveHandler {

    @Override
    public void handlerRequest(LeaveRequest request) {
        if (request.getDay() <= MIN) {
            System.out.println(request.getName() + "请假了" + request.getName() + "天，直接上级批准了");
            return;
        }

        if (nextHandler != null) {
            nextHandler.handlerRequest(request);
        } else {
            System.out.println("不合规的申请，不予批准");
        }
    }
}
