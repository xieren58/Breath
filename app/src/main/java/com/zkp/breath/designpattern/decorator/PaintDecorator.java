package com.zkp.breath.designpattern.decorator;

class PaintDecorator extends ShapeDecorator {

    public PaintDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        setPaint();
        super.draw();
    }

    private void setPaint() {
        System.out.println("red paint");
    }

}
