package com.zkp.breath.designpattern.observerable;

import java.util.Observable;

/**
 * 被观察工具类，内置了addObserver()、deleteObserver()、notifyObservers()等方法。
 */
public class UtilObservable extends Observable {
    private int edition;
    private float cost;

    public void setInfomation(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        // 设置改变后，才能触发通知
        setChanged();
        notifyObservers();
    }

    public int getEdition() {
        return edition;
    }

    public float getCost() {
        return cost;
    }
}
