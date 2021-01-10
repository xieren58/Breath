package com.zkp.breath.designpattern.decorator;


abstract class ShapeDecorator implements Shape {

    private Shape shape;

    /**
     * 利用构造函数或者setXX()函数对对象进行包装
     */
    public ShapeDecorator(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw() {
        if (shape != null) {
            shape.draw();
        }
    }
}


