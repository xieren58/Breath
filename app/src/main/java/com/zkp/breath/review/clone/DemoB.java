package com.zkp.breath.review.clone;

/**
 * 实现彻底的深度克隆，类中的引用类型也需要实现clone
 */
public class DemoB {

    static class Address {
        private String add;

        public String getAdd() {
            return add;
        }

        public void setAdd(String add) {
            this.add = add;
        }
    }

    static class Student implements Cloneable {
        private int number;

        private Address addr;

        public Address getAddr() {
            return addr;
        }

        public void setAddr(Address addr) {
            this.addr = addr;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public Object clone() {
            Student stu = null;
            try {
                stu = (Student) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return stu;
        }
    }

    public static void main(String[] args) {
        Address addr = new Address();
        addr.setAdd("杭州市");
        Student stu1 = new Student();
        stu1.setNumber(123);
        stu1.setAddr(addr);

        Student stu2 = (Student) stu1.clone();

        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());

        // Address没有实现clone，所以两个对象的addr变量指向同一个内存地址
        addr.setAdd("西湖区");

        System.out.println("========================================================");
        System.out.println("========================================================");

        System.out.println("学生1:" + stu1.getNumber() + ",地址:" + stu1.getAddr().getAdd());
        System.out.println("学生2:" + stu2.getNumber() + ",地址:" + stu2.getAddr().getAdd());

    }
}
