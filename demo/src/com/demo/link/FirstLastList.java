package com.demo.link;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/18.
 * 双端链表
 */
public class FirstLastList {


    private Link first;//头部链结点
    private Link last;//尾部链结点


    /**
     * 自引用 创建链结点类
     */
    public class Link{
        private long dData;//数据
        private Link next;//链结点的下一个链结

        /**
         * 构造
         */
        public Link(long d){
            this.dData = d;
        }

        /**
         * 显示链结点
         */
        public void displayLink(){
            System.out.println(dData);
        }
    }

    /**
     * FirstLastList构造
     * 申明之初将 头尾链结点置为空 (这步会在申明对象是 自动完成,为了代码的观赏性)
     */
    public FirstLastList(){
        this.first = null;
        this.last = null;
    }

    /**
     * 是否为空判断
     * 链头是空的 链尾肯定是空的 空链
     */
    public boolean isEmpty(){
        return (first == null);
    }

    /**
     * 向链头添加数据
     * 添加新的链结点
     */
    public void insertFirst(long d){
        Link newLink = new Link(d);//新创建链结点
        /**
         * 插入规则 向链头插入新结点 就像入栈一样 新插入的再栈低 所以 新插入的一定是往链尾方向的
         *  因为双端链表有两个项  first头部链结点 last尾部链结点
         */
        if(this.isEmpty()){
            //空链情况
            /**
             * 新创建的链结点 要指向链尾 所以下一次插入数据 链头first可以直接指向新插入的链节点的next
             * 而新创建的链结点就指向first
             */
            last = newLink;
        }
        //反之不是空的
        newLink.next = first;//头部的链结点指向新创建的链结点的下一个
        first = newLink;//新创建的链结点指向头部的链结点
    }

    /**
     * 向链尾增加数据
     */
    public void insertLast(long d){
        Link newLink = new Link(d);//创建新的链结点

        /**
         * 插入规则 链尾插入与链头插入相反 链尾插入 新插入的链结点往链头方向
         */
        if(this.isEmpty()){//空链情况
            /**
             * 链尾插入新的链结点
             * 当空链情况下 因为插入规则是从链尾向链头插入 所以 链尾就是链头 所以新插入的链结点指向first
             * 而下一个插入的last的next下一个链结点指向新创建的链结点,新创建的链结点指向last
             */
            first = newLink;
        }else{
            /**
             * 从链尾处添加
             */
            last.next = newLink;//新创建的链结点指向链尾的下一个
            last = newLink;//新创建的链结点指向链尾的链结点
        }
    }

    /**
     * 链头删除
     */
    public long deleteFirst(){

        long temp = first.dData;
        if(first.next==null){
           //链头的下一个是null,就说明last就是null
            last = null;
        }
        //反之不是空的
        first = first.next;//链头的下一个就指向链头
        //返回被移除的数据
        return temp;
    }

    /**
     * 遍历
     */
    public void displayList(){
        Link current = first; //始终从链头开始按照顺序往下找
        while (current != null){
            current.displayLink();
            current = current.next;//移动到下一个
        }
    }


}
