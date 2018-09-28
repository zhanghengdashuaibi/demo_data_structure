package com.demo.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by 蜡笔小新不爱吃青椒 on 2018/9/27.
 * 实现ArrayList
 */
public class MyArrayList<T> implements Iterable<T> {

    /**
     * 1.基础数组,数组容量
     * @return
     */
    //初始容量 10
    private static final int DEFAULT_CAPACITY = 10;

    //初始size
    private int theSize;
    //初始数组
    private T[] element;

    /**
     * 构造
     * @return
     */
    public MyArrayList(){
        /**
         * 初始化
         */
        doClear();
    }

    /**
     * 清空数组
     * @return
     */
    public void doClear(){
        this.theSize = 0;
        //返回到原始数组
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int size(){
        return this.theSize;
    }

    /**
     * 清空数组
     */
    public void clear(){
        this.doClear();
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty(){
        return (size()==0);
    }

    /**
     * 扩容
     * @return
     */
    public void ensureCapacity(int newCapacity){
         if(newCapacity<this.theSize){
             //新容量小于原来容量
             return;
         }
         //反之 扩容
        /**
         * 扩容方式  通过新数组覆盖老数组
         */
        //老数组
        T[] oldElement = element;
                element = (T[]) new Object[newCapacity];
        //新数组覆盖
//        T[] newElement = (T[]) new Object[newCapacity];
        for (int i = 0; i <size() ; i++) {
            element[i] = oldElement[i];
        }
//        element = newElement;//扩容完成
    }

    /**
     * get方法 通过下标获取数据
     * @return
     */
    public T get(int index){
        if(index < 0 || index >= this.size()){
            //抛出下标越界异常
            throw new ArrayIndexOutOfBoundsException();
        }
        //反之通过下标获取数据
        return element[index];

    }

    /**
     * set()方法 通过替换下标处的数据
     * @return
     */
    public T set(int index,T newValue){
       if(index < 0 || index >= this.size()){
           //数组越界
           throw new ArrayIndexOutOfBoundsException();
       }
       //反之 在下标出替换
        //获取原来下标处的元素
        T old = element[index];
        element[index] = newValue;//将老的元素替换成新的元素
        return old;//返回老的元素
    }

    /**
     * add()方法 添加方法
     * 在下标出新增加元素
     * @return
     */
     public void add(int index,T newValue){
         //尺寸相等 需要扩容
         if(this.element.length==this.size()){
             this.ensureCapacity(this.size()*2+1);//扩容一倍
         }
         //从下标处开始倒循 从前往后空出一个位置
         //theSize当前数组整体元素个数
         //index要插入的下标位
         for (int i = theSize; i <index ; i--) {
             element[i] = element[i-1];
         }
         element[index] = newValue;
         theSize++;
     }

    /**
     * 重写add方法 从表的尾段添加
     */
    public void add(T newValue){
        add(size(),newValue);
    }

    /**
     * remove()方法 移除下标位的元素
     * @return
     */
    public T remove(int index){
        //要移除的元素
        T removeElement = element[index];
        //移除元素需要移除后的下标位向前进一
        for (int i = index; i < size()-1 ; i++) {
            //从当前下标开始循环
            //当前下标移除
            element[i] = element[i+1];
        }
        //整体元素个数减一
        theSize--;
        return removeElement;//返回移除的元素
    }

    /**
     * 实现迭代器的方法
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    /**
     * ArrayListIterator实现迭代器的方法
     * 此实现不可用 因为MyArrayList类的theSize和element不是ArrayListIterator中的元素
     */
//    public class ArrayListIterator implements Iterator<T>{
//        private int current = 0;
//
//        /**
//         * 是否存在下一个元素
//         * @return
//         */
//        @Override
//        public boolean hasNext() {
//            if(current<size()){
//               return true;
//            }
//            return false;
//        }
//
//        /**
//         * 获取下一个元素
//         * @return
//         */
//        @Override
//        public T next() {
//            if(!hasNext()){
//                //不存在下一个元素 抛异常
//                throw new NoSuchElementException();
//            }
//            return element[current++];
//        }
//
//        @Override
//        public void remove() {
////           MyArrayList myArrayList = new MyArrayList();
//            //因为初始值为0 --current 直接从1开始
//            MyArrayList.this.remove(--current);
//        }
//    }

    /**
     * 改良版本
     */
//    public static class ArrayListIterator<T> implements Iterator<T>{
//        private int current = 0;
//
//        private MyArrayList<T> myArrayList;
//
//        /**
//         * 构造
//         */
//        public ArrayListIterator(MyArrayList<T> list){
//            this.myArrayList = list;
//        }
//        /**
//         * 是否存在下一个元素
//         * @return
//         */
//        @Override
//        public boolean hasNext() {
//            if(current<myArrayList.size()){
//                return true;
//            }
//            return false;
//        }
//
//        /**
//         * 获取下一个元素
//         * @return
//         */
//        @Override
//        public T next() {
//            if(!hasNext()){
//                //不存在下一个元素 抛异常
//                throw new NoSuchElementException();
//            }
//            return myArrayList.element[current++];
//        }
//
//        @Override
//        public void remove() {
//            //因为初始值为0 --current 直接从1开始
//            myArrayList.remove(--current);
//        }
//    }

    /**
     * 简化版
     */
    private class ArrayListIterator implements Iterator<T>{
        private int current = 0;


        /**
         * 是否存在下一个元素
         * @return
         */
        @Override
        public boolean hasNext() {
            if(current<size()){
                return true;
            }
            return false;
        }

        /**
         * 获取下一个元素
         * @return
         */
        @Override
        public T next() {
            if(!hasNext()){
                //不存在下一个元素 抛异常
                throw new NoSuchElementException();
            }
            return element[current++];
        }

        @Override
        public void remove() {
            //因为初始值为0 --current 直接从1开始
            MyArrayList.this.remove(--current);
        }
    }
}
