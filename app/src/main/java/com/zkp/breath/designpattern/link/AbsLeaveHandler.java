package com.zkp.breath.designpattern.link;

public abstract class AbsLeaveHandler {

    /**
     * 直接主管审批处理的请假天数
     */
    protected int MIN = 1;
    /**
     * 部门经理处理的请假天数
     */
    protected int MIDDLE = 3;
    /**
     * 总经理处理的请假天数
     */
    protected int MAX = 30;

    /**
     * 下一个处理节点（即更高级别的领导）
     */
    protected AbsLeaveHandler nextHandler;

    /**
     * 设置下一节点
     */
    protected void setNextHandler(AbsLeaveHandler handler) {
        this.nextHandler = handler;
    }

    /**
     * 处理请假的请求，子类实现
     */
    public abstract void handlerRequest(LeaveRequest request);
}
