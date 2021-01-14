package com.zkp.breath.designpattern.responsibility_chain;

import android.telephony.mbms.MbmsErrors;

public abstract class Handler {
    // 接班人
    protected Handler successor;

    public void setSuccessor(Handler successor) {
        successor = successor;
    }

    public abstract void handleRequest(int request);
}

