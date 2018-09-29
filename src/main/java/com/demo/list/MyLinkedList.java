package com.demo.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/28.
 * 实现LinkedList
 */
public class MyLinkedList<T> implements Iterable<T> {


    private Node<T> beginMarker;//头部链结点
    private Node<T> endMarker;//尾部链结点
    //链中存储的个数
    private int theSize;
    //对链中操作的的次数
    private int modCount = 0;



    /**
     * 创建链结点
     * @return
     */
    private static class Node<T>{
        private T data;//数据
        private Node<T> prev;//前一个链结点的引用
        private Node<T> next;//下一个链结点的引用

        /**
         * 构造
         */
        public Node(Node<T> prev,T data,Node<T> next){
           this.prev = prev;
           this.data = data;
           this.next = next;
        }
    }

    /**
     * size()
     */
    public int size(){
        return theSize;
    }

    /**
     * clear
     * @return
     */
    public void clear(){
       doClear();
    }

    /**
     * 空链处理
     */
    public void doClear(){
        /**
         * 空链
         */
        beginMarker = new Node<>(null,null,null);//头部链结点
        endMarker = new Node<>(beginMarker,null,null);//尾部链结点
        beginMarker.next = endMarker;//头部链结点的下一个指向尾部链结点
        theSize = 0;//初始存储个数为0
        modCount++;//计数 对链操作次数(每操作一个计数加一)
    }

    /**
     * 判空
     */
    public boolean isEmpty(){
        return (size()==0);
    }

    /**
     * 从头部开始添加
     */
    public void addFirst(T value){
        /**
         * 从头部开始添加
         */
        Node<T> current = beginMarker;
        Node newNode = new Node(null,value,current);
        /**
         * 空链情况
         */
        if(isEmpty()){
            beginMarker = newNode;
            endMarker = newNode;
        }else{
            //不是空链的情况
            current.prev = newNode;
            beginMarker = newNode;
        }
        theSize++;
        modCount++;
    }

    /**
     * 从尾部添加链结点
     */
    public void addLast(T value){
        /**
         * 从尾部添加链结点
         */
        Node<T> current = endMarker;
        Node<T> newNode = new Node<>(current,value,null);
        if(isEmpty()){
            /**
             * 空链情况
             */
            endMarker = newNode;
            beginMarker = newNode;
        }else{
            //不是空链的情况
            current.next = newNode;
            endMarker = newNode;
        }
        theSize++;
        modCount++;

    }

    /**
     * 向中间位置前添加
     * 入参 当前位置p 新插入的链结点x
     * @return
     */
    public void addBefore(Node<T> currnet,T x){
        //current不为空的情况调用
        Node<T> newNode = new Node<>(currnet.prev,x,currnet);
            /**
             * 新创建的链结点的下一个就是current
             */
            currnet.prev = newNode;
            if(currnet.prev == null){
                beginMarker = newNode;
            }else{
                currnet.prev.next = newNode;
            }
        theSize++;
        modCount++;
    }

    /**
     * 根据下标位插入数据
     */
    public void add(int index,T value){

        /**
         * 判断
         * 1.什么情况下从头部开始添加
         * 2.什么情况下从尾部开始添加
         * 3.什么情况下从中间开始添加
         */
        if(isEmpty()){
            /**
             * 空链的情况从头部添加
             */
            addFirst(value);
        }else{
            //在当前链结点前面插入新的链结点
            addBefore(getNode(index),value);
        }
    }
    /**
     * 封装整体add方法
     */
    public void add(T value){
        addFirst(value);
    }

    /**
     * get方法
     */
    public T get(int index){
        return getNode(index).data;
    }

    /**
     * set方法
     */
    public T set(int index,T value){
        Node<T> oldNode = getNode(index);
        T oldValue = oldNode.data;
        oldNode.data = value;
        return oldValue;
    }

    /**
     * 从头部开始删除
     */
    public T removeFirst(){
        /**
         * 从头部开始移除
         */
        Node<T> temp = beginMarker;
        /**
         * 只有一个链结点的情况
         */
        if(temp.next==null){
            endMarker = null;
        }else{
            //反之不是一个链结点的情况
            temp.next.prev = null;

        }
        beginMarker = temp.next;
        theSize--;
        modCount++;
        return temp.data;
    }

