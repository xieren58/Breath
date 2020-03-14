package com.zkp.breath.designpattern.observerable;

import java.util.Observable;
import java.util.Observer;

public class UtilObserver implements Observer {

    private String name;
    private int edition;
    private float cost;

    public UtilObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        //判断o是否是JournalData的一个实例
        if (o instanceof UtilObservable) {
            //强制转化为JournalData类型
            UtilObservable journalData = (UtilObservable) o;
            //拉取数据
            this.edition = journalData.getEdition();
            this.cost = journalData.getCost();
            buy();
        }

    }

    public void buy() {
        System.out.println(name + "购买了第" + edition + "期的杂志，花费了" + cost + "元。");
    }
}
