package com.zkp.breath.designpattern.decorator;

/**
 * 装饰模式：动态给一个对象添加一些额外的职责，就增加功能来说比生成子类更加灵活。
 * 优点：
 * 1. 一般为了扩展一个类经常使用继承方式实现，但继承为类引入了静态特征，并且随着扩展功能的增多，子类会很膨胀。
 * 如果一直使用继承，那么在多层继承下，父类的有些功能并非子类需要，这时候就会导致子类职能臃肿，也就违反了职能单一原则。
 * 2. 装饰类和被装饰类可以独立发展，不会相互耦合，装饰模式是继承的一个替代模式，装饰模式可以动态扩展一个实现类的功能
 * 3. 在不想增加很多子类的情况下扩展类。
 */
class DecoratorClient {
    public static void main(String[] args) {
        Shape circle = new Circle();
        ShapeDecorator borderDecorator = new BorderDecorator(circle);
        ShapeDecorator paintDecorator = new PaintDecorator(borderDecorator);
        paintDecorator.draw();
    }
}
