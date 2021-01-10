package com.zkp.breath.designpattern.decorator;

import android.util.Log;

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle_draw: 画圆");
    }
}
