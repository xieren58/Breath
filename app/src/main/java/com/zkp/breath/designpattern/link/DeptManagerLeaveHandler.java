package com.zkp.breath.designpattern.link;

// 直接上级
public class DeptManagerLeaveHandler extends AbsLeaveHandler {

    @Override
    public void handlerRequest(LeaveRequest request) {
        if (request.getDay() > MIN && request.getDay() <= MIDDLE) {
            System.out.println(request.getName() + "请假了" + request.getName() + "天，部门经理批准了");
            return;
        }

        if (nextHandler != null) {
            nextHandler.handlerRequest(request);
        } else {
            System.out.println("不合规的申请，不予批准");
        }
    }
}
