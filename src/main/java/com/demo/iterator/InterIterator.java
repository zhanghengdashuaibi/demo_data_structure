package com.demo.iterator;

import org.apache.log4j.Logger;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/26.
 *
 * 迭代器
 */
public class InterIterator {

    /**
     * 日志
     */
    private static Logger log = Logger.getLogger(InterIterator.class);


    /**
     * 创建链结点 自引用
     */
    public static class Link{
        private long dData;
        private Link next;//指向下一个链结点的引用

        /**
         * 构造
         */
        public Link(long dd){
            this.dData = dd;
        }

        /**
         * 显示
         */
        public void displayLink(){
//            log.info(this.dData);
            System.out.println(this.dData);
        }

        public long getdData() {
            return dData;
        }

        public void setdData(long dData) {
            this.dData = dData;
        }
    }

    public static class LinkList{
        private Link first;//头结点

        /**
         * 判空
         */
        public boolean isEmpty(){
            return (first==null);
        }

        /**
         * 构造
         */
        public LinkList(){
            this.first = null;
        }

        /**
         * 迭代器
         * @return
         */
        public ListIterator getIterator(){
            return new ListIterator(this);
        }

        public Link getFirst() {
            return first;
        }

        public void setFirst(Link first) {
            this.first = first;
        }

        /**
         * 显示
         */
        public void displayList(){
            /**
             * 从first端开始遍历
             */
            Link current = first;

            while(current != null){
                current.displayLink();
                current = current.next;
            }

        }

    }

    /**
     * 迭代器类
     */
    public static class ListIterator{
        private Link previous;//对前一个链结点的引用
        private Link current;//当前链结点的引用
        private LinkList ourList;//引用父list

        /**
         * 构造 初始化(始终将迭代器设置在first)
         */
        public ListIterator(LinkList list){
            this.ourList = list;
            reset();
        }

        /**
         * 把迭代器设置在first端
         */
        public void reset(){
            //头部链结点指向当前链结点
            this.current = ourList.getFirst();
            //因为是第一个链结点所以他的前一个链结点要指向null
            this.previous = null;
        }

        /**
         * 迭代器在链尾 则返回true
         */
        public boolean atEnd(){
            return (current.next == null);
        }

        /**
         * 把迭代器移动到下一个链结点
         */
        public void nextLink(){
            previous = current;
            current = current.next;
        }

        /**
         * 返回迭代器指定的链结点
         */
        public Link getCurrent(){
            return current;
        }

        /**
         * 在迭代器后面插入链结点
         */
        public void insertAfter(long dd){

            Link newLink = new Link(dd);

            /**
             * 空链的情况
             */
            if(ourList.isEmpty()){
                //链头就是新创建的链结点
                ourList.setFirst(newLink);
                current = newLink;//当前链结点指向新创建的链结点
            }else{
                //不是空链的情况
                newLink.next = current.next;
                current.next = newLink;
                //把当前链结点指向下一个
                nextLink();
            }
        }

        /**
         * 在迭代器前面插入一个新的链结点
         */
        public void insertBefore(long dd){
            Link newLink = new Link(dd);
            if(previous==null){
                //链中只有一个链结点(当前链结点是头部链结点)
                newLink.next = ourList.getFirst();
                ourList.setFirst(newLink);
                //重置到头链结点
                reset();
            }else{
                //
                newLink.next = previous.next;
                previous.next = newLink;
                current = newLink;
            }
        }

        /**
         * 删除迭代器所指向的链结点
         */
        public long deleteCurrent(){
            long value = current.dData;
            if(previous==null){
                //当前链结点是头部链结点,也就是在链头删除
                ourList.setFirst(current.next);
                reset();
            }else{
                //反之 不是在链头删除
                previous.next = current.next;
                if(atEnd()){
                    //链尾(要删除的链结点是链尾的节点)
                    reset();
                }else{
                    current = current.next;
                }
            }
            return value;
        }

    }

}
