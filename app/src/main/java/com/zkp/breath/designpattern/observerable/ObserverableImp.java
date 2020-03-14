package com.zkp.breath.designpattern.observerable;

import java.util.ArrayList;
import java.util.List;

public class ObserverableImp implements IObserverable {

    private List<IObserver> mObservers;
    private int edition;
    private float cost;

    public ObserverableImp() {
        mObservers = new ArrayList<>();
    }

    @Override
    public void registerObserver(IObserver o) {
        mObservers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        int i = mObservers.indexOf(o);
        if (i >= 0)
            mObservers.remove(i);
    }

    @Override
    public void notifyObservers() {
        for (int i = 0; i < mObservers.size(); i++) {
            IObserver observer = mObservers.get(i);
            observer.update(edition, cost);
        }
    }

    public void setInfomation(int edition, float cost) {
        this.edition = edition;
        this.cost = cost;
        //信息更新完毕，通知所有观察者
        notifyObservers();
    }
}
