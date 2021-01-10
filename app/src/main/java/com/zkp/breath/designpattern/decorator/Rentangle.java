package com.zkp.breath.designpattern.decorator;

import android.util.Log;

class Rentangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Rentangle_draw: 画矩形");
    }
}
