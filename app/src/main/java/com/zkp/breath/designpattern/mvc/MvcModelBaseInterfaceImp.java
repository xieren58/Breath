package com.zkp.breath.designpattern.mvc;

public class MvcModelBaseInterfaceImp implements MvcModelBaseInterface {
    @Override
    public void onCommonFunction(MvcCallback mvcCallback) {
        // 你的业务操作处理
        // 。。。
        // 。。。
        String s = "运算结果";
        mvcCallback.onCallBack(s);
    }
}
