package com.zkp.breath.designpattern.observerable;

import java.util.Observable;

public class UtilObservable extends Observable {
    private int edition;
    private float cost;

    public void setInfomation(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        setChanged();
        //调用无参数的方法，使用拉模型
        notifyObservers();
    }

    //提供get方法
    public int getEdition() {
        return edition;
    }

    public float getCost() {
        return cost;
    }
}
