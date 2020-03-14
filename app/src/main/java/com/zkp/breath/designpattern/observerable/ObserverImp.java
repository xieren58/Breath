package com.zkp.breath.designpattern.observerable;

public class ObserverImp implements IObserver {
    private String name;
    private int edition;
    private float cost;

    public ObserverImp(String name) {
        this.name = name;
    }

    @Override
    public void update(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        buy();
    }

    public void buy() {
        System.out.println(name + "购买了第" + edition + "期的杂志，花费了" + cost + "元。");
    }
}
