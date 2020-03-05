package com.zkp.breath.designpattern.link;

/**
 * 责任链设计模式的例子。
 * 每个节点都包含下一个节点的对象，每一个节点都有触发响应的条件，处理一般从最低层开始
 * 其实内部和链表的含义很像。
 */
public class ResponsibilityLinkDemo {
    public static void main(String[] args) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setName("小米");
        leaveRequest.setDay(13);

        // 第一层
        AbsLeaveHandler absLeaveHandler1 = new DirectLeaderLeaveHandler();
        // 第二层
        AbsLeaveHandler absLeaveHandler2 = new DeptManagerLeaveHandler();
        // 第三层
        AbsLeaveHandler absLeaveHandler3 = new GManagerLeaveHandler();

        // 第一层持有第二层对象
        absLeaveHandler1.setNextHandler(absLeaveHandler2);
        // 第二层持有第三层对象
        absLeaveHandler2.setNextHandler(absLeaveHandler3);

        // 从最低层开始
        absLeaveHandler1.handlerRequest(leaveRequest);
    }
}
