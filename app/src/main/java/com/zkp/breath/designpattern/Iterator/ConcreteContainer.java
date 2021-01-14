package com.zkp.breath.designpattern.Iterator;

class ConcreteContainer implements Container {
    //    public String[] names = {"Robert", "John", "Julie", "Lora"};
    // 存放聚合对象
    public String[] names;

    public void setData(String[] names) {
        this.names = names;
    }

    @Override
    public Iterator getIterator() {
        return new ConcreteIterator();
    }

    // 迭代聚合对象
    private class ConcreteIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index < names.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return names[index++];
            }
            return null;
        }

    }

}
