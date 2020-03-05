package com.zkp.breath.designpattern.factory.abstracts;

import android.graphics.drawable.shapes.Shape;

import com.zkp.breath.designpattern.factory.simple.ILanguage;

/**
 * 抽象工厂
 */
public abstract class AbstractFactory {

    public abstract ILanguage getLanguage(int flag);

    public abstract IDesignPatterns getDesignPattern(int flag);
}