    /**
     * 从尾部开始移除
     */
    public T removeLast(){

        /**
         * 从尾部开始移除
         */
        Node<T> temp = endMarker;

        if(temp.prev==null){
            /**
             *只有一个链结点的情况
             */
            beginMarker = null;
        }else{
            //反之不是一个链结点
            temp.prev.next = null;
        }
        endMarker = temp.next;
        theSize--;
        modCount++;
        return  temp.data;
    }

    /**
     * remove
     * 入参 要删除的链结点p
     * @return
     */
    public T remove(Node<T> p){
        /**
         * 删除指定的链结点
         */
        T element = p.data;
        Node<T> prev = p.prev;//要删除的当前链结点的前一个引用
        Node<T> next = p.next;//要删除的当前链结点的下一个引用
        /**
         * 判断情况
         */
        if(prev==null){
            /**
             * 要删除的链结点是头部链结点
             */
            //头部链结点
            beginMarker = next;
        }else{
            //反之不是头部链结点
            /**
             * 剩下的情况
             * 1.要删除的链结点是中间结点
             * 2.要删除的链结点是尾部结点
             */
            //判断要删除的链结点是否是尾部结点
            if(next == null){
                //尾部链结点
                endMarker = prev;
            }else{
                //中间链结点
                prev.next = next;
                next.prev = prev;
            }

        }
        if(next==null){
            /**
             * 要删除的链结点是尾部的
             */
            endMarker = prev;
        }else{
            /**
             * 判断情况
             * 1.要删除的链结点是中间结点
             * 2.要删除的链结点是头部链结点
             */
            if(prev==null){
                //头部链结点
                beginMarker = next;
            }else{
                //中间链结点
                prev.next = next;
                next.prev = prev;
            }
        }
        theSize--;//链中存储的个数
        modCount++;//对链的操作次数
        return element;
    }

    /**
     * 根据下标位删除链结点
     * 入参下标位
     */
    public T remove(int index){
        return remove(getNode(index));
    }

    /**
     * 根据下标取得当前的链结点
     * 入参 下标位
     * @return
     */
    public Node<T> getNode(int index){
        /**
         * lower upper 范围在(0-size()-1)
         */
       return getNode(index,0,size()-1);
    }

    /**
     * 重写
     * 入参 下标位index 下限范围lower 上限范围upper
     * @return
     */
    public Node<T> getNode(int index,int lower,int upper){
        /**
         * 当前位置链结点position
          */
        Node<T> p;

        if(index < lower || index > upper){
           throw new ArrayIndexOutOfBoundsException("数组越界");
        }

        /**
         * 专门去判断只有一个链结点的情况
         */
        if(size()==1&&index==0){
           p = beginMarker;
        }else if(index < size()/2){
            /**
             * 头部链结点
             */
            //满足则从头部往尾部
            p = beginMarker.next;
            /**
             * 遍历
             * 下标位为0不进行遍历
             */
            for (int i = 0; i <index ; i++) {
                p = p.next;
            }
        }else{
            //反之尾部往头部
            p = endMarker;//尾部链结点
            /**
             * 这里i=size()-1是错误的
             * 如果下标位是指向的最后一位,如果减一就不满足循环条件
             */
            for (int i = size(); i > index ; i--) {
               p = p.prev;
            }
        }
       return p;
    }




    /**
     * 实现迭代器
     * @return
     */
    @Override
    public Iterator<T> iterator() {

        return new LinkedListIterator();
    }

    /**
     * LinkedListIterator实现迭代器的方法
     */
     private class LinkedListIterator implements Iterator<T>{

        private int expectedModCount = modCount;//操作次数
        //当前结点
        private Node<T> current = beginMarker;
        private boolean okToRemove = false;


        /**
         * 是否存在下一个节点
         */
        public boolean hasNext(){
            return (current != null);
        }

//        public boolean hasCurrent(){
//            return (current != null);
//        }

        /**
         * 下一个链结点
         */
        public T next(){
            checkForComodification();
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            /**
             * 遍历
             */
            T data = current.data;
            current = current.next;
            okToRemove = true;
            return data;
        }

        /**
         * 删除
         */
        public void remove(){
            checkForComodification();
            if(!okToRemove){
                throw new IllegalStateException();

            }
            MyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }

        /**
         * 判断操作次数是否同步
         */
        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }

    }
}
