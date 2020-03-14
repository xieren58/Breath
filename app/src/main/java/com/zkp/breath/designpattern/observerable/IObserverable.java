package com.zkp.breath.designpattern.observerable;

// 被观察者
public interface IObserverable {

    public void registerObserver(IObserver o);

    public void removeObserver(IObserver o);

    public void notifyObservers();
}
