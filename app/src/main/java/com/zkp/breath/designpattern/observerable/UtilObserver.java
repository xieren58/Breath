package com.zkp.breath.designpattern.observerable;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者工具类
 */
public class UtilObserver implements Observer {

    private String name;
    private int edition;
    private float cost;

    public UtilObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UtilObservable) {
            UtilObservable journalData = (UtilObservable) o;
            this.edition = journalData.getEdition();
            this.cost = journalData.getCost();
            buy();
        }
    }

    public void buy() {
        System.out.println(name + "购买了第" + edition + "期的杂志，花费了" + cost + "元。");
    }
}
