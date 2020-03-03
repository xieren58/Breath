package com.zkp.breath.arithmetic;

// 单链表反转
public class LinkedDemo {

    public static void main(String[] args) {
        Node<Integer> node5 = new Node<>(5, null);
        Node<Integer> node4 = new Node<>(4, node5);
        Node<Integer> node3 = new Node<>(3, node4);
        Node<Integer> node2 = new Node<>(2, node3);
        Node<Integer> node1 = new Node<>(1, node2);

        Node node = reverseList1(node1);
//        Node node = reverseList2(node1);
        System.out.println("打印: " + node);
    }

    public static Node reverseList1(Node<Integer> head) {
        if (head == null) {
            return head;
        }
        // 创建一个假的头节点，指针为null
        Node<Integer> dummy = new Node<>(-1, null);
        // 假头节点的指针指向真头节点
        dummy.next = head;
        // 上一个节点
        Node prev = dummy.next; // 1
        // 当前节点
        Node pCur = prev.next; // 2
        while (pCur != null) {
            prev.next = pCur.next;  // prev连接下一次需要反转的节点
            pCur.next = dummy.next; // 反转节点pCur
            dummy.next = pCur;  // 纠正头结点dummy的指向
            pCur = prev.next;   // pCur指向下一次要反转的节点
        }
        return dummy.next;
    }

    // 2.新建链表,头节点插入法
    public static Node reverseList2(Node<Integer> head) {
        Node<Integer> dummy = new Node(-1, null);
        Node pCur = head;
        while (pCur != null) {
            Node pNex = pCur.next;
            pCur.next = dummy.next;
            dummy.next = pCur;
            pCur = pNex;
        }
        return dummy.next;
    }

    static class Node<T> {
        // 当前节点的数据
        private T value;
        // 指向下个节点的指针
        private Node next;

        public Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", next=" + next +
                    '}';
        }
    }

}
