package com.zkp.breath.designpattern.decorator;

class BorderDecorator extends ShapeDecorator {

    public BorderDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        setBorder();
        super.draw();
    }

    private void setBorder() {
        System.out.println("set Border by paint");
    }

}
