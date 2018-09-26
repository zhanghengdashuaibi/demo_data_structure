package com.demo.link;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/19.
 * 有序链表
 */
public class SortedList {


    private Link first;

    /**
     * 构造初始为null
     */
    public SortedList(){
        this.first = null;
    }

    /**
     * 有序插入新的链结点
     */
    public void insert(long key){
        Link newLink = new Link(key);
        //前一个链结点
        Link previous = null;
        //当前链结点
        Link current = first;//将头部链结点指向当前链结点

        /**
         * 有序插入规则 按数据的大小排序插入
         */
        while (current != null && key > current.dData){
             //满足条件
            /**
             * 将当前的链结点指向前一个链结点
             * 将当前的下一个链结点指向当前链结点
             */
            previous = current;
            current = current.next;
        }

        //反之current为null
        if(previous==null){
          //前一个链结点为null
            first = newLink;//空链情况 将新创建的链结点 指向头部链结点
        }else{
            //不是空链的情况
            /**
             * 此情况属于 新创建的链结点数据为2  而已经存在的链结点 1和3
             * 而且新创建的链结点 在已有的链结点中部位置 ,也就是key<current.dData的情况
             */
            previous.next = newLink;//新创建的链结点指向 前一个的下一个链结点
        }
        /**
         * 此情况属于 新创建的链结点数据为1  而已存在的链结点为2和3
         */
        //否则就是在最头部的位置插入
        newLink.next = current;//当前链结点指向新创建的下一个链结点
    }

    /**
     * 移除
     */
    public long remove(){
        long temp = first.dData;
        /**
         * 移除 从头部开始移除
         */
        first = first.next;
        return temp;
    }

    /**
     * 判断是否为空
     */
    public boolean isEmpty(){
        return (first == null);
    }


    /**
     * 遍历显示
     */
    public void displayList(){
        /**
         * 遍历规则 从头开始按顺序遍历
         */
        Link current = first;

        while (current != null){
            current.displayLink();
           current = current.next;
        }
    }

    /**
     *
     * 创建新的链结点
     */
    public class Link{
        public long dData;//数据
        public Link next;//连接下一个链结点

        /**
         * 构造
         */
        public Link(long d){
            this.dData = d;
        }

        /**
         * 显示链结点的数据
         */
        public void displayLink(){
            System.out.println("dData:"+dData);
        }
    }

}
